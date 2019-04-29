package com.liyulin.demo.common.support.condition;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.util.ArrayUtil;
import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.common.util.ObjectUtil;
import com.liyulin.demo.common.util.ReflectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <code>FeignClient</code>生效条件判断
 * 
 * <h3>判断逻辑</h3>
 * <ul>
 * <li>1、获取使用{@link FeignClient}的interface的Class；
 * <li>2、通过放射，获取interface对应的所有实现类；
 * <li>3、遍历interface对应的所有实现类，判断是否存在一个Class被{@link Controller}或{@link RestController}注解修饰（排除interface对应的熔断bean）；
 * <li>4、如果存在，则{@link #matches(ConditionContext, AnnotatedTypeMetadata)}返回false，{@code FeignClient}不生效；否则，返回true，{@code FeignClient}生效。
 * </ul>
 *
 * @author liyulin
 * @date 2019年3月22日下午2:30:00
 */
@Slf4j
public class SmartFeignClientCondition implements Condition {
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 1、获取使用@FeignClient的interface的Class
		ClassMetadata classMetadata = (ClassMetadata) metadata;
		String interfaceClassName = classMetadata.getClassName();
		Class<?> interfaceClass = null;
		try {
			interfaceClass = Class.forName(interfaceClassName);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}

		// 2、获取interface对应的所有实现类
		Set<?> subTypes = ReflectionUtil.getSubTypesOf(interfaceClass);

		// 3、判断是否存在RPC interface的实现类，且实现类上有Controller、RestController注解
		if (CollectionUtil.isEmpty(subTypes)) {
			return true;
		}
		// 遍历bean
		for (Object subType : subTypes) {
			Class<?> subTypeClass = (Class<?>) subType;
			Annotation[] annotations = subTypeClass.getAnnotations();
			if (ArrayUtil.isEmpty(annotations)) {
				continue;
			}

			// 遍历bean上修饰的注解
			for (Annotation annotation : annotations) {
				// 注解本身就是RestController或Controller注解
				if (isRpcImplementClass(annotation)) {
					return false;
				}

				// 注解继承了RestController或Controller注解
				Class<? extends Annotation> annotationType = annotation.annotationType();
				if (ObjectUtil.isNull(annotationType)) {
					continue;
				}

				Annotation[] annotationTypeAnnotations = annotationType.getAnnotations();
				if (null != annotationTypeAnnotations) {
					for (Annotation annotationTypeAnnotation : annotationTypeAnnotations) {
						if (isRpcImplementClass(annotationTypeAnnotation)) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	private boolean isRpcImplementClass(Annotation annotation) {
		return annotation instanceof RestController || annotation instanceof Controller;
	}

}