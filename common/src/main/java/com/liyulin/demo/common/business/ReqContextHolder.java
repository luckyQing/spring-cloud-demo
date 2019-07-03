package com.liyulin.demo.common.business;

import java.util.Objects;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.exception.DataValidateException;
import com.liyulin.demo.common.business.exception.confg.ParamValidateMessage;
import com.liyulin.demo.common.business.security.LoginRedisConfig;
import com.liyulin.demo.common.business.security.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.redis.RedisComponent;
import com.liyulin.demo.common.util.SpringContextUtil;

import lombok.experimental.UtilityClass;

/**
 * 请求上下文
 * 
 * @author liyulin
 * @date 2019年6月26日 下午5:07:13
 */
@UtilityClass
public class ReqContextHolder extends BaseDto {
	private static final long serialVersionUID = 1L;

	private static ThreadLocal<LoginCache> loginCacheThreadLocal = new ThreadLocal<>();

	/**
	 * 获取当前用户id
	 * 
	 * @return
	 */
	public static Long userId() {
		LoginCache loginCache = getLoginCache();
		Long userId = loginCache.getUserId();
		if (Objects.isNull(userId)) {
			throw new DataValidateException(ParamValidateMessage.GET_USERID_FAIL);
		}
		return userId;
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @return
	 */
	public static LoginCache getLoginCache() {
		LoginCache loginCache = loginCacheThreadLocal.get();
		if (loginCache != null) {
			return loginCache;
		}

		RedisComponent redisWrapper = SpringContextUtil.getBean(RedisComponent.class);
		String token = ReqHttpHeadersUtil.getTokenMustExist();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		loginCache = redisWrapper.getObject(tokenRedisKey, new TypeReference<LoginCache>() {
		});
		if (Objects.isNull(loginCache)) {
			throw new DataValidateException(ParamValidateMessage.LOGIN_CACHE_MISSING);
		}

		loginCacheThreadLocal.set(loginCache);
		return loginCache;
	}

}