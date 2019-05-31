package com.liyulin.demo.common.web.validation.util;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.liyulin.demo.common.business.exception.ParamValidateError;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.SpringUtil;

import lombok.experimental.UtilityClass;

/**
 * 参数校验
 *
 * @author liyulin
 * @date 2019年5月1日下午12:34:50
 */
@UtilityClass
public class ValidationUtil {

	/**
	 * 参数校验
	 * 
	 * @param object
	 */
	public static <T> void validate(T object) {
		if (object == null) {
			throw new ParamValidateError();
		}

		Validator validator = SpringUtil.getBean(Validator.class);
		Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(object);
		// 抛出检验异常
		if (CollectionUtil.isNotEmpty(constraintViolationSet)) {
			Set<ConstraintViolation<?>> constraintViolationSetTmp = constraintViolationSet.stream()
					.map(item -> (ConstraintViolation<?>) (item)).collect(Collectors.toSet());

			String errorMsg = ExceptionUtil.getErrorMsg(constraintViolationSetTmp);
			throw new ParamValidateError(errorMsg);
		}
	}

}