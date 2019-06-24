package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.ReqHead;
import com.liyulin.demo.common.util.TransactionIdUtil;

import lombok.experimental.UtilityClass;

/**
 * {@link ReqHead}工具类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:26:07
 */
@UtilityClass
public class ReqHeadUtil {

	public static ReqHead of() {
		return of(null);
	}

	public static ReqHead of(String token) {
		ReqHead reqHead = new ReqHead();
		reqHead.setTransactionId(TransactionIdUtil.getInstance().nextId());
		reqHead.setTimestamp(System.currentTimeMillis());
		reqHead.setToken(token);
		return reqHead;
	}

}