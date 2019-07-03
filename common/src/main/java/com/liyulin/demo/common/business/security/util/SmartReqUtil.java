package com.liyulin.demo.common.business.security.util;

import com.alibaba.fastjson.JSON;
import com.liyulin.demo.common.util.security.AesUtil;

public class SmartReqUtil {

	public static String encryptReq(Object body, String password) {
		return AesUtil.encrypt(JSON.toJSONString(body), password);
	}
	
}