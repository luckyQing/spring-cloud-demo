package com.liyulin.demo.common.support;

import java.beans.Introspector;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.liyulin.demo.common.constants.CommonConstants;

/**
 * 生成bean名称的规则
 *
 * @author liyulin
 * @date 2019年4月22日上午1:55:56
 */
public class UniqueBeanNameGenerator extends AnnotationBeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
			if (StringUtils.hasText(beanName)) {
				// Explicit bean name found.
				return beanName;
			}
		}

		String beanClassName = definition.getBeanClassName();
		if (beanClassName.startsWith(CommonConstants.BASE_PACAKGE)) {
			// 如果是非第三方的类，则bean名称直接返回“包名.类名”
			return beanClassName;
		}

		Assert.state(beanClassName != null, "No bean class name set");
		String shortClassName = ClassUtils.getShortName(beanClassName);
		return Introspector.decapitalize(shortClassName);
	}

}