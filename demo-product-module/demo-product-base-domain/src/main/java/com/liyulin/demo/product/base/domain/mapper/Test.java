package com.liyulin.demo.product.base.domain.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Test {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericClass(Class<?> clazz, int index) {
		Type[]  types = clazz.getGenericInterfaces();
		ParameterizedType type = (ParameterizedType)types[0];
		return (Class<T>) type.getActualTypeArguments()[index];
	}
	
	public static void main(String[] args) {
		System.out.println(getGenericClass(ProductInfoBaseMapper.class, 1));
	}

}
