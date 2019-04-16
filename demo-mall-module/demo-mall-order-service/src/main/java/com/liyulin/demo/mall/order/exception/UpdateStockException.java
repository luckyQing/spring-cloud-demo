package com.liyulin.demo.mall.order.exception;

import com.liyulin.demo.common.business.exception.BaseException;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
import com.liyulin.demo.mall.order.enums.OrderReturnCodeEnum;

/**
 * 库存更新异常
 *
 * @author liyulin
 * @date 2019年4月16日下午4:49:18
 */
public class UpdateStockException extends BaseException {

	private static final long serialVersionUID = 1L;

	@Override
	public IBaseReturnCode getReturnCode() {
		return OrderReturnCodeEnum.UPDATE_STOCK_FAIL;
	}

}