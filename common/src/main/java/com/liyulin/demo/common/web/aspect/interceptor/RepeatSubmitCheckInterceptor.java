package com.liyulin.demo.common.web.aspect.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;

import com.alibaba.fastjson.JSON;
import com.liyulin.demo.common.business.exception.RepeatSubmitException;
import com.liyulin.demo.common.business.security.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.constants.OrderConstant;
import com.liyulin.demo.common.constants.RedisKeyPrefix;
import com.liyulin.demo.common.redis.RedisComponent;
import com.liyulin.demo.common.util.WebUtil;
import com.liyulin.demo.common.web.annotation.RepeatReqValidate;

import lombok.AllArgsConstructor;

/**
 * 重复提交校验拦截器
 * 
 * @author liyulin
 * @date 2019年6月13日 上午9:24:18
 */
@AllArgsConstructor
public class RepeatSubmitCheckInterceptor implements MethodInterceptor, Ordered {

	private RedisComponent redisComponent;

	@Override
	public int getOrder() {
		return OrderConstant.REPEAT_SUBMIT_CHECK;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String token = ReqHttpHeadersUtil.getTokenable();
		if (StringUtils.isBlank(token)) {
			return invocation.proceed();
		}

		Method method = invocation.getMethod();
		RepeatReqValidate validate = method.getAnnotation(RepeatReqValidate.class);
		if (null == validate) {
			return invocation.proceed();
		}

		String repeatSubmitCheckRedisKey = getRepeatSubmitCheckRedisKey(token);
		boolean success = false;
		try {
			Object reqObject = WebUtil.getRequestArgs(invocation.getArguments());
			if (reqObject != null) {
				String reqString = JSON.toJSONString(reqObject);
				success = redisComponent.setNx(repeatSubmitCheckRedisKey, reqString, validate.expireMillis());
				if (success) {
					return invocation.proceed();
				} else {
					throw new RepeatSubmitException(validate.message());
				}
			}
		} finally {
			if (success) {
				redisComponent.delete(repeatSubmitCheckRedisKey);
			}
		}

		return invocation.proceed();
	}

	/**
	 * 重复提交校验redis key
	 * 
	 * @param token
	 * @return
	 */
	private String getRepeatSubmitCheckRedisKey(String token) {
		return RedisKeyPrefix.API + RedisKeyPrefix.REDIS_KEY_SEPARATOR + "rsc" + RedisKeyPrefix.REDIS_KEY_SEPARATOR
				+ token;
	}

}