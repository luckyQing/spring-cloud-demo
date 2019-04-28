package com.liyulin.demo.common.business.test.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import com.liyulin.demo.common.business.test.BaseSpringBootTest;
import com.liyulin.demo.common.util.ArrayUtil;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.ReflectionUtil;

/**
 * 套件测试Runner
 * 
 * <p>
 * 自动搜索满足条件的Test类，以便以套件的方式进行测试
 *
 * @author liyulin
 * @date 2019年4月28日上午12:54:15
 */
public class AllTestsRunner extends Suite {

	public AllTestsRunner(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		super(builder, clazz, getSuiteClasses());
	}

	private static Class<?>[] getSuiteClasses() {
		Set<Class<? extends BaseSpringBootTest>> set = ReflectionUtil.getSubTypesOf(BaseSpringBootTest.class);
		if (CollectionUtil.isEmpty(set)) {
			return new Class<?>[0];
		}

		List<Class<?>> suiteClasses = set.stream().filter(item -> {
			// 过滤掉不符合条件的类
			// 1.过滤掉抽象类
			if (item.getClass().getModifiers() == Modifier.ABSTRACT) {
				return false;
			}

			// 2.过滤掉没有method的类
			Method[] methods = item.getMethods();
			if (ArrayUtil.isEmpty(methods)) {
				return false;
			}

			// 3.所有的method中，至少有一个被@Test修饰的类
			for (Method method : methods) {
				Annotation[] annotations = method.getDeclaredAnnotations();
				if (ArrayUtil.isNotEmpty(annotations)) {
					for (Annotation annotation : annotations) {
						if (annotation instanceof Test) {
							return true;
						}
					}
				}
			}

			return false;
		}).collect(Collectors.toList());

		return suiteClasses.toArray(new Class<?>[suiteClasses.size()]);
	}

}