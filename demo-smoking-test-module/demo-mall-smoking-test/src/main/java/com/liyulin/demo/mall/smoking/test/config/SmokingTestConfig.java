package com.liyulin.demo.mall.smoking.test.config;

import java.util.EnumMap;

import com.liyulin.demo.mall.smoking.test.enums.SmokingTestEnv;

public class SmokingTestConfig {

	/** 当前环境 */
	private static final SmokingTestEnv CURRENT_ENV = SmokingTestEnv.LOCAL;

	private static final EnumMap<SmokingTestEnv, AbstractSmokingTestConfig> CONFIG_ROUTE = new EnumMap<>(
			SmokingTestEnv.class);
	static {
		CONFIG_ROUTE.put(SmokingTestEnv.LOCAL, new SmokingTestLocalConfig());
	}

	public static String getOrderBaseUrl() {
		return CONFIG_ROUTE.get(CURRENT_ENV).getOrderBaseUrl();
	}
	
	public static String getProductBaseUrl() {
		return CONFIG_ROUTE.get(CURRENT_ENV).getProductBaseUrl();
	}

}