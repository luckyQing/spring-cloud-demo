package com.liyulin.demo.common.web.autoconfigure;

import javax.validation.Validator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.liyulin.demo.common.web.validation.ValidatorSingleton;

/**
 * Hibernate Validator校验配置
 *
 * @author liyulin
 * @date 2019年3月29日下午11:14:39
 */
@Configuration
@ConditionalOnProperty(name = "smart.api.validator", havingValue = "true")
public class HibernateValidatorAutoConfigure {

	@Bean
	@ConditionalOnMissingBean
	public MethodValidationPostProcessor methodValidationPostProcessor(final Validator validator) {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		/** 设置validator模式为快速失败返回 */
		postProcessor.setValidator(ValidatorSingleton.getInstance());
		return postProcessor;
	}

}