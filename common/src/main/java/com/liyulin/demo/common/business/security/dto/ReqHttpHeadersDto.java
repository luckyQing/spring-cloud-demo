package com.liyulin.demo.common.business.security.dto;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 请求头信息
 * 
 * @author liyulin
 * @date 2019年6月27日 下午12:04:53
 */
@Getter
@Setter
@SuperBuilder
public class ReqHttpHeadersDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** token */
	private String token;
	/** 全局唯一交易流水号（防止重复提交） */
	private String nonce;
	/** 请求时间戳 */
	private String timestamp;
	/** 请求参数签名 */
	private String sign;

}