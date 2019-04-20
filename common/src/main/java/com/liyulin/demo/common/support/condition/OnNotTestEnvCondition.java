package com.liyulin.demo.common.support.condition;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.liyulin.demo.common.util.TestUtil;

/**
 * 不是Test环境生效
 *
 * @author liyulin
 * @date 2019年4月17日下午7:31:25
 */
public class OnNotTestEnvCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ApplicationContext applicationContext = (ApplicationContext) context.getResourceLoader();
		return !TestUtil.isTestEnv(applicationContext);
	}

}