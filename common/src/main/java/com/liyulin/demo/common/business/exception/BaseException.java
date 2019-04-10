package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.Getter;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	@Getter
	private IBaseReturnCode returnCode;

	public BaseException(IBaseReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public String toString() {
		return "[" + returnCode.getCode() + "]:" + returnCode.getMsg();
	}
	
}