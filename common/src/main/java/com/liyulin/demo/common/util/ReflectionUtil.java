package com.liyulin.demo.common.util;

import java.util.Set;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.context.annotation.Condition;

import com.liyulin.demo.common.constants.CommonConstants;

import lombok.experimental.UtilityClass;

/**
 * {@link Condition}工具类
 *
 * @author liyulin
 * @date 2019年4月27日上午3:56:53
 */
@UtilityClass
public class ReflectionUtil extends ReflectionUtils {

	private static Reflections reflections = null;
	static {
		reflections = new Reflections(CommonConstants.BASE_PACAKGE, new SubTypesScanner());
	}

	/**
	 * 根据父类类型获取所有子类类型
	 * 
	 * @param type
	 * @return
	 */
	public static <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> type) {
		return reflections.getSubTypesOf(type);
	}

}