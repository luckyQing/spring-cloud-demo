package com.liyulin.demo.common.business.test;

import com.liyulin.demo.common.properties.SmartProperties;

/**
 * 冒烟测试基类
 *
 * @author liyulin
 * @date 2019年4月27日下午2:22:36
 */
public abstract class AbstractSmokingTest {

	static {
		System.setProperty(SmartProperties.PropertiesName.API_VERSION, "1.0.0");
	}
	
}