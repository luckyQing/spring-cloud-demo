package com.liyulin.demo.common.web.aspect.advice;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.util.ReqHeadUtil;
import com.liyulin.demo.common.constants.SymbolConstants;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.UnitTestUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aspect.dto.FeignAspectDto;
import com.liyulin.demo.common.web.aspect.util.AspectUtil;
import com.liyulin.demo.common.web.validation.util.ValidationUtil;

/**
 * feign切面
 *
 * @author liyulin
 * @date 2019年4月21日下午3:32:33
 */
public class FeignAspectAdvice implements MethodInterceptor {

	private static ConcurrentLinkedQueue<Object> mockData = new ConcurrentLinkedQueue<>();

	public static void push(Object object) {
		mockData.add(object);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		// 1、填充head
		if (isReq(args)) {
			Req<?> req = (Req<?>) args[0];
			req.setHead(ReqHeadUtil.of());
			// TODO:填充token、sign
			
			if (UnitTestUtil.isTest()) {
				// 参数校验
				ValidationUtil.validate(req);
			}
		}

		// 如果为单元测试环境，则直接返回mock数据
		if (UnitTestUtil.isTest()) {
			return mockData.poll();
		}

		FeignAspectDto logDto = new FeignAspectDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Method method = invocation.getMethod();
		String apiDesc = AspectUtil.getFeignMethodDesc(method, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		String classMethod = method.getDeclaringClass().getTypeName() + SymbolConstants.DOT + method.getName();
		logDto.setClassMethod(classMethod);

		logDto.setRequestParams(WebUtil.getRequestArgs(args));

		// 2、rpc
		Object result = invocation.proceed();

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setResponseData(result);

		// 3、打印日志
		LogUtil.info("rpc.logDto=>{}", logDto);

		return result;
	}

	private boolean isReq(Object[] args) {
		return ObjectUtil.isNotNull(args) && args.length == 1 && ObjectUtil.isNotNull(args[0])
				&& args[0] instanceof Req;
	}

}