package com.liyulin.demo.common.web.aspect.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.constants.SymbolConstant;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aspect.dto.FeignAspectDto;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

/**
 * feign切面
 *
 * @author liyulin
 * @date 2019年4月21日下午3:32:33
 */
public class FeignInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		// 1、填充head
		// TODO:填充token、sign

		FeignAspectDto logDto = new FeignAspectDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Method method = invocation.getMethod();
		String apiDesc = AspectInterceptorUtil.getFeignMethodDesc(method, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		String classMethod = method.getDeclaringClass().getTypeName() + SymbolConstant.DOT + method.getName();
		logDto.setClassMethod(classMethod);

		logDto.setReqParams(WebUtil.getRequestArgs(args));
		logDto.setReqHttpHeaders(ReqHttpHeadersUtil.getReqHttpHeadersDto());

		// 2、rpc
		Object result = invocation.proceed();

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setRespData(result);

		// 3、打印日志
		LogUtil.info("rpc.logDto=>{}", logDto);

		return result;
	}

}