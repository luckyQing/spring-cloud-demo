package com.liyulin.demo.common.support.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;

import com.liyulin.demo.common.support.annotation.SmartSpringCloudApplication;

/**
 * 判断注解{@link SmartSpringCloudApplication}是否生效
 * 
 * @author liyulin
 * @date 2019年4月27日上午3:15:00
 */
public class SmartSpringCloudApplicationCondition implements Condition {

	private static String bootstrapClassName = null;

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ClassMetadata classMetadata = (ClassMetadata) metadata;
		String currentClassName = classMetadata.getClassName();
		// 只有第一个被{@code SmartSpringCloudApplication}标记的类会生效
		if (bootstrapClassName == null) {
			bootstrapClassName = currentClassName;
			return true;
		}

		return bootstrapClassName.equals(currentClassName);
	}

}