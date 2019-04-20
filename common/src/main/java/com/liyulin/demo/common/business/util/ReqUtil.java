package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.BasePageReq;
import com.liyulin.demo.common.business.dto.Req;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReqUtil {

	public static <T extends BaseDto> Req<T> of(T body) {
		Req<T> req = new Req<>(ReqHeadUtil.of());
		req.setBody(body);
		return req;
	}

	public static <T extends BaseDto> Req<BasePageReq<T>> of(T body, Integer pageNum, Integer pageSize) {
		Req<BasePageReq<T>> req = new Req<>(ReqHeadUtil.of());
		req.setBody(new BasePageReq<>(body, pageNum, pageSize));
		return req;
	}

}