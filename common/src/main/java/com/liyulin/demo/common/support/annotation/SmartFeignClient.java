package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

import com.liyulin.demo.common.support.condition.SmartFeignClientCondition;

import springfox.documentation.annotations.ApiIgnore;

/**
 * <code>FeignClient</code>自定义条件封装
 *
 * @author liyulin
 * @date 2019年3月22日下午2:42:14
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@FeignClient
@Conditional(SmartFeignClientCondition.class)
@ApiIgnore
public @interface SmartFeignClient {

	@AliasFor(annotation = FeignClient.class)
	String name() default "";

	@AliasFor(annotation = FeignClient.class)
	String url() default "";

	@AliasFor(annotation = FeignClient.class)
	Class<?> fallback() default void.class;

}