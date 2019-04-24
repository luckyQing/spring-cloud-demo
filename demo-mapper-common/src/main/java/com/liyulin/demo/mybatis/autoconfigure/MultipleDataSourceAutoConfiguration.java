package com.liyulin.demo.mybatis.autoconfigure;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;
import com.liyulin.demo.common.properties.CommonProperties;
import com.liyulin.demo.common.properties.SingleDataSourceProperties;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.mybatis.plugin.SqlLogInterceptor;
import com.zaxxer.hikari.HikariDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 多数据源配置
 *
 * @author liyulin
 * @date 2019年4月24日下午8:06:47
 */
@Configuration
@Order
public class MultipleDataSourceAutoConfiguration implements ImportBeanDefinitionRegistrar {

	@Autowired
	private CommonProperties commonProperties;
	@Autowired
	private SqlLogInterceptor sqlLogInterceptor;
	@Autowired
	private PageInterceptor pageInterceptor;
	private BeanDefinitionRegistry registry;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		this.registry = registry;
		dynamicCreateMultipleDataSourceBeans();
	}

	/**
	 * 动态创建多数据源的bean，并注册到容器中
	 */
	private void dynamicCreateMultipleDataSourceBeans() {
		// 1、获取数据源属性
		Map<String, SingleDataSourceProperties> dataSources = commonProperties.getDataSources();
		if (CollectionUtil.isEmpty(dataSources)) {
			return;
		}

		// 2、校验
		validateDatasourceAttribute(dataSources);

		// 3、创建所有需要的bean，并加入到容器中
		dataSources.forEach((serviceName, dataSourceProperties) -> {
			// 3.1、HikariDataSource
			HikariDataSource dataSource = registerDataSource(serviceName, dataSourceProperties);

			// 3.2、SqlSessionFactoryBean
			String sqlSessionFactoryBeanName = generateBeanName(serviceName, "SqlSessionFactoryName");
			registerSqlSessionFactoryBean(sqlSessionFactoryBeanName, dataSourceProperties, dataSource);

			// 3.3、DataSourceTransactionManager
			registerDataSourceTransactionManager(serviceName, dataSourceProperties, dataSource);

			// 3.4、MapperScannerConfigurer
			registerMapperScannerConfigurer(serviceName, sqlSessionFactoryBeanName, dataSourceProperties);
		});
	}

	private HikariDataSource registerDataSource(String serviceName, SingleDataSourceProperties dataSourceProperties) {
		String dataSourceBeanName = generateBeanName(serviceName, "DataSource");
		// 构建bean对象
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());
		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		// 注册bean
		registerBean(dataSourceBeanName, dataSource);

		return dataSource;
	}

	private SqlSessionFactoryBean registerSqlSessionFactoryBean(String beanName, SingleDataSourceProperties dataSourceProperties,
			DataSource dataSource) {
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
		registerBean(beanName, sqlSessionFactoryBean);
		
		return sqlSessionFactoryBean;
	}

	private DataSourceTransactionManager registerDataSourceTransactionManager(String serviceName,
			SingleDataSourceProperties dataSourceProperties, DataSource dataSource) {
		String dataSourceTransactionManagerBeanName = generateBeanName(serviceName, "DataSourceTransactionManager");
		// 构建bean对象
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		// 注册bean
		registerBean(dataSourceTransactionManagerBeanName, dataSourceTransactionManager);
		
		return dataSourceTransactionManager;
	}

	private MapperScannerConfigurer registerMapperScannerConfigurer(String serviceName, String sqlSessionFactoryBeanName,
			SingleDataSourceProperties dataSourceProperties) {
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
		registerBean(mapperScannerConfigurerBeanName, mapperScannerConfigurer);
		
		return mapperScannerConfigurer;
	}

	/**
	 * 校验{@code SingleDataSourceProperties}的属性值
	 * 
	 * @param dataSources
	 */
	private void validateDatasourceAttribute(Map<String, SingleDataSourceProperties> dataSources) {
		for (Map.Entry<String, SingleDataSourceProperties> entry : dataSources.entrySet()) {
			SingleDataSourceProperties properties = entry.getValue();
			if (StringUtils.isAnyBlank(properties.getUrl(), properties.getUsername(), properties.getPassword(),
					properties.getTypeAliasesPackage(), properties.getMapperInterfaceLocation(),
					properties.getMapperXmlLocation())) {
				throw new NullArgumentException(SingleDataSourceProperties.class.getCanonicalName() + " attriutes");
			}
		}
	}

	/**
	 * 将bean注册到容器
	 * 
	 * @param beanName
	 * @param bean
	 */
	private <T> void registerBean(String beanName, T bean) {
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
//		Field[] fields = bean.getClass().getFields();
//		for (Field field : fields) {
//			field.setAccessible(true);
//			Object fieldValue = new Object();
//			field.get(fieldValue);
//			if (ObjectUtil.isNull(fieldValue)) {
//				continue;
//			}
//			
//			builder.addPropertyValue(field.getName(), fieldValue);
//		}

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

	@Bean
	public PageInterceptor buildPageInterceptor() {
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