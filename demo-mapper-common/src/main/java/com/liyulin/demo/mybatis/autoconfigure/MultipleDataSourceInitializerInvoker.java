package com.liyulin.demo.mybatis.autoconfigure;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.Assert;

import com.github.pagehelper.PageInterceptor;
import com.liyulin.demo.common.constants.SymbolConstants;
import com.liyulin.demo.common.support.UniqueBeanNameGenerator;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.mybatis.plugin.MybatisSqlLogInterceptor;
import com.liyulin.demo.mybatis.properties.MultipleDatasourceProperties;
import com.liyulin.demo.mybatis.properties.MultipleDatasourceProperties.SingleDatasourceProperties;
import com.zaxxer.hikari.HikariDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

public class MultipleDataSourceInitializerInvoker implements InitializingBean {

	private final MultipleDatasourceProperties multipleDatasourceProperties;
	private final ConfigurableBeanFactory beanFactory;
	/** bean名称组成部分（后缀） */
	public static final String TRANSACTION_MANAGER_NAME = "DataSourceTransactionManager";
	private MybatisSqlLogInterceptor mybatisSqlLogInterceptor;
	private PageInterceptor pageInterceptor;
	/** jdbc url默认参数 */
	private String defaultJdbcUrlParams = "characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";

	MultipleDataSourceInitializerInvoker(MultipleDatasourceProperties multipleDatasourceProperties,
			ConfigurableBeanFactory beanFactory) {
		this.multipleDatasourceProperties = multipleDatasourceProperties;
		this.beanFactory = beanFactory;
		this.mybatisSqlLogInterceptor = new MybatisSqlLogInterceptor();
		this.pageInterceptor = buildPageInterceptor();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		dynamicCreateMultipleDataSourceBeans();
	}

	/**
	 * 动态创建多数据源的bean，并注册到容器中
	 */
	private void dynamicCreateMultipleDataSourceBeans() {
		Assert.state(multipleDatasourceProperties != null && multipleDatasourceProperties.getDatasources() != null
				&& multipleDatasourceProperties.getDatasources().size() > 0, "不能找到数据源配置！");

		Map<String, SingleDatasourceProperties> dataSources = multipleDatasourceProperties.getDatasources();

		// 1、校验SingleDataSourceProperties的属性值
		for (Map.Entry<String, SingleDatasourceProperties> entry : dataSources.entrySet()) {
			SingleDatasourceProperties properties = entry.getValue();

			// 所有属性都不能为空
			boolean isAnyBlank = StringUtils.isAnyBlank(properties.getUrl(), properties.getUsername(),
					properties.getPassword(), properties.getTypeAliasesPackage(),
					properties.getMapperInterfaceLocation(), properties.getMapperXmlLocation());
			Assert.state(!isAnyBlank, SingleDatasourceProperties.class.getCanonicalName() + " attriutes存在未配置的！");
		}

		// 2、创建所有需要的bean，并加入到容器中
		dataSources.forEach((serviceName, dataSourceProperties) -> {
			// 2.1、HikariDataSource
			HikariDataSource dataSource = registerDataSource(serviceName, dataSourceProperties);

			// 2.2、SqlSessionFactoryBean
			String sqlSessionFactoryBeanName = generateBeanName(serviceName,
					SqlSessionFactoryBean.class.getSimpleName());
			registerSqlSessionFactoryBean(sqlSessionFactoryBeanName, dataSourceProperties, dataSource);

			// 2.3、MapperScannerConfigurer
			registerMapperScannerConfigurer(serviceName, sqlSessionFactoryBeanName, dataSourceProperties);

			// 2.4、DataSourceTransactionManager
			String transactionManagerBeanName = generateBeanName(serviceName, TRANSACTION_MANAGER_NAME);
			registerDataSourceTransactionManager(transactionManagerBeanName, dataSource);

			// 2.5、cache transaction info
			cacheTransactionManagerInfo(dataSourceProperties.getTransactionBasePackages(), transactionManagerBeanName);
		});

		// 事务注解value初始化
	}

	private void cacheTransactionManagerInfo(String transactionBasePackages, String transactionManagerBeanName) {
		if (StringUtils.isBlank(transactionBasePackages)) {
			return;
		}

		String[] transactionBasePackageArray = transactionBasePackages.split(SymbolConstants.COMMA);
		for (String transactionBasePackage : transactionBasePackageArray) {
			InitTransactionalValue.getMultipleTransactionManagerInfoCache().putIfAbsent(transactionBasePackage,
					transactionManagerBeanName);
		}
	}

	/**
	 * 创建并注册<code>HikariDataSource</code>
	 * 
	 * @param serviceName
	 * @param dataSourceProperties
	 * @return
	 */
	private HikariDataSource registerDataSource(String serviceName, SingleDatasourceProperties dataSourceProperties) {
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
			SingleDatasourceProperties dataSourceProperties, DataSource dataSource) {
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
	 * @param dataSource
	 * @return
	 */
	private void registerDataSourceTransactionManager(String transactionManagerBeanName, DataSource dataSource) {
		// 构建bean对象
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		// 注册bean
		registerBean(transactionManagerBeanName, dataSourceTransactionManager);
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
			String sqlSessionFactoryBeanName, SingleDatasourceProperties dataSourceProperties) {
		String mapperScannerConfigurerBeanName = generateBeanName(serviceName,
				MapperScannerConfigurer.class.getSimpleName());
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