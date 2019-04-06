package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

import lombok.experimental.UtilityClass;

/**
 * 响应对象工具类
 *
 * @author liyulin
 * @date 2019年4月6日下午5:36:34
 */
@UtilityClass
public class RespUtil {

	/**
	 * 构造响应成功对象
	 * 
	 * @return
	 */
	public static <R extends BaseDto> Resp<R> success() {
		return new Resp<>(new RespHead(ReturnCodeEnum.SUCCESS));
	}

	/**
	 * 构造响应错误对象
	 * 
	 * @param returnCode
	 * @return
	 */
	public static <R extends BaseDto> Resp<R> error(IBaseReturnCode returnCode) {
		return new Resp<>(new RespHead(returnCode));
	}

	/**
	 * 构造响应错误对象
	 * 
	 * @param msg
	 * @return
	 */
	public static <R extends BaseDto> Resp<R> error(String msg) {
		return new Resp<>(new RespHead(ReturnCodeEnum.SERVER_ERROR.getCode(), msg));
	}

}