package com.liyulin.demo.common.web.aop;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.util.ReqHeadUtil;
import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.TestUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aop.dto.FeignAspectDto;
import com.liyulin.demo.common.web.aop.util.AspectUtil;

/**
 * feign切面
 *
 * @author liyulin
 * @date 2019年4月21日下午3:32:33
 */
@Aspect
@Component
public class FeignAspect {

	private static ConcurrentLinkedQueue<Object> mockData = new ConcurrentLinkedQueue<>();

	public static void push(Object object) {
		mockData.add(object);
	}

	@Around(CommonConstants.FEIGN_MOCK_AOP_EXECUTION)
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 如果为单元测试环境，则直接返回mock数据
		if (TestUtil.isTestEnv()) {
			return mockData.poll();
		}
		
		FeignAspectDto logDto = new FeignAspectDto();
		logDto.setReqStartTime(new Date());
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String apiDesc = AspectUtil.getFeignMethodDesc(joinPoint, request.getServletPath());
		logDto.setApiDesc(apiDesc);
		
		Signature signature = joinPoint.getSignature();
		String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
		logDto.setClassMethod(classMethod);

		// 1、填充head
		Object[] args = joinPoint.getArgs();
		if (ObjectUtil.isNotNull(args) 
				&& args.length == 1 
				&& ObjectUtil.isNotNull(args[0]) 
				&& args[0] instanceof Req) {
			Req<?> req = (Req<?>) args[0];
			req.setHead(ReqHeadUtil.of());
			// TODO:填充token、sign
		}
		
		logDto.setRequestParams(WebUtil.getRequestArgs(args));

		// 2、rpc
		Object result = joinPoint.proceed();
		
		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setResponseData(result);
		
		// 3、打印日志
		LogUtil.info("rpc.logDto=>{}", logDto);

		return result;
	}

}