package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private IBaseReturnCode returnCode;

	public BusinessException(IBaseReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public String toString() {
		return "[" + returnCode.getCode() + "]:" + returnCode.getMsg();
	}
	
}