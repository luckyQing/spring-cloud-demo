package com.liyulin.demo.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.business.dto.BaseDto;

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
@ConfigurationProperties(prefix = CommonProperties.PREFIX)
public class CommonProperties extends BaseDto {

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "com.liyulin";

	/** id生成器数据机器标识配置 */
	private Long dataMachineId;

	/** swagger开关 （默认true） */
	private boolean swagger = true;

	/** hibernate validator开关 （默认true） */
	private boolean validator = true;

	/** api版本 */
	private String apiVersion;

	/** mock开关 （默认false） */
	private boolean mock = false;

}