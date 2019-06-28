package com.liyulin.demo.common.properties;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 切面配置
 * 
 * @author liyulin
 * @date 2019年6月19日 下午10:20:54
 */
@Getter
@Setter
public class AspectProperties extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** feign切面开关 （默认false） */
	private boolean rpclog = false;
	/** 接口日志切面开关 （默认false） */
	private boolean apilog = false;
	/** 加密、签名切面开关（默认false） */
	private boolean apiSecurity = false;
	/** mock开关 （默认false） */
	private boolean mock = false;

}