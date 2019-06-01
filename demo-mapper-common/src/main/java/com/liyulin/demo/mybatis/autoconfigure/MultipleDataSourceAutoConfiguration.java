package com.liyulin.demo.mybatis.autoconfigure;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyulin.demo.mybatis.properties.MultipleDatasourceProperties;

import lombok.Getter;

/**
 * 多数据源配置
 * 
 * @author liyulin
 * @date 2019年5月28日 下午7:58:14
 * @since DataSourceAutoConfiguration
 * @since DataSourceInitializationConfiguration
 */
@Configuration
@EnableConfigurationProperties(MultipleDatasourceProperties.class)
@EnableTransactionManagement
@Import({ MultipleDataSourceInitializerInvoker.class, MultipleDataSourceAutoConfiguration.Registrar.class })
public class MultipleDataSourceAutoConfiguration {

	static class Registrar implements ImportBeanDefinitionRegistrar {

		private static final String BEAN_NAME = MultipleDataSourceInitializerPostProcessor.class.getSimpleName();
		@Getter
		private static BeanDefinitionRegistry registry;

		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
				BeanDefinitionRegistry registry) {
			if (!registry.containsBeanDefinition(BEAN_NAME)) {
				MultipleDataSourceAutoConfiguration.Registrar.registry = registry;
				
				GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
				beanDefinition.setBeanClass(MultipleDataSourceInitializerPostProcessor.class);
				beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				// We don't need this one to be post processed otherwise it can cause a
				// cascade of bean instantiation that we would rather avoid.
				beanDefinition.setSynthetic(true);
				registry.registerBeanDefinition(BEAN_NAME, beanDefinition);
			}
		}

	}

}