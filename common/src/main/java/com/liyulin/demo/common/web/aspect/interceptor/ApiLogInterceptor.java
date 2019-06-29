package com.liyulin.demo.common.web.aspect.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.constants.OrderConstant;
import com.liyulin.demo.common.constants.SymbolConstant;
import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aspect.dto.LogAspectDto;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

/**
 * 接口日志切面
 *
 * @author liyulin
 * @date 2019年4月8日下午8:49:29
 */
public class ApiLogInterceptor implements MethodInterceptor, Ordered {

	@Override
	public int getOrder() {
		return OrderConstant.API_LOG;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 请求前
		if (ObjectUtil.isNull(RequestContextHolder.getRequestAttributes())) {
			return invocation.proceed();
		}
		LogAspectDto logDto = new LogAspectDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Method method = invocation.getMethod();
		String apiDesc = AspectInterceptorUtil.getControllerMethodDesc(method, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		logDto.setReqParams(WebUtil.getRequestArgs(invocation.getArguments()));
		logDto.setReqHttpHeaders(ReqHttpHeadersUtil.getReqHttpHeadersDto());

		logDto.setUrl(request.getRequestURL().toString());
		logDto.setIp(WebUtil.getRealIP(request));
		logDto.setOs(request.getHeader("User-Agent"));
		logDto.setHttpMethod(request.getMethod());

		String classMethod = method.getDeclaringClass().getTypeName() + SymbolConstant.DOT + method.getName();
		logDto.setClassMethod(classMethod);

		// 处理请求
		Object result = null;
		try {
			result = invocation.proceed();
			// 正常请求后
			logDto.setReqEndTime(new Date());
			logDto.setReqDealTime(getReqDealTime(logDto));
			logDto.setRespData(result);

			LogUtil.info("api.logDto.info=>{}", logDto);
			return result;
		} catch (Exception e) {
			logDto.setReqEndTime(new Date());
			logDto.setReqDealTime(getReqDealTime(logDto));
			logDto.setExceptionStackInfo(ExceptionUtil.toString(e));

			LogUtil.error("api.logDto.error=>{}", logDto);

			RespHead head = ExceptionUtil.parse(e);
			return new Resp<>(head);
		}
	}

	private final int getReqDealTime(LogAspectDto logDto) {
		return (int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime());
	}

}