package com.liyulin.demo.common.web.aop.advice;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.liyulin.demo.common.util.MockUtil;

/**
 * mock切面
 *
 * @author liyulin
 * @date 2019年4月21日下午3:32:44
 */
public class MockAspectAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method m = invocation.getMethod();
		ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();
		Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
		
		return MockUtil.mock(m.getReturnType(), parameterizedType.getActualTypeArguments()[0]);
	}

}