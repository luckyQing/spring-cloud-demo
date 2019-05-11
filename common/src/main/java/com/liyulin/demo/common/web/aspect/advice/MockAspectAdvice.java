package com.liyulin.demo.common.web.aspect.advice;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.liyulin.demo.common.business.mock.MockUtil;

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
		Type returnType = m.getGenericReturnType();

		// 泛型
		if (returnType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) returnType;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			return MockUtil.mock(m.getReturnType(), actualTypeArguments);
		} else {
			return MockUtil.mock(m.getReturnType());
		}
	}

}