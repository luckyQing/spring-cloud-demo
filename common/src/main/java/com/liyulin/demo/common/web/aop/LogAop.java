package com.liyulin.demo.common.web.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.stream.Stream;

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

import com.liyulin.demo.common.util.ArrayUtil;
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

	/** 切面 */
	private static final String POINTCUT = "execution( * com.liyulin.demo..controller..*.*(..))";
	/** 切面方法名 */
	private static final String AOP_METHOD_NAME = "aopLog()";
	private static ThreadLocal<LogDto> logDtoThreadLocal = new ThreadLocal<>();

	@Pointcut(POINTCUT)
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
		logDto.setUrl(request.getRequestURL().toString());

		Object[] args = joinPoint.getArgs();
		logDto.setRequestParams(getValidArgs(args));
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

	/**
	 * 获取有效的请求参数（过滤掉不能序列化的）
	 * 
	 * @param args
	 * @return
	 */
	private Object[] getValidArgs(Object[] args) {
		if (ArrayUtil.isEmpty(args)) {
			return args;
		}

		boolean needFilter = false;
		for (Object arg : args) {
			if (needFilter(arg)) {
				needFilter = true;
				break;
			}
		}

		if (!needFilter) {
			return args;
		}

		return Stream.of(args).filter(arg -> {
			return !needFilter(arg);
		}).toArray();
	}

	/**
	 * 是否需要过滤
	 * 
	 * @param object
	 * @return
	 */
	private boolean needFilter(Object object) {
		return !(object instanceof Serializable);
	}

}