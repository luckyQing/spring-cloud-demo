package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

/**
 * 重复提交校验异常
 * 
 * @author liyulin
 * @date 2019年7月3日 下午4:58:17
 */
public class RepeatSubmitException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public RepeatSubmitException(String message) {
		setCode(ReturnCodeEnum.REPEAT_SUBMIT.getCode());
		setMessage(message);
	}

}