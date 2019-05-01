package com.liyulin.demo.common.util;

import java.lang.reflect.Type;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * mock工具类
 *
 * @author liyulin
 * @date 2019年4月9日下午8:04:26
 */
@UtilityClass
public class MockUtil {
	@Getter
	private PodamFactory factory = new PodamFactoryImpl();

	public <T> T mock(Class<T> pojoClass) {
		return factory.manufacturePojoWithFullData(pojoClass);
	}

	public <T> T mock(Class<T> pojoClass, Type... genericTypeArgs) {
		return factory.manufacturePojoWithFullData(pojoClass, genericTypeArgs);
	}

}