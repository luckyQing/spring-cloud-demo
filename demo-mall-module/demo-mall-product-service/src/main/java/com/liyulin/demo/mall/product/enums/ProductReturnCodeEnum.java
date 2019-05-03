package com.liyulin.demo.mall.product.enums;

import com.liyulin.demo.common.business.exception.dto.ReturnCodeDto;
import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;

import lombok.Getter;

/**
 * 商品服务状态码
 *
 * @author liyulin
 * @date 2019年4月7日下午11:52:53
 */
@Getter
public enum ProductReturnCodeEnum implements IBaseReturnCode {

	STOCK_NOT_ENOUGH(new ReturnCodeDto("201101", "库存不足，操作失败"));

	private ProductReturnCodeEnum(ReturnCodeDto returnCodeDto) {
		this.info = returnCodeDto;
	}

	/** 状态码、提示信息 */
	private ReturnCodeDto info;

}