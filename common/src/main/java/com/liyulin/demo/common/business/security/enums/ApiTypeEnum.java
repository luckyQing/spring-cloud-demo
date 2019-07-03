package com.liyulin.demo.common.business.security.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口类型
 * 
 * @author liyulin
 * @date 2019年7月3日 下午2:25:01
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiTypeEnum {

	/** 既不登陆，亦不签名加密 */
	OPEN("/open/"),
	/** 不需登陆，需签名加密 */
	SIGN("/sign/"),
	/** 需登陆，不签名加密，需鉴权 */
	IDENTITY("/identity/"),
	/** 需登陆，需签名加密，需鉴权 */
	AUTH("/auth/");

	/** url片段 */
	private String pathSegment;

}