package com.liyulin.demo.common.condition;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.liyulin.demo.common.util.TestUtil;

import lombok.Getter;

/**
 * Test环境生效
 *
 * @author liyulin
 * @date 2019年4月17日下午7:31:25
 */
public class OnTestCondition implements Condition {
	
	@Getter
	public static boolean test = false;

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ApplicationContext applicationContext = (ApplicationContext) context.getResourceLoader();
		test = TestUtil.isTestEnv(applicationContext);
		return test;
	}

}