package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

/**
 * 数据校验错误
 *
 * @author liyulin
 * @date 2019年5月1日上午11:56:59
 */
public class DataValidateError extends BaseException {

	private static final long serialVersionUID = 1L;

	public DataValidateError() {
		super(ReturnCodeEnum.DATE_MISSING);
	}
	
	public DataValidateError(String message) {
		setCode(ReturnCodeEnum.DATE_MISSING.getCode());
		setMessage(message);
	}

}