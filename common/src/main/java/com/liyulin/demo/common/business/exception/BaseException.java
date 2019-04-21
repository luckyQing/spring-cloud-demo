package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

/**
 * 自定义异常基类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:25:07
 */
public abstract class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public abstract IBaseReturnCode getReturnCode();

	public String toString() {
		return "[" + getReturnCode().getCode() + "]:" + getReturnCode().getMsg();
	}

}