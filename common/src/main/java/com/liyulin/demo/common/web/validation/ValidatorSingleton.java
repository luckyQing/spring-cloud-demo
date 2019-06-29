package com.liyulin.demo.common.web.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

import com.liyulin.demo.common.web.validation.valueextraction.BasePageReqExtractor;
import com.liyulin.demo.common.web.validation.valueextraction.ReqExtractor;
import com.liyulin.demo.common.web.validation.valueextraction.ReqObjectBodyExtractor;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorSingleton {

	public static Validator getInstance() {
		return Holder.INSTANCE.getValidator();
	}

	@Getter
	public enum Holder {
		INSTANCE;
		private Validator validator;

		private Holder() {
			ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
					.addValueExtractor(ReqExtractor.DESCRIPTOR.getValueExtractor())
					.addValueExtractor(BasePageReqExtractor.DESCRIPTOR.getValueExtractor())
					.addValueExtractor(ReqObjectBodyExtractor.DESCRIPTOR.getValueExtractor())
					.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();
			validator = validatorFactory.getValidator();
		}
	}

}