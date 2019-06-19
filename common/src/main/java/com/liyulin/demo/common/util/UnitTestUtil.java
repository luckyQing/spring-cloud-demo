package com.liyulin.demo.common.util;

import com.liyulin.demo.common.business.test.AbstractIntegrationTest;

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

	/** 是否是单元测试环境（在加载{@link AbstractIntegrationTest}时，手动初始化<code>test</code>的值） */
	@Getter
	@Setter
	private static boolean test = false;

}