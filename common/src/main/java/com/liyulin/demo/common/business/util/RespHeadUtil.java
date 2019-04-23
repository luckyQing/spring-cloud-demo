package com.liyulin.demo.common.business.util;

import com.liyulin.demo.common.business.dto.RespHead;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.util.TransactionIdUtil;

import lombok.experimental.UtilityClass;

/**
 * {@link RespHead}工具类
 *
 * @author liyulin
 * @date 2019年4月14日下午5:47:57
 */
@UtilityClass
public class RespHeadUtil {

	/**
	 * 构造RespHead对象
	 * 
	 * @return 默认返回状态码{@code ReturnCodeEnum.SUCCESS}
	 */
	public static RespHead of() {
		RespHead respHead = new RespHead(ReturnCodeEnum.SUCCESS);
		respHead.setTimestamp(System.currentTimeMillis());
		respHead.setTransactionId(TransactionIdUtil.getInstance().nextId());
		return respHead;
	}
	
}