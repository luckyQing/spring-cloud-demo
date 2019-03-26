package com.liyulin.demo.common.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReturnCodeEnum implements IReturnCodeEnum {

	/** 成功 */
	SUCCESS("0000", "成功");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String msg;

}