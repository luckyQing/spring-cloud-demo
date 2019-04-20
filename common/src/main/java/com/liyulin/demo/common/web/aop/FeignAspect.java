package com.liyulin.demo.common.web.aop;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.util.ReqHeadUtil;
import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.TestUtil;
import com.liyulin.demo.common.util.WebUtil;

@Aspect
@Component
public class FeignAspect {

	private static ConcurrentLinkedQueue<Object> mockData = new ConcurrentLinkedQueue<>();

	public static void push(Object object) {
		mockData.add(object);
	}

	@Around(CommonConstants.FEIGN_MOCK_AOP_EXECUTION)
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		if (TestUtil.isTestEnv()) {
			return mockData.poll();
		}

		// 1、填充head
		Object[] args = jp.getArgs();
		if (ObjectUtil.isNotNull(args) 
				&& args.length == 1 
				&& ObjectUtil.isNotNull(args[0]) 
				&& args[0] instanceof Req) {
			Req<?> req = (Req<?>) args[0];
			req.setHead(ReqHeadUtil.of());
			// TODO:填充token、sign
		}

		// 2、打印请求参数
		LogUtil.info("rpc.args={}", WebUtil.getRequestArgs(args));

		Object result = jp.proceed();

		// 3、打印返回参数
		if (result instanceof Serializable) {
			LogUtil.info("rpc.result={}", result);
		}

		return result;
	}

}