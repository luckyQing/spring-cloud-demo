package com.liyulin.demo.mall.smoking.test.config;

public class SmokingTestLocalConfig extends AbstractSmokingTestConfig {

	private static final boolean MERGE_MODULE = false;
	private static final String MERGE_MALL_BASE_URL = "http://localhost:30001/";

	@Override
	public String getOrderBaseUrl() {
		return getBaseUrl(MERGE_MODULE, MERGE_MALL_BASE_URL, "http://localhost:20011/");
	}
	
	@Override
	public String getProductBaseUrl() {
		return getBaseUrl(MERGE_MODULE, MERGE_MALL_BASE_URL, "http://localhost:20021/");
	}
	
}