package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.liyulin.demo.common.support.condition.ConditionalOnPropertyBooleanCondition;

/**
 * Boolean值属性（默认值：true）条件判断注解
 * 
 * @author liyulin
 * @date 2019年6月14日 下午7:44:13
 * @since ConditionalOnProperty
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Conditional(ConditionalOnPropertyBooleanCondition.class)
public @interface ConditionalOnPropertyBoolean {

	String prefix() default "";
	
	String[] name() default {};

	String havingValue() default "true";

	boolean matchIfMissing() default false;

}