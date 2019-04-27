package com.liyulin.demo.common.support.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;

import com.liyulin.demo.common.support.annotation.MainService;

/**
 * 判断注解{@link MainService}是否生效
 * 
 * @author liyulin
 * @date 2019年4月27日上午3:15:00
 */
public class MainServiceCondition implements Condition {

	private static String bootstrapClassName = null;

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ClassMetadata classMetadata = (ClassMetadata) metadata;
		String currentClassName = classMetadata.getClassName();
		// 只有第一个被{@code MainService}标记的类会生效
		if (bootstrapClassName == null) {
			bootstrapClassName = currentClassName;
			return true;
		}

		return bootstrapClassName.equals(currentClassName);
	}

}