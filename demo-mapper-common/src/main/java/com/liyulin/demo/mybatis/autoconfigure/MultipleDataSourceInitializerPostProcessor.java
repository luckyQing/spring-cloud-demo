package com.liyulin.demo.mybatis.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

public class MultipleDataSourceInitializerPostProcessor implements BeanPostProcessor, Ordered {
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

	@Autowired
	private BeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof ProxyTransactionManagementConfiguration) {
			// force initialization of this bean as soon as we see a ProxyTransactionManagementConfiguration
			this.beanFactory.getBean(MultipleDataSourceInitializerInvoker.class);
		}
		return bean;
	}

}