package com.liyulin.demo.rpc.enums.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 密码状态=={"1":"未设置","2":"已设置"}
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PwdStateEnum {

	/** 未设置 */
	NOT_SETTING((byte)1),
	/** 已设置 */
	DONE_SETTING((byte)2);

	private Byte value;
	
}