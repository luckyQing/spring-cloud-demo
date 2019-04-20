package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReqUtil {

	/**
	 * 构建{@code Req<T>}对象（不带{@code ReqHead}）
	 * 
	 * @param body
	 * @return
	 */
	public static <T extends BaseDto> Req<T> build(T body) {
		return new Req<>(body);
	}

	/**
	 * 构建{@code Req<ReqObjectBody<T>>}对象（不带{@code ReqHead}）
	 * 
	 * @param object
	 * @return
	 */
	public static <T> Req<ReqObjectBody<T>> build(T object) {
		return new Req<>(new ReqObjectBody<>(object));
	}

	/**
	 * 构建{@code Req<T>}对象（带{@code ReqHead}）
	 * 
	 * @param body
	 * @return
	 */
	public static <T extends BaseDto> Req<T> buildWithHead(T body) {
		Req<T> req = new Req<>(ReqHeadUtil.of());
		req.setBody(body);
		return req;
	}

	/**
	 * 构建{@code Req<BasePageReq<T>>}对象（带{@code ReqHead}）
	 * 
	 * @param body
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static <T extends BaseDto> Req<BasePageReq<T>> buildWithHead(T body, Integer pageNum, Integer pageSize) {
		Req<BasePageReq<T>> req = new Req<>(ReqHeadUtil.of());
		req.setBody(new BasePageReq<>(body, pageNum, pageSize));
		return req;
	}

}