package com.liyulin.demo.mall.order.enums;

import com.liyulin.demo.common.business.exception.dto.ReturnCodeDto;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.Getter;

/**
 * 订单服务状态码
 *
 * @author liyulin
 * @date 2019年4月16日下午4:48:43
 */
@Getter
public enum OrderReturnCodeEnum implements IBaseReturnCode {

	/** 库存更新失败 */
	UPDATE_STOCK_FAIL(new ReturnCodeDto("200101", "库存更新失败"));

	private OrderReturnCodeEnum(ReturnCodeDto returnCodeDto) {
		this.info = returnCodeDto;
	}

	/** 状态码、提示信息 */
	private ReturnCodeDto info;

}