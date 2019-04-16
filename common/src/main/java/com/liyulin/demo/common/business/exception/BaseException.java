package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

public abstract class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public abstract IBaseReturnCode getReturnCode();

	public String toString() {
		return "[" + getReturnCode().getCode() + "]:" + getReturnCode().getMsg();
	}

}