package com.liyulin.demo.common.business.test;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.liyulin.demo.common.condition.OnTestCondition;
import com.liyulin.demo.common.constants.CommonConstants;

@Aspect
@Component
@Conditional(OnTestCondition.class)
public class FiegnMockAspect {

	private static ConcurrentLinkedQueue<Object> mockData = new ConcurrentLinkedQueue<>();

	public static void push(Object object) {
		mockData.add(object);
	}

	@Around(CommonConstants.FEIGN_MOCK_AOP_EXECUTION)
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		if (OnTestCondition.isTest()) {
			return mockData.poll();
		}

		return jp.proceed();
	}

}