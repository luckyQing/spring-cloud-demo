package com.liyulin.demo.mall.user.enums;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserReturnCodeEnum implements IBaseReturnCode {

	/** 账号不存在 */
	ACCOUNT_NOT_EXIST("203101", "账号不存在"),
	/** 用户被禁用 */
	USER_UNENABLE("203102", "用户被禁用"),
	/** 用户已被删除 */
	USER_DELETED("203103", "用户已被删除"),
	/** rsa密钥对生成出错 */
	GENERATE_RSAKEY_FAIL("203501", "rsa密钥对生成出错");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String message;
	
}