package com.liyulin.demo.common.web.aspect.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;

import com.liyulin.demo.common.constants.OrderConstant;

/**
 * 重复提交校验拦截器
 * 
 * @author liyulin
 * @date 2019年6月13日 上午9:24:18
 */
public class RepeatSubmitCheckInterceptor implements MethodInterceptor, Ordered {

	@Override
	public int getOrder() {
		return OrderConstant.REPEAT_SUBMIT_CHECK;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return invocation.proceed();
	}

}