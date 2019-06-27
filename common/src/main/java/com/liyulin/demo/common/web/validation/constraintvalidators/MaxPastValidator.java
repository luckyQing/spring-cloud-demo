package com.liyulin.demo.common.web.validation.constraintvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.liyulin.demo.common.web.validation.constraints.MaxPast;

/**
 * 时间校验
 *
 * @author liyulin
 * @date 2019年4月15日上午11:10:04
 */
public class MaxPastValidator implements ConstraintValidator<MaxPast, Long> {
	
	private long maxValue;

	@Override
	public void initialize(MaxPast constraintAnnotation) {
		this.maxValue = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		long currentTimeMillis = System.currentTimeMillis();
		return currentTimeMillis - value >= 0 && currentTimeMillis - value <= maxValue;
	}

}