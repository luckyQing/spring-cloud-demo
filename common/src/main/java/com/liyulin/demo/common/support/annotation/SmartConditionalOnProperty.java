package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.support.condition.SmartConditionalOnPropertyCondition;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Conditional(SmartConditionalOnPropertyCondition.class)
public @interface SmartConditionalOnProperty {

	String prefix() default CommonConstants.SMART_PROPERTIES_PREFIX;
	
	String[] name() default {};

	String havingValue() default "true";

	boolean matchIfMissing() default false;

}