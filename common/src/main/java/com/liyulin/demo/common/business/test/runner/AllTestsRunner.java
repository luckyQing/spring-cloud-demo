package com.liyulin.demo.common.business.test.runner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import com.liyulin.demo.common.business.test.AbstractUnitTest;
import com.liyulin.demo.common.util.ArrayUtil;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.ReflectionUtil;

import junit.framework.TestCase;

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
		Set<Class<? extends AbstractUnitTest>> abstractUnitTestSet = ReflectionUtil.getSubTypesOf(AbstractUnitTest.class);
		Set<Class<? extends TestCase>> testCaseSet = ReflectionUtil.getSubTypesOf(TestCase.class);

		Set<Class<?>> caseSet = new HashSet<>();
		caseSet.addAll(abstractUnitTestSet);
		caseSet.addAll(testCaseSet);

		if (CollectionUtil.isEmpty(caseSet)) {
			return new Class<?>[0];
		}

		List<Class<?>> suiteClasses = caseSet.stream().filter(item -> {
			// 过滤掉不符合条件的类
			// 1.过滤掉抽象类
			if (Modifier.isAbstract(item.getClass().getModifiers())) {
				return false;
			}

			// 2.过滤掉没有method的类
			Method[] methods = item.getMethods();
			if (ArrayUtil.isEmpty(methods)) {
				return false;
			}

			// 3.所有的method中，至少有一个被@Test修饰的类
			for (Method method : methods) {
				if (AnnotationUtils.findAnnotation(method, Test.class) != null) {
					return true;
				}
			}

			return false;
		}).collect(Collectors.toList());

		return suiteClasses.toArray(new Class<?>[suiteClasses.size()]);
	}

}