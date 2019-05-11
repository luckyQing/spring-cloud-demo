package com.liyulin.demo.common.business.autoconfigure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;

import com.liyulin.demo.common.support.annotation.YamlScan;

import lombok.extern.slf4j.Slf4j;

/**
 * 加载指定的yml、yaml文件
 *
 * @author liyulin
 * @date 2019年5月12日上午1:09:40
 */
@Slf4j
public class YamlImportBeanDefinitionRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

	private ConfigurableEnvironment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 1、获取yml文件路径
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(YamlScan.class.getName(), true));
		String[] locationPatterns = attributes.getStringArray(YamlScan.ATTRIBUTE_LOCATION_PATTERNS);

		// 2、获取每个文件对应的Resource对象
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Map<String, Resource> resourceMap = new HashMap<>();
		for (String locationPattern : locationPatterns) {
			try {
				Resource[] resources = resourcePatternResolver.getResources(locationPattern);
				if (resources != null && resources.length > 0) {
					for (Resource resource : resources) {
						resourceMap.putIfAbsent(resource.getFile().getAbsolutePath(), resource);
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (resourceMap.size() == 0) {
			return;
		}

		// 3、将所有Resource加入Environment中
		try {
			for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {
				Resource resource = entry.getValue();
				YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
				List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load(resource.getFilename(),
						resource);
				for (PropertySource<?> propertySource : propertySources) {
					environment.getPropertySources().addLast(propertySource);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}