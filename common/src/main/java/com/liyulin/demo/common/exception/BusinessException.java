package com.liyulin.demo.common.exception;

import com.liyulin.demo.common.exception.code.IReturnCodeEnum;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private IReturnCodeEnum returnCode;

	public BusinessException(IReturnCodeEnum returnCode) {
		this.returnCode = returnCode;
	}

	public String toString() {
		return "[" + returnCode.getCode() + "]:" + returnCode.getMsg();
	}

}