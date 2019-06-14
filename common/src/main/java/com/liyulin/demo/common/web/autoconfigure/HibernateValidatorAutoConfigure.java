package com.liyulin.demo.common.web.autoconfigure;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;
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
@ConditionalOnPropertyBoolean(name = SmartProperties.PropertiesName.VALIDATOR)
public class HibernateValidatorAutoConfigure {

	@Bean
	@ConditionalOnMissingBean
	public MethodValidationPostProcessor methodValidationPostProcessor(final Validator validator) {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		/** 设置validator模式为快速失败返回 */
		postProcessor.setValidator(validator);
		return postProcessor;
	}

	@Bean
	@ConditionalOnMissingBean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
				.addValueExtractor(ReqExtractor.DESCRIPTOR.getValueExtractor())
				.addValueExtractor(BasePageReqExtractor.DESCRIPTOR.getValueExtractor())
				.addValueExtractor(ReqObjectBodyExtractor.DESCRIPTOR.getValueExtractor())
				.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();

		return validatorFactory.getValidator();
	}

}