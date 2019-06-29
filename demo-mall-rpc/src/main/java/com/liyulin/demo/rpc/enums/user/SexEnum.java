package com.liyulin.demo.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别=={"1":"男","2":"女","3":"未知"}
 *
 * @author liyulin
 * @date 2019年6月29日上午11:40:24
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SexEnum {

	/** 男 */
	MALE((byte) 1),
	/** 女 */
	FEMALE((byte) 2),
	/** 未知 */
	UNKNOWN((byte) 3);

	private Byte value;

}