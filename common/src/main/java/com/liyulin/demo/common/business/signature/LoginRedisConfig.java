package com.liyulin.demo.common.business.signature;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginRedisConfig {

	public static String getTokenRedisKey(String token) {
		return "mall:user:login:token:" + token;
	}

	public static String getUserIdRedisKey(String userId) {
		return "mall:user:login:userId:" + userId;
	}

}