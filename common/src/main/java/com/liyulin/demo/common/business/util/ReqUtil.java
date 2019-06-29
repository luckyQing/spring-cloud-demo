package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;

import lombok.experimental.UtilityClass;

/**
 * {@link Req}工具类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:26:38
 */
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
		return build(new ReqObjectBody<>(object));
	}

	/**
	 * 构建{@code Req<BasePageReq<T>>}对象
	 * 
	 * @param body
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static <T extends BaseDto> Req<BasePageReq<T>> build(T body, Integer pageNum, Integer pageSize) {
		return new Req<>(new BasePageReq<>(body, pageNum, pageSize));
	}

}