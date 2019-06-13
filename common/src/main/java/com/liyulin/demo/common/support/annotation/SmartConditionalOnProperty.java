package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

import com.liyulin.demo.common.constants.CommonConstants;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
@ConditionalOnProperty
public @interface SmartConditionalOnProperty {

	@AliasFor(annotation = ConditionalOnProperty.class)
	String prefix() default CommonConstants.SMART_PROPERTIES_PREFIX;

	@AliasFor(annotation = ConditionalOnProperty.class, value="name")
	String[] name() default {};
	
	@AliasFor(annotation = ConditionalOnProperty.class)
	String[] value() default {};

	@AliasFor(annotation = ConditionalOnProperty.class)
	String havingValue() default "true";

	@AliasFor(annotation = ConditionalOnProperty.class)
	boolean matchIfMissing() default false;

}