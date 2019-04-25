package com.liyulin.demo.common.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
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

		// 默认的bean名称（不含package，首字母小写）
		String defaultClassName = super.buildDefaultBeanName(definition);
		// bean类名（package+className）
		String beanClassName = definition.getBeanClassName();
		if (beanClassName.startsWith(CommonConstants.BASE_PACAKGE)) {
			// 如果是非第三方的类
			// 如果该bean名称不存在容器中，则按照className的规则生成；否则按照“package+className”的规则存在。
			BeanFactory beanFactory = (BeanFactory) registry;
			if (beanFactory.containsBean(defaultClassName)) {
				// 如果是非第三方的类，则bean名称直接返回“包名.类名”
				return beanClassName;
			} else {
				return defaultClassName;
			}
		}

		return defaultClassName;
	}

}