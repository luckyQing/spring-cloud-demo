package com.liyulin.demo.common.business.mock;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * mock工厂类
 *
 * @author liyulin
 * @date 2019年5月11日上午11:49:40
 */
public class MockUtil extends PodamFactoryImpl {

	private static final PodamFactory podamFactory = new PodamFactoryImpl();

	private MockUtil() {
	}

	/**
	 * mock对象
	 * 
	 * @param pojoClass
	 * @param genericTypeArgs
	 * @return
	 */
	public static <T> T mock(Class<T> pojoClass, Type... genericTypeArgs) {
		return podamFactory.manufacturePojo(pojoClass, genericTypeArgs);
	}
	
	/**
	 * mock对象，支持嵌套泛型
	 * 
	 * @param typeReference
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T manufacturePojo(TypeReference<T> typeReference) {
		Type type = typeReference.getType();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Class<T> rawTypeClass = (Class<T>) (parameterizedType).getRawType();
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			return podamFactory.manufacturePojo(rawTypeClass, actualTypeArguments);
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			if (componentType instanceof ParameterizedType) {
				throw new UnsupportedOperationException("不支持泛型数组操作");
			} else {
				Class<T> clazz = (Class<T>) type;
				return podamFactory.manufacturePojo(clazz);
			}
		} else if (type instanceof TypeVariable || type instanceof WildcardType) {
			throw new UnsupportedOperationException("不支持泛TypeVariable、WildcardType操作");
		} else {
			Class<T> clazz = (Class<T>) type;
			return podamFactory.manufacturePojo(clazz);
		}
	}

}