package com.liyulin.demo.mybatis.autoconfigure;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInterceptor;
import com.liyulin.demo.common.constants.SymbolConstants;
import com.liyulin.demo.common.properties.SingleDataSourceProperties;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.support.UniqueBeanNameGenerator;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.mybatis.autoconfigure.MultipleDataSourceAutoConfiguration.MultipleDataSourceRegistrar;
import com.liyulin.demo.mybatis.plugin.MybatisSqlLogInterceptor;
import com.zaxxer.hikari.HikariDataSource;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 多数据源配置
 *
 * @author liyulin
 * @date 2019年4月25日上午10:38:05
 */
@Configuration
@EnableTransactionManagement
@Import({ MultipleDataSourceRegistrar.class })
public class MultipleDataSourceAutoConfiguration {
	
	/** <code>DataSourceTransactionManager</code> bean名称组成部分（后缀） */
	public static final String TRANSACTION_MANAGER_NAME = "DataSourceTransactionManager";
	public static final ConcurrentMap<String, PlatformTransactionManager> MULTIPLE_TRANSACTION_MANAGER_CACHE =
			new ConcurrentReferenceHashMap<>(4);
	
	public static ConcurrentMap<String, PlatformTransactionManager> getMultipleTransactionManagerCache(){
		return MULTIPLE_TRANSACTION_MANAGER_CACHE;
	}
	
	static {
		// 修改determineTransactionManager方法，在方法开头插入获取PlatformTransactionManager的代码
		ClassPool classPool = ClassPool.getDefault();
		// 导入需要的类
		classPool.importPackage("org.springframework.transaction.interceptor.DefaultTransactionAttribute");
		classPool.importPackage("java.util.concurrent.ConcurrentMap");
		classPool.importPackage("java.util.Iterator");
		classPool.importPackage("org.springframework.transaction.PlatformTransactionManager");
		classPool.importPackage("com.liyulin.demo.mybatis.autoconfigure.MultipleDataSourceAutoConfiguration");
		
		String lineSeparator = System.getProperty("line.separator");
		
		// 注意：javassist不支持泛型
		StringBuilder src = new StringBuilder();
		src.append("DefaultTransactionAttribute defaultTransactionAttribute = (DefaultTransactionAttribute) txAttr;").append(lineSeparator);
		src.append("String descriptor = defaultTransactionAttribute.getDescriptor();").append(lineSeparator);
		src.append("ConcurrentMap multipleTransactionManagerCache = MultipleDataSourceAutoConfiguration.getMultipleTransactionManagerCache();").append(lineSeparator);
		src.append("Iterator iterator = multipleTransactionManagerCache.keySet().iterator();").append(lineSeparator);
		src.append("while (iterator.hasNext()) {").append(lineSeparator);
		src.append("	String key = String.valueOf(iterator.next());").append(lineSeparator);
		src.append("	if (descriptor.startsWith(key)) {").append(lineSeparator);
		src.append("		return (PlatformTransactionManager)multipleTransactionManagerCache.get(key);").append(lineSeparator);
		src.append("	}").append(lineSeparator);
		src.append("}").append(lineSeparator);
		
		try {
			CtClass ctClass = classPool.getCtClass(TransactionAspectSupport.class.getName());
			CtMethod ctMethod = ctClass.getDeclaredMethod("determineTransactionManager");
			
			LogUtil.info("在类[{}]的方法[{}]中动态插入以下代码：{}", TransactionAspectSupport.class.getName(), ctMethod.getMethodInfo().getName(), src);
			ctMethod.insertBefore(src.toString());
			ctClass.writeFile("target/classes");
		} catch (NotFoundException | CannotCompileException | IOException  e) {
			LogUtil.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 多数据源bean注册
	 *
	 * @author liyulin
	 * @date 2019年4月24日下午8:06:47
	 */
	public static class MultipleDataSourceRegistrar
			implements BeanFactoryAware, EnvironmentAware, ImportBeanDefinitionRegistrar {

		private Map<String, SingleDataSourceProperties> dataSources;
		private MybatisSqlLogInterceptor mybatisSqlLogInterceptor;
		private PageInterceptor pageInterceptor;
		private Environment environment;
		private ConfigurableBeanFactory beanFactory;
		/** jdbc url默认参数 */
		private String defaultJdbcUrlParams = "characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";

		public MultipleDataSourceRegistrar() {
			this.mybatisSqlLogInterceptor = new MybatisSqlLogInterceptor();
			this.pageInterceptor = buildPageInterceptor();
		}
		
		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.beanFactory = (ConfigurableBeanFactory) beanFactory;
		}

		@Override
		public void setEnvironment(Environment environment) {
			this.environment = environment;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
				BeanDefinitionRegistry registry) {
			// 获取数据源配置
			Binder binder = Binder.get(environment);
			Map<String, Object> dataSourcesMap = binder.bind(SmartProperties.PropertiesName.DATA_SOURCES, Map.class).get();
			Assert.state(CollectionUtil.isNotEmpty(dataSourcesMap), "不能找到数据源配置！");

			dataSources = new LinkedHashMap<>(dataSourcesMap.size());
			for (Map.Entry<String, Object> entry : dataSourcesMap.entrySet()) {
				dataSources.put(entry.getKey(), JSON.parseObject(JSON.toJSONString(entry.getValue()), SingleDataSourceProperties.class));
			}
			
			dynamicCreateMultipleDataSourceBeans();
		}

		/**
		 * 动态创建多数据源的bean，并注册到容器中
		 */
		private void dynamicCreateMultipleDataSourceBeans() {
			// 1、校验SingleDataSourceProperties的属性值
			for (Map.Entry<String, SingleDataSourceProperties> entry : dataSources.entrySet()) {
				SingleDataSourceProperties properties = entry.getValue();

				// 所有属性都不能为空
				boolean isAnyBlank = StringUtils.isAnyBlank(properties.getUrl(), properties.getUsername(),
						properties.getPassword(), properties.getTypeAliasesPackage(),
						properties.getMapperInterfaceLocation(), properties.getMapperXmlLocation());
				Assert.state(!isAnyBlank, SingleDataSourceProperties.class.getCanonicalName() + " attriutes存在未配置的！");
			}

			// 2、创建所有需要的bean，并加入到容器中
			dataSources.forEach((serviceName, dataSourceProperties) -> {
				// 2.1、HikariDataSource
				HikariDataSource dataSource = registerDataSource(serviceName, dataSourceProperties);

				// 2.2、SqlSessionFactoryBean
				String sqlSessionFactoryBeanName = generateBeanName(serviceName, SqlSessionFactoryBean.class.getSimpleName());
				registerSqlSessionFactoryBean(sqlSessionFactoryBeanName, dataSourceProperties, dataSource);

				// 2.3、MapperScannerConfigurer
				registerMapperScannerConfigurer(serviceName, sqlSessionFactoryBeanName, dataSourceProperties);

				// 2.4、DataSourceTransactionManager
				String transactionManagerBeanName = generateBeanName(serviceName, TRANSACTION_MANAGER_NAME);
				DataSourceTransactionManager transactionManager = registerDataSourceTransactionManager(transactionManagerBeanName, dataSourceProperties, dataSource);
				
				// 2.5、cache transaction
				cacheTransactionManager(dataSourceProperties.getTransactionBasePackages(), transactionManager);
			});
		}
		private void cacheTransactionManager(String transactionBasePackages,
				PlatformTransactionManager transactionManager) {
			if (StringUtils.isBlank(transactionBasePackages)) {
				return;
			}
			String[] transactionBasePackageArray = transactionBasePackages.split(SymbolConstants.COMMA);
			for (String transactionBasePackage : transactionBasePackageArray) {
				MultipleDataSourceAutoConfiguration.getMultipleTransactionManagerCache().putIfAbsent(transactionBasePackage,
						transactionManager);
			}
		}

		/**
		 * 创建并注册<code>HikariDataSource</code>
		 * 
		 * @param serviceName
		 * @param dataSourceProperties
		 * @return
		 */
		private HikariDataSource registerDataSource(String serviceName,
				SingleDataSourceProperties dataSourceProperties) {
			String dataSourceBeanName = generateBeanName(serviceName, HikariDataSource.class.getSimpleName());
			// 构建bean对象
			HikariDataSource dataSource = new HikariDataSource();
			String jdbcUrl = dataSourceProperties.getUrl();
			// 如果jdbcUrl没有设置参数，则用默认设置
			if (StringUtils.containsNone(jdbcUrl, SymbolConstants.QUESTION_MARK)) {
				jdbcUrl += SymbolConstants.QUESTION_MARK + defaultJdbcUrlParams;
			}
			dataSource.setJdbcUrl(jdbcUrl);
			dataSource.setUsername(dataSourceProperties.getUsername());
			dataSource.setPassword(dataSourceProperties.getPassword());
			dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
			// 注册bean
			registerBean(dataSourceBeanName, dataSource);

			return dataSource;
		}

		/**
		 * 创建并注册<code>SqlSessionFactoryBean</code>
		 * 
		 * @param beanName
		 * @param dataSourceProperties
		 * @param dataSource
		 * @return
		 */
		private SqlSessionFactoryBean registerSqlSessionFactoryBean(String beanName,
				SingleDataSourceProperties dataSourceProperties, DataSource dataSource) {
			// 构建bean对象
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(dataSource);
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			try {
				Resource[] mapperXmlLocationResources = resolver.getResources(dataSourceProperties.getMapperXmlLocation());
				sqlSessionFactoryBean.setMapperLocations(mapperXmlLocationResources);
			} catch (IOException e) {
				LogUtil.error(e.getMessage(), e);
			}
			sqlSessionFactoryBean.setPlugins(new Interceptor[] { mybatisSqlLogInterceptor, pageInterceptor });
			// 注册bean
			registerBean(beanName, sqlSessionFactoryBean);

			return sqlSessionFactoryBean;
		}

		/**
		 * 创建并注册<code>DataSourceTransactionManager</code>
		 * 
		 * @param transactionManagerBeanName
		 * @param dataSourceProperties
		 * @param dataSource
		 * @return
		 */
		private DataSourceTransactionManager registerDataSourceTransactionManager(String transactionManagerBeanName,
				SingleDataSourceProperties dataSourceProperties, DataSource dataSource) {
			// 构建bean对象
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
			// 注册bean
			registerBean(transactionManagerBeanName, dataSourceTransactionManager);

			return dataSourceTransactionManager;
		}

		/**
		 * 创建并注册<code>MapperScannerConfigurer</code>
		 * 
		 * @param serviceName
		 * @param sqlSessionFactoryBeanName
		 * @param dataSourceProperties
		 * @return
		 */
		private MapperScannerConfigurer registerMapperScannerConfigurer(String serviceName,
				String sqlSessionFactoryBeanName, SingleDataSourceProperties dataSourceProperties) {
			String mapperScannerConfigurerBeanName = generateBeanName(serviceName, MapperScannerConfigurer.class.getSimpleName());
			// 构建bean对象
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

			Properties properties = new Properties();
			properties.setProperty("IDENTITY", "MYSQL");
			properties.setProperty("notEmpty", "true");
			mapperScannerConfigurer.setProperties(properties);
			mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
			mapperScannerConfigurer.setBasePackage(dataSourceProperties.getMapperInterfaceLocation());
			mapperScannerConfigurer.setNameGenerator(new UniqueBeanNameGenerator());
			// 注册bean
			registerBean(mapperScannerConfigurerBeanName, mapperScannerConfigurer);

			return mapperScannerConfigurer;
		}

		/**
		 * 将bean注册到容器
		 * 
		 * @param beanName
		 * @param singletonObject
		 */
		private void registerBean(String beanName, Object singletonObject) {
			beanFactory.registerSingleton(beanName, singletonObject);
		}

		/**
		 * 生成bean名称
		 * 
		 * @param serviceName   服务名
		 * @param beanClassName bean类名
		 * @return
		 */
		private String generateBeanName(String serviceName, String beanClassName) {
			return serviceName + beanClassName;
		}

		/**
		 * 构建分页拦截器
		 * 
		 * @return
		 */
		private PageInterceptor buildPageInterceptor() {
			PageInterceptor pageHelper = new PageInterceptor();
			Properties p = new Properties();
			// 分页合理化参数
			p.setProperty("reasonable", "true");
			p.setProperty("supportMethodsArguments", "true");
			p.setProperty("params", "count=countSql");
			pageHelper.setProperties(p);
			return pageHelper;
		}

	}

}