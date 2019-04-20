package com.liyulin.demo.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.constants.CommonConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置
 *
 * @author liyulin
 * @date 2019年4月14日下午4:45:04
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = CommonConstants.COMMON_PROPERTIES_PREFIX)
public class CommonProperties extends BaseDto {

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

	/** 是否强制开启服务发现 */
	private boolean forceEnableDiscoveryClient = false;

}