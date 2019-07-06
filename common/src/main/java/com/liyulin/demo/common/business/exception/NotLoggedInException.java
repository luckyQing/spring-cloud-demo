package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

/**
 * 未登陆异常
 *
 * @author liyulin
 * @date 2019年7月6日上午1:32:37
 */
public class NotLoggedInException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public NotLoggedInException() {
		super(ReturnCodeEnum.NOT_LOGGED_IN);
	}

}