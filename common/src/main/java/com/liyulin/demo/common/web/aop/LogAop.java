package com.liyulin.demo.common.web.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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
	private ThreadLocal<LogDto> logDtoThreadLocal = new ThreadLocal<>();
	private ConcurrentMap<String, String> apiDescMap = new ConcurrentHashMap<>();

	@Pointcut(POINTCUT)
	public void aopLog() {
	}

	@Before(value = AOP_METHOD_NAME)
	public void doBefore(JoinPoint joinPoint) {
		if (null == RequestContextHolder.getRequestAttributes()) {
			return;
		}
		LogDto logDto = new LogDto();
		logDto.setReqStartTime(new Date());

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		String apiDesc = getApiDesc(joinPoint, request.getServletPath());
		logDto.setApiDesc(apiDesc);

		Object[] args = joinPoint.getArgs();
		logDto.setRequestParams(filterArgs(args));

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

	private String getApiDesc(JoinPoint joinPoint, String path) {
		// 先从缓存取
		String apiDesc = apiDescMap.get(path);
		if (ObjectUtil.isNotNull(apiDesc)) {
			return apiDesc;
		}

		// 缓存没有，则通过反射获取
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		ApiOperation operation = method.getAnnotation(ApiOperation.class);
		apiDesc = ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY;
		if (StringUtils.isBlank(apiDesc)) {
			// 如果为空，则从接口类rpc取
			Object controller = joinPoint.getTarget();
			Class<?> controllerClass = controller.getClass();
			Class<?>[] interfaces = controllerClass.getInterfaces();
			if (ArrayUtil.isNotEmpty(interfaces)) {
				Class<?> rpcClass = interfaces[0];
				Method[] methods = rpcClass.getMethods();
				for (Method rpcMethod : methods) {
					if (isSameMethod(rpcMethod, method)) {
						operation = rpcMethod.getAnnotation(ApiOperation.class);
						apiDesc = ObjectUtil.isNotNull(operation) ? operation.value() : StringUtils.EMPTY;
						break;
					}
				}
			}
		}

		apiDescMap.putIfAbsent(path, apiDesc);

		return apiDesc;
	}

	/**
	 * 是否是同一个method
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean isSameMethod(Method a, Method b) {
		return (a.getReturnType() == b.getReturnType()) && ObjectUtil.equals(a.getName(), b.getName());
	}

	/**
	 * 获取有效的请求参数（过滤掉不能序列化的）
	 * 
	 * @param args
	 * @return
	 */
	private Object filterArgs(Object[] args) {
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
			return args.length == 1 ? args[0] : args;
		}

		Object[] tempArgs = Stream.of(args).filter(arg -> {
			return !needFilter(arg);
		}).toArray();

		return getValidArgs(tempArgs);
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

	/**
	 * 获取有效的参数（如果是request对象，则优先从ParameterMap里取）
	 * 
	 * @param args
	 * @return
	 */
	private Object getValidArgs(Object[] args) {
		if (ArrayUtil.isEmpty(args)) {
			return args;
		}

		if (args.length == 1 && args[0] instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) args[0];
			return request.getParameterMap();
		}

		return args;
	}

}