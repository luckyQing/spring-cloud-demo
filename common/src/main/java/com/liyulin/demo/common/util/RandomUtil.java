package com.liyulin.demo.common.util;

import java.util.List;

import lombok.experimental.UtilityClass;

/**
 * 随机工具类
 *
 * @author liyulin
 * @date 2019年4月8日下午5:04:27
 */
@UtilityClass
public class RandomUtil {

	/**
	 * 随机排序
	 * 
	 * @param array
	 * @return
	 */
	public static <E> E[] randomSort(E[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return array;
		}

		for (int i = 0, len = array.length; i < len; i++) {
			int currentRandom = Double.valueOf(Math.random() * (len - 1)).intValue();
			E current = array[i];
			array[i] = array[currentRandom];
			array[currentRandom] = current;
		}

		return array;
	}

	/**
	 * 随机排序
	 * 
	 * @param list
	 * @return
	 */
	public static <E> List<E> randomSort(List<E> list) {
		if (CollectionUtil.isEmpty(list)) {
			return list;
		}

		for (int i = 0, len = list.size(); i < len; i++) {
			int currentRandom = Double.valueOf(Math.random() * (len - 1)).intValue();
			E current = list.get(i);
			list.set(i, list.get(currentRandom));
			list.set(currentRandom, current);
		}

		return list;
	}

}