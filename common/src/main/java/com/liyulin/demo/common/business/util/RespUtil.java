package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.util.ObjectUtil;

import lombok.experimental.UtilityClass;

/**
 * {@link Resp}工具类
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
	 * 构造响应成功对象
	 * 
	 * @param r
	 * @return
	 */
	public static <R extends BaseDto> Resp<R> success(R r) {
		return new Resp<>(r);
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
	 * 根据失败的响应对象，返回对应信息
	 * 
	 * @param resp
	 * @return
	 */
	public static <R extends BaseDto, T extends BaseDto> Resp<T> error(Resp<R> resp) {
		return error(getFailMsg(resp));
	}

	/**
	 * 构造响应错误对象
	 * 
	 * @param msg
	 * @return
	 */
	public static <R extends BaseDto> Resp<R> error(String msg) {
		return new Resp<>(new RespHead(ReturnCodeEnum.SERVER_ERROR.getInfo().getCode(), msg));
	}

	/**
	 * 是否成功
	 * 
	 * @param resp
	 * @return
	 */
	public static <R extends BaseDto> boolean isSuccess(Resp<R> resp) {
		return ObjectUtil.isNotNull(resp) && ObjectUtil.isNotNull(resp.getHead())
				&& ObjectUtil.equals(ReturnCodeEnum.SUCCESS.getInfo().getCode(), resp.getHead().getCode());
	}

	/**
	 * 获取失败的提示信息
	 * 
	 * @param resp
	 * @return
	 */
	public static <R extends BaseDto> String getFailMsg(Resp<R> resp) {
		if (ObjectUtil.isNull(resp)) {
			return "rpc请求失败";
		}

		if (ObjectUtil.isNull(resp.getHead())) {
			return "返回结果异常";
		}

		return resp.getHead().getMessage();
	}

}