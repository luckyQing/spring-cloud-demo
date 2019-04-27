package com.liyulin.demo.common.support.condition.util;

import java.util.Set;

import org.reflections.Reflections;
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
public class ConditionUtil {

	private static Reflections reflections = null;
	static {
		reflections = new Reflections(CommonConstants.BASE_PACAKGE);
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