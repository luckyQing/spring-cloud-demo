package com.liyulin.demo.common.web.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.liyulin.demo.common.web.validation.constraints.MaxPast.List;
import com.liyulin.demo.common.web.validation.constraintvalidators.MaxPastValidator;

/**
 * 时间校验注解（用于判断否个请求是否在过去的某个时间范围内）
 *
 * @author liyulin
 * @date 2019年4月15日上午11:10:16
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxPastValidator.class)
@Documented
@Repeatable(List.class)
public @interface MaxPast {

	/** 默认1分钟（单位：毫秒） */
	long value() default 60000;

	String message() default "请求时间错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ FIELD, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		MaxPast[] value();
	}

}