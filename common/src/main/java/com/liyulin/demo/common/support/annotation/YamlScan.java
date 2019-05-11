package com.liyulin.demo.common.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.liyulin.demo.common.business.autoconfigure.YamlImportBeanDefinitionRegistrar;

/**
 * yaml文件匹配
 *
 * @author liyulin
 * @date 2019年5月11日下午5:08:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(YamlImportBeanDefinitionRegistrar.class)
public @interface YamlScan {
	
	/** 属性名locationPatterns */
	public static final String ATTRIBUTE_LOCATION_PATTERNS = "locationPatterns";

	/** yml文件路径（支持正则表达式） */
	String[] locationPatterns();

}