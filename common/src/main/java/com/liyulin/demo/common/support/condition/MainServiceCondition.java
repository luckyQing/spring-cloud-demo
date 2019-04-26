package com.liyulin.demo.common.support.condition;

import java.util.Set;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;

import com.liyulin.demo.common.support.annotation.MainService;
import com.liyulin.demo.common.support.condition.util.ConditionUtil;

/**
 * 判断注解{@link MainService}是否生效
 * 
 * <p>
 * 单体服务时，生效；合并服务时，单体服务jar里面被{@code MainService}标记的不生效；最外面的生效。
 *
 * @author liyulin
 * @date 2019年4月27日上午3:15:00
 */
public class MainServiceCondition implements Condition {

	/** 是否是合并服务（默认false，表示不是） */
	private static boolean mergeService = false;
	static {
		Set<?> subTypes = ConditionUtil.getTypesAnnotatedWith(MainService.class);
		// 合并服务中被MainService标记的类的个数大于1
		mergeService = subTypes != null && subTypes.size() > 1;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (mergeService) {
			ClassMetadata classMetadata = (ClassMetadata) metadata;
			return classMetadata.getClassName().contains("merge");
		}

		return true;
	}

}