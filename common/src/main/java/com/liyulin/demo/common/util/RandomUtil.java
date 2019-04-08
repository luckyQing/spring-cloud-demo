package com.liyulin.demo.common.util;

import java.util.List;
import java.util.Random;
import java.util.UUID;

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
	 * 创建指定数量的随机字符串
	 * 
	 * @param pureNumber 是否是纯数字
	 * @param length 随机串长度
	 * @return
	 */
	public static String createRandom(boolean pureNumber, int length) {
		StringBuilder result = new StringBuilder(length);
		String strTable = pureNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		for (int i = 0; i < length; i++) {
			int index = (int) Math.floor(Math.random() * strTable.length());
			result.append(strTable.charAt(index));
		}

		return result.toString();
	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成指定范围随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rangeRandom(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

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