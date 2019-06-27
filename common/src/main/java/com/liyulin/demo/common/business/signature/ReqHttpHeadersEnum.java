package com.liyulin.demo.common.business.signature;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求头名称枚举
 * 
 * @author liyulin
 * @date 2019年6月27日 下午12:04:13
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReqHttpHeadersEnum {

	/** token */
	SMART_TOKEN("smart-token"),
	/** 全局唯一交易流水号（防止重复提交） */
	SMART_NONCE("smart-nonce"),
	/** 请求时间戳 */
	SMART_TIMESTAMP("smart-timestamp"),
	/** 请求参数签名 */
	SMART_SIGN("smart-sign");

	/** 请求头名称 */
	private String headerName;

}
