package com.liyulin.demo.mall.order.enums;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单服务状态码
 *
 * @author liyulin
 * @date 2019年4月16日下午4:48:43
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderReturnCodeEnum implements IBaseReturnCode {

	/** 库存更新失败 */
	UPDATE_STOCK_FAIL("200101", "库存更新失败");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String message;

}