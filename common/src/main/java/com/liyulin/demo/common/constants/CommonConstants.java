package com.liyulin.demo.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

	/** 接口日志切面 */
	public static final String LOG_AOP_EXECUTION = "execution( * com.liyulin.demo..controller..*.*(..))";

	/** feign切面 */
	public static final String FEIGN_MOCK_AOP_EXECUTION = "execution( * com.liyulin.demo.rpc..*Rpc.*(..))";

}