package com.liyulin.demo.common.business.test.runner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.test.AbstractSmokingTest;
import com.liyulin.demo.common.business.test.AbstractUnitTest;
import com.liyulin.demo.common.util.ArrayUtil;
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

	private static final String TEST_CASE_PREFIX = "test";

	public AllTestsRunner(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		super(builder, clazz, getSuiteClasses());
	}

	private static Class<?>[] getSuiteClasses() {
		Set<Class<? extends AbstractUnitTest>> abstractUnitTestSet = ReflectionUtil.getSubTypesOf(AbstractUnitTest.class);
		Set<Class<? extends AbstractIntegrationTest>> abstractIntegrationTestSet = ReflectionUtil.getSubTypesOf(AbstractIntegrationTest.class);
		Set<Class<? extends AbstractSmokingTest>> abstractSmokingTestSet = ReflectionUtil.getSubTypesOf(AbstractSmokingTest.class);
		Set<Class<? extends TestCase>> testCaseSet = ReflectionUtil.getSubTypesOf(TestCase.class);

		Set<Class<?>> testClassSet = new HashSet<>();
		testClassSet.addAll(abstractUnitTestSet);
		testClassSet.addAll(abstractIntegrationTestSet);
		testClassSet.addAll(abstractSmokingTestSet);
		testClassSet.addAll(testCaseSet);

		Set<Class<?>> suiteClasses = testClassSet.stream().filter(clazz -> {
			// 过滤掉不符合条件的类
			return !isAbstractClass(clazz) && isContainTestCase(clazz);
		}).collect(Collectors.toSet());

		return suiteClasses.toArray(new Class<?>[suiteClasses.size()]);
	}

	/**
	 * 是否是抽象类
	 * 
	 * @param clazz
	 * @return
	 */
	private static boolean isAbstractClass(Class<?> clazz) {
		return Modifier.isAbstract(clazz.getClass().getModifiers());
	}

	/**
	 * 是否包含test case
	 * 
	 * @param clazz
	 * @return
	 */
	private static boolean isContainTestCase(Class<?> clazz) {
		Method[] methods = clazz.getMethods();
		if (ArrayUtil.isEmpty(methods)) {
			return false;
		}
		if (clazz.isAssignableFrom(TestCase.class)) {
			for (Method method : methods) {
				if (method.getName().startsWith(TEST_CASE_PREFIX)) {
					return true;
				}
			}
		} else {
			// 3.所有的method中，至少有一个被@Test修饰的类
			for (Method method : methods) {
				if (AnnotationUtils.findAnnotation(method, Test.class) != null) {
					return true;
				}
			}
		}

		return false;
	}
	
}