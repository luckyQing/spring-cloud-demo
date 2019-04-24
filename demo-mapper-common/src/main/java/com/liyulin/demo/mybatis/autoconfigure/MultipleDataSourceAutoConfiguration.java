package com.liyulin.demo.mybatis.autoconfigure;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;
import com.liyulin.demo.common.properties.CommonProperties;
import com.liyulin.demo.common.properties.SingleDataSourceProperties;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.mybatis.plugin.SqlLogInterceptor;
import com.zaxxer.hikari.HikariDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class MultipleDataSourceAutoConfiguration implements ImportBeanDefinitionRegistrar {

	@Autowired
	private CommonProperties commonProperties;
	@Autowired
	private SqlLogInterceptor sqlLogInterceptor;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		dynamicCreateMultipleDataSourceBeans(registry);
	}

	/**
	 * 参考{@linkplain https://stackoverflow.com/questions/44777506/spring-boot-register-an-instance-as-a-bean}
	 * {@linkplain https://stackoverflow.com/questions/25160221/how-do-i-create-beans-programmatically-in-spring-boot}
	 */
	private void dynamicCreateMultipleDataSourceBeans(BeanDefinitionRegistry registry) {
		// 1、解析“application-db-*.yml”
//		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//		// 所有的数据源配置
//		Map<String, MultipleDataSourceProperties> dataSources = new HashMap<>();
//		try {
//			Resource[] resources = resourcePatternResolver.getResources("classpath*:/application-db-*.yml");
//			for (Resource resource : resources) {
//				Yaml yaml = new Yaml();
//				InputStream inputStream = resource.getInputStream();
//				Map<String, Object> yamlJson = yaml.load(inputStream);
//				// <服务名, 数据库连接配置>
//				Map<String, MultipleDataSourceProperties> dataSource = (Map<String, MultipleDataSourceProperties>) yamlJson
//						.get("spring.datasource");
//				dataSources.putAll(dataSource);
//
//				inputStream.close();
//			}
//		} catch (IOException e) {
//			LogUtil.error(e.getMessage(), e);
//		}
		Map<String, SingleDataSourceProperties> dataSources = commonProperties.getDataSources();
		if (dataSources == null) {
			return;
		}

		PageInterceptor pageInterceptor = buildPageInterceptor();
		// 2、创建所有需要的bean，并加入到容器中
		dataSources.forEach((serviceName, dataSourceProperties) -> {
			// 2.1、HikariDataSource
			String dataSourceBeanName = generateBeanName(serviceName, "HikariDataSource");
			// 构建bean对象
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setUsername(dataSourceProperties.getUsername());
			dataSource.setPassword(dataSourceProperties.getPassword());
			dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
			// 注册bean
			registerBean(dataSourceBeanName, dataSource, registry);

			// 2.2、SqlSessionFactoryBean
			String sqlSessionFactoryBeanName = generateBeanName(serviceName, "SqlSessionFactoryName");
			// 构建bean对象
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(dataSource);
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			try {
				sqlSessionFactoryBean
						.setMapperLocations(resolver.getResources(dataSourceProperties.getMapperXmlLocation()));
			} catch (IOException e) {
				LogUtil.error(e.getMessage(), e);
			}
			sqlSessionFactoryBean.setPlugins(new Interceptor[] { sqlLogInterceptor, pageInterceptor });
			// 注册bean
			registerBean(sqlSessionFactoryBeanName, sqlSessionFactoryBean, registry);

			// 2.3、DataSourceTransactionManager
			String dataSourceTransactionManagerBeanName = generateBeanName(serviceName, "DataSourceTransactionManager");
			// 构建bean对象
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
			// 注册bean
			registerBean(dataSourceTransactionManagerBeanName, dataSourceTransactionManager, registry);

			// 2.4、MapperScannerConfigurer
			String mapperScannerConfigurerBeanName = generateBeanName(serviceName, "MapperScannerConfigurer");
			// 构建bean对象
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

			Properties properties = new Properties();
			properties.setProperty("IDENTITY", "MYSQL");
			properties.setProperty("notEmpty", "true");
			mapperScannerConfigurer.setProperties(properties);
			mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
			mapperScannerConfigurer.setBasePackage(dataSourceProperties.getMapperInterfaceLocation());
			// 注册bean
			registerBean(mapperScannerConfigurerBeanName, mapperScannerConfigurer, registry);

		});
	}

	/**
	 * 将bean注册到容器
	 * 
	 * @param beanName
	 * @param bean
	 * @param registry
	 */
	private <T> void registerBean(String beanName, T bean, BeanDefinitionRegistry registry) {
		BeanDefinition beanDefinition = null;
		try {
			beanDefinition = buildBeanDefinition(bean);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LogUtil.error(e.getMessage(), e);
		}
		registry.registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * 构造{@code BeanDefinition}对象
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private <T> BeanDefinition buildBeanDefinition(T bean) throws IllegalArgumentException, IllegalAccessException {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(bean.getClass());
		Field[] fields = bean.getClass().getFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldValue = new Object();
			field.get(fieldValue);
			if (ObjectUtil.isNull(fieldValue)) {
				continue;
			}
			builder.addPropertyValue(field.getName(), fieldValue);
		}

		return builder.getBeanDefinition();
	}

	/**
	 * 生成bean名称
	 * 
	 * @param serviceName   服务名
	 * @param beanClassName bean的类名
	 * @return
	 */
	private String generateBeanName(String serviceName, String beanClassName) {
		return serviceName + beanClassName;
	}

	private PageInterceptor buildPageInterceptor() {
		PageInterceptor pageHelper = new PageInterceptor();
		Properties p = new Properties();
		p.setProperty("dialect", "mysql");
		// 分页合理化参数
		p.setProperty("reasonable", "true");
		p.setProperty("supportMethodsArguments", "true");
		p.setProperty("params", "count=countSql");
		pageHelper.setProperties(p);
		return pageHelper;
	}

}