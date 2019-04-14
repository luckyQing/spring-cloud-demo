package com.liyulin.demo.common.web.autoconfigure;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.liyulin.demo.common.properties.CommonProperties;
import com.liyulin.demo.common.web.validation.valueextraction.BasePageReqExtractor;
import com.liyulin.demo.common.web.validation.valueextraction.ReqExtractor;
import com.liyulin.demo.common.web.validation.valueextraction.ReqObjectBodyExtractor;

/**
 * Hibernate Validator校验配置
 *
 * @author liyulin
 * @date 2019年3月29日下午11:14:39
 */
@Configuration
@ConditionalOnProperty(prefix = CommonProperties.PREFIX, name = "validator", havingValue = "true", matchIfMissing = false)
public class HibernateValidatorAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@DependsOn("validator")
	public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		/** 设置validator模式为快速失败返回 */
		postProcessor.setValidator(validator);
		return postProcessor;
	}

	@Bean("validator")
	@ConditionalOnMissingBean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
				.addValueExtractor(ReqExtractor.DESCRIPTOR.getValueExtractor())
				.addValueExtractor(BasePageReqExtractor.DESCRIPTOR.getValueExtractor())
				.addValueExtractor(ReqObjectBodyExtractor.DESCRIPTOR.getValueExtractor())
				.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();

		Validator validator = validatorFactory.getValidator();
		return validator;
	}

}