package com.liyulin.demo.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.lang.Nullable;

import lombok.experimental.UtilityClass;

/**
 * 集合工具类
 *
 * @author liyulin
 * @date 2019年3月30日下午4:09:46
 */
@UtilityClass
public class CollectionUtil {

	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null && collection.size() > 0);
	}

	public static boolean isEmpty(@Nullable Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
		return (map != null && map.size() > 0);
	}

}