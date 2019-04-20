package com.liyulin.demo.common.web.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aop.dto.LogAspectDto;
import com.liyulin.demo.common.web.aop.util.AspectUtil;

/**
 * 日志切面
 *
 * @author liyulin
 * @date 2019年4月8日下午8:49:29
 */
@Aspect
@Component
public class LogAspect {

	/** 切面方法名 */
	private static final String AOP_METHOD_NAME = "aopLog()";
	private ThreadLocal<LogAspectDto> logDtoThreadLocal = new ThreadLocal<>();

	@Pointcut(CommonConstants.LOG_AOP_EXECUTION)
	public void aopLog() {
	}

	@Before(value = AOP_METHOD_NAME)
	public void doBefore(JoinPoint joinPoint) {
		if (null == RequestContextHolder.getRequestAttributes()) {
			return;
		}
		LogAspectDto logDto = new LogAspectDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String apiDesc = AspectUtil.getControllerMethodDesc(joinPoint, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		Object[] args = joinPoint.getArgs();
		logDto.setRequestParams(WebUtil.getRequestArgs(args));

		logDto.setUrl(request.getRequestURL().toString());
		logDto.setIp(WebUtil.getRealIP(request));
		logDto.setOs(request.getHeader("User-Agent"));
		logDto.setHttpMethod(request.getMethod());

		Signature signature = joinPoint.getSignature();
		String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
		logDto.setClassMethod(classMethod);

		logDtoThreadLocal.set(logDto);
	}

	@AfterReturning(returning = "object", pointcut = AOP_METHOD_NAME)
	public void doAfterReturning(Object object) throws Throwable {
		LogAspectDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setResponseData(object);

		LogUtil.info("api.logDto.info=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		clearThreadLocal();
	}

	@AfterThrowing(throwing = "e", pointcut = AOP_METHOD_NAME)
	public void doAfterThrowing(Throwable e) {
		LogAspectDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setExceptionStackInfo(ExceptionUtil.toString(e));

		LogUtil.error("api.logDto.error=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		clearThreadLocal();
	}

	private void clearThreadLocal() {
		logDtoThreadLocal.remove();
	}

}