package com.liyulin.demo.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.constants.CommonConstant;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * yml文件公共属性配置定义
 *
 * @author liyulin
 * @date 2019年4月14日下午4:45:04
 */
@Getter
@Setter
@ConfigurationProperties(prefix = CommonConstant.SMART_PROPERTIES_PREFIX)
public class SmartProperties extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** id生成器数据机器标识配置 */
	private Long dataMachineId;
	/** @Async配置开关 */
	private boolean async = true;
	/** api配置 */
	private ApiProperties api = new ApiProperties();
	/** 切面配置 */
	private AspectProperties aspect = new AspectProperties();
	/** swagger配置 */
	private SwaggerProperties swagger = new SwaggerProperties();

	@UtilityClass
	public static final class PropertiesName {
		public static final String DATA_MACHINE_ID = "dataMachineId";
		public static final String API_VERSION = "apiVersion";
	}

}