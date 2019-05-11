package com.liyulin.demo.common.business.exception.enums;

import com.liyulin.demo.common.business.exception.dto.ReturnCodeDto;

import lombok.Getter;

/**
 * 通用状态码
 * 
 * @author liyulin
 * @date 2019年3月27日下午10:50:41
 * @see IBaseReturnCode
 */
@Getter
public enum ReturnCodeEnum implements IBaseReturnCode {

	/** 成功 */
	SUCCESS(new ReturnCodeDto("100200", "成功")),
	/** 校验失败 */
	VALIDATE_FAIL(new ReturnCodeDto("100101", "校验失败")),
	/** 数据不存在 */
	DATE_NOT_EXIST(new ReturnCodeDto("100102", "数据不存在")),
	/** 数据已存在 */
	DATE_EXISTED(new ReturnCodeDto("100103", "数据已存在")),
	/** 服务器异常 */
	SERVER_ERROR(new ReturnCodeDto("100500", "服务器异常")),
	/** 无权限访问 */
	NO_ACCESS(new ReturnCodeDto("100401", "无权限访问")),
	/** 请求url错误 */
	REQUEST_URL_ERROR(new ReturnCodeDto("100404", "请求url错误")),
	/** 请求超时 */
	REQUEST_TIMEOUT(new ReturnCodeDto("100408", "请求超时")),
	/** 重复提交 */
	REPEAT_SUBMIT(new ReturnCodeDto("100409", "重复提交")),
	/** 参数不全 */
	PARAMETERS_MISSING(new ReturnCodeDto("100412", "参数不全")),
	/** 请求方式不支持 */
	REQUEST_METHOD_NOT_SUPPORTED(new ReturnCodeDto("100415", "请求方式不支持")),
	/** 请求类型不支持 */
	UNSUPPORTED_MEDIA_TYPE(new ReturnCodeDto("100416", "请求类型不支持")),
	/** 上传文件大小超过限制 */
	UPLOAD_FILE_SIZE_EXCEEDED(new ReturnCodeDto("100418", "上传文件大小超过限制")),
	/** 签名错误 */
	SIGN_ERROR(new ReturnCodeDto("100400", "签名错误")),
	/** 获取锁失败 */
	GET_LOCK_FAIL(new ReturnCodeDto("100417", "获取锁失败"));

	private ReturnCodeEnum(ReturnCodeDto returnCodeDto) {
		this.info = returnCodeDto;
	}

	/** 状态码、提示信息 */
	private ReturnCodeDto info;

}