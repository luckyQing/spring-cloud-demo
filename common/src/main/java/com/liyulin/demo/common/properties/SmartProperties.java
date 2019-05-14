package com.liyulin.demo.common.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.constants.CommonConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * yml文件公共属性配置定义
 *
 * @author liyulin
 * @date 2019年4月14日下午4:45:04
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = CommonConstants.SMART_PROPERTIES_PREFIX)
public class SmartProperties extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** id生成器数据机器标识配置 */
	private Long dataMachineId;

	/** swagger开关 （默认false） */
	private boolean swagger = false;

	/** hibernate validator开关 （默认false） */
	private boolean validator = false;

	/** api版本 */
	private String apiVersion;

	/** mock开关 （默认false） */
	private boolean mock = false;

	/** @Async配置开关 */
	private boolean enableAsync = true;
	
	/** 多数据源配置信息 */
	private Map<String, SingleDataSourceProperties> dataSources;

	public static final class PropertiesName {
		public static final String DATA_MACHINE_ID = "dataMachineId";
		public static final String SWAGGER = "swagger";
		public static final String VALIDATOR = "validator";
		public static final String API_VERSION = "apiVersion";
		public static final String MOCK = "mock";
		public static final String ENABLE_ASYNC = "enableAsync";
		public static final String DATA_SOURCES = CommonConstants.SMART_PROPERTIES_PREFIX +".data-sources";
	}

}