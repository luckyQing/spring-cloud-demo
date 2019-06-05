package com.liyulin.demo.common.web.aspect.advice;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.constants.SymbolConstants;
import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aspect.dto.LogAspectDto;
import com.liyulin.demo.common.web.aspect.util.AspectUtil;

/**
 * 日志切面
 *
 * @author liyulin
 * @date 2019年4月8日下午8:49:29
 */
public class LogAspectAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	private ThreadLocal<LogAspectDto> logDtoThreadLocal = new ThreadLocal<>();

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (ObjectUtil.isNull(RequestContextHolder.getRequestAttributes())) {
			return;
		}
		LogAspectDto logDto = new LogAspectDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String apiDesc = AspectUtil.getControllerMethodDesc(method, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		logDto.setReqParams(WebUtil.getRequestArgs(args));

		logDto.setUrl(request.getRequestURL().toString());
		logDto.setIp(WebUtil.getRealIP(request));
		logDto.setOs(request.getHeader("User-Agent"));
		logDto.setHttpMethod(request.getMethod());
		
		String classMethod = method.getDeclaringClass().getTypeName() + SymbolConstants.DOT + method.getName();
		logDto.setClassMethod(classMethod);

		logDtoThreadLocal.set(logDto);
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		LogAspectDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setRespData(returnValue);

		LogUtil.info("api.logDto.info=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		logDtoThreadLocal.remove();
	}

	/**
	 * 异常切面
	 * 
	 * @param e
	 * @since {@link ThrowsAdvice}
	 */
	public void afterThrowing(Exception e) {
		LogAspectDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setExceptionStackInfo(ExceptionUtil.toString(e));

		LogUtil.error("api.logDto.error=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		logDtoThreadLocal.remove();
	}

}