package com.liyulin.demo.common.business.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态码
 * 
 * @author liyulin
 * @date 2019年3月27日下午10:50:41
 * @see IBaseReturnCode
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReturnCodeEnum implements IBaseReturnCode {

	/** 成功 */
	SUCCESS("100200", "成功"),
	/** 校验失败 */
	VALIDATE_FAIL("100101", "校验失败"),
	/** 数据不存在 */
	DATE_NOT_EXIST("100102", "数据不存在"),
	/** 数据已存在 */
	DATE_EXISTED("100103", "数据已存在"),
	/** 成功 */
	SERVER_ERROR("100500", "服务器异常"),
	/** 无权限访问 */
	NO_ACCESS("100401", "无权限访问"),
	/** rpc请求失败 */
	RPC_FAIL("100404", "rpc请求失败"),
	/** 请求超时 */
	REQUEST_TIMEOUT("100408", "请求超时"),
	/** 重复提交 */
	REPEAT_SUBMIT("100409", "重复提交"),
	/** 参数不全 */
	PARAMETERS_MISSING("100412", "参数不全"),
	/** 请求方式不支持 */
	REQUEST_METHOD_NOT_SUPPORTED("100415", "请求方式不支持"),
	/** 签名错误 */
	SIGN_ERROR("100400", "签名错误"),
	/** 获取锁失败 */
	GET_LOCK_FAIL("100417", "获取锁失败");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String msg;

}