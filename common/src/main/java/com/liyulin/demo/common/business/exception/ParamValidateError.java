package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

/**
 * 参数校验错误
 *
 * @author liyulin
 * @date 2019年5月1日上午11:56:59
 */
public class ParamValidateError extends BaseException {

	private static final long serialVersionUID = 1L;

	public ParamValidateError() {
		super(ReturnCodeEnum.PARAMETERS_MISSING);
	}
	
	public ParamValidateError(String message) {
		setCode(ReturnCodeEnum.PARAMETERS_MISSING.getCode());
		setMessage(message);
	}

}