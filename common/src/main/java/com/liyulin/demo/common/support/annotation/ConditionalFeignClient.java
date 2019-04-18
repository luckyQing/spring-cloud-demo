package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

import com.liyulin.demo.common.condition.OnFeignClientCondition;

import springfox.documentation.annotations.ApiIgnore;

/**
 * {@link FeignClient}自定义条件封装
 *
 * @author liyulin
 * @date 2019年3月22日下午2:42:14
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Conditional(OnFeignClientCondition.class)
@FeignClient
@ApiIgnore
public @interface ConditionalFeignClient {

	@AliasFor(annotation = FeignClient.class, attribute = "value")
	String value() default "";

	@AliasFor(annotation = FeignClient.class, attribute = "url")
	String url() default "";

	@AliasFor(annotation = FeignClient.class, attribute = "fallback")
	Class<?> fallback() default void.class;

}
