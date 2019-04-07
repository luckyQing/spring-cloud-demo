package com.liyulin.demo.common.util;

import lombok.experimental.UtilityClass;

/**
 * Object工具类
 *
 * @author liyulin
 * @date 2019年4月7日下午12:18:49
 */
@UtilityClass
public class ObjectUtil {

	/**
	 * 是否为null
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * 是否不为null
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
		return object != null;
	}

}