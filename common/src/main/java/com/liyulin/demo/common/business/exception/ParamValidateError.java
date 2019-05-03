package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.dto.ReturnCodeDto;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
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

	}

	public ParamValidateError(ReturnCodeDto returnCodeDto) {
		super.setReturnCodeDto(returnCodeDto);
	}

	public ParamValidateError(String msg) {
		ReturnCodeDto returnCodeDto = new ReturnCodeDto(ReturnCodeEnum.PARAMETERS_MISSING.getInfo().getCode(), msg);
		super.setReturnCodeDto(returnCodeDto);
	}

	@Override
	public IBaseReturnCode getReturnCode() {
		return ReturnCodeEnum.PARAMETERS_MISSING;
	}

}