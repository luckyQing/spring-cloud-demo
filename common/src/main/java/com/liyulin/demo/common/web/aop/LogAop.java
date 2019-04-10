package com.liyulin.demo.common.web.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.util.ExceptionUtil;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.aop.dto.LogDto;

import io.swagger.annotations.ApiOperation;

/**
 * 日志切面
 *
 * @author liyulin
 * @date 2019年4月8日下午8:49:29
 */
@Aspect
@Component
public class LogAop {

	/** 切面方法名 */
	private static final String AOP_METHOD_NAME = "aopLog()";
	private static ThreadLocal<LogDto> logDtoThreadLocal = new ThreadLocal<>();

	@Pointcut("execution( * com.liyulin.demo..controller..*.*(..))")
	public void aopLog() {
	}

	@Before(value = AOP_METHOD_NAME)
	public void doBefore(JoinPoint joinPoint) {
		if (null == RequestContextHolder.getRequestAttributes()) {
			return;
		}

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		ApiOperation operation = method.getAnnotation(ApiOperation.class);
		LogDto logDto = new LogDto();
		logDto.setApiDesc(ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY);

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		ServletInputStream inputStream = request.getInputStream();
		logDto.setUrl(request.getRequestURL().toString());
		logDto.setRequestParams(ReqParameterUtil.getReqParams(request));
		logDto.setIp(WebUtil.getRealIP(request));
		logDto.setOs(request.getHeader("User-Agent"));
		logDto.setHttpMethod(request.getMethod());
		
		String classMethod = joinPoint.getSignature().getDeclaringType().getTypeName();
		logDto.setClassMethod(classMethod);
		logDto.setReqStartTime(new Date());

		logDtoThreadLocal.set(logDto);
	}

	@AfterReturning(returning = "object", pointcut = AOP_METHOD_NAME)
	public void doAfterReturning(Object object) throws Throwable {
		LogDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setResponseData(object);

		LogUtil.info("logDto.info=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		clearThreadLocal();
	}

	@AfterThrowing(throwing = "e", pointcut = AOP_METHOD_NAME)
	public void doAfterThrowing(Throwable e) {
		LogUtil.error(e.getMessage(), e);

		LogDto logDto = logDtoThreadLocal.get();
		if (ObjectUtil.isNull(logDto)) {
			return;
		}

		logDto.setReqEndTime(new Date());
		logDto.setReqDealTime((int) (logDto.getReqEndTime().getTime() - logDto.getReqStartTime().getTime()));
		logDto.setExceptionStackInfo(ExceptionUtil.toString(e));

		LogUtil.error("logDto.error=>{}", logDto);
		// 使用完释放掉，防止内存泄露
		clearThreadLocal();
	}

	private void clearThreadLocal() {
		logDtoThreadLocal.remove();
	}

}