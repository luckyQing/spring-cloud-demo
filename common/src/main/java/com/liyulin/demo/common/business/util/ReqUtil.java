package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;

//@UtilityClass
public class ReqUtil {

	public static <T extends BaseDto> Req<T> of(T body) {
		Req<T> req = new Req<>(ReqHeadUtil.of());
		req.setBody(body);
		return req;
	}

}