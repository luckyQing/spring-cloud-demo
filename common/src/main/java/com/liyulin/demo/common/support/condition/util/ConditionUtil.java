package com.liyulin.demo.common.support.condition.util;

import java.lang.annotation.Annotation;
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

	private static Reflections reflections = new Reflections(CommonConstants.BASE_PACAKGE);

	/**
	 * 根据父类类型获取所有子类类型
	 * 
	 * @param type
	 * @return
	 */
	public static <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> type) {
		return reflections.getSubTypesOf(type);
	}

	/**
	 * 获取被当前注解标记的所有类
	 * 
	 * @param annotation
	 * @return
	 */
	public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
		return reflections.getTypesAnnotatedWith(annotation);
	}

}