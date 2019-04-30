package com.liyulin.demo.common.web.aop.pointCut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.cloud.openfeign.FeignClient;

import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.common.util.ArrayUtil;

public class FeignClientPointcut implements Pointcut {

	@Override
	public ClassFilter getClassFilter() {
		return new ClassFilter() {

			@Override
			public boolean matches(Class<?> clazz) {
				Annotation[] annotations = clazz.getAnnotations();
				if (ArrayUtil.isEmpty(annotations)) {
					return false;
				}

				for (Annotation annotation : annotations) {
					if (annotation instanceof FeignClient || annotation instanceof SmartFeignClient) {
						return true;
					}
				}

				return false;
			}
		};
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new MethodMatcher() {

			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				return true;
			}

			@Override
			public boolean isRuntime() {
				return false;
			}

			@Override
			public boolean matches(Method method, Class<?> targetClass, Object... args) {
				return true;
			}

		};
	}

}