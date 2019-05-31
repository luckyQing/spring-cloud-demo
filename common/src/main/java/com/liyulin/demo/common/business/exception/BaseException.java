package com.liyulin.demo.common.business.exception;

import com.liyulin.demo.common.business.exception.dto.ReturnCodeDto;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.Setter;

/**
 * 自定义异常基类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:25:07
 */
public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 提示信息 */
	@Setter
	private ReturnCodeDto returnCodeDto;

	protected abstract IBaseReturnCode getReturnCode();

	public String getCode() {
		if (returnCodeDto == null && getReturnCode() != null && getReturnCode().getInfo() != null) {
			return getReturnCode().getInfo().getCode();
		}

		return returnCodeDto.getCode();
	}

	@Override
	public String getMessage() {
		if (returnCodeDto == null && getReturnCode() != null && getReturnCode().getInfo() != null) {
			return getReturnCode().getInfo().getMessage();
		}

		return returnCodeDto.getMessage();
	}

	@Override
	public String toString() {
		return "[" + getCode() + "]:" + this.getMessage();
	}

}