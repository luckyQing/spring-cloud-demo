package com.liyulin.demo.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * 单元测试工具类
 *
 * @author liyulin
 * @date 2019年4月21日下午4:39:14
 */
@UtilityClass
public class UnitTestUtil {

	/** 是否时单元测试环境 */
	@Getter
	@Setter
	private static boolean test = false;

}