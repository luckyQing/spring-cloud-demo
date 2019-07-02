package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;

import lombok.experimental.UtilityClass;

/**
 * 请求对象工具类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:26:38
 */
@UtilityClass
public class ReqUtil {



	/**
	 * 构建{@code BasePageReq<T>}对象
	 * 
	 * @param body
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static <T extends BaseDto> BasePageReq<T> build(T body, Integer pageNum, Integer pageSize) {
		return new BasePageReq<>(body, pageNum, pageSize);
	}

}