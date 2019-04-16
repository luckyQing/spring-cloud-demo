package com.liyulin.demo.rpc.enums.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 *
 * @author liyulin
 * @date 2019年4月16日下午3:54:38
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayStateEnum {

	/** 待支付 */
	PENDING_PAY(1),
	/** 支付成功 */
	PAY_SUCCESS(2),
	/** 支付失败 */
	PAY_FAIL(3),
	/** 待退款 */
	PENDING_REFUND(4),
	/** 退款成功 */
	PENDING_SUCCESS(5),
	/** 退款失败 */
	PENDING_FAIL(6);

	private Integer state;

}