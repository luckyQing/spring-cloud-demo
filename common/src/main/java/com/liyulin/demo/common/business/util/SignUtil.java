package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqHead;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SignUtil {

	public static <T extends BaseDto> String sign(Req<T> req) {
		T body = req.getBody();
		ReqHead head = req.getHead();
		return null;
	}

}