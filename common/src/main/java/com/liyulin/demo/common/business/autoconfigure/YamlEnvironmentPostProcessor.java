package com.liyulin.demo.common.business.autoconfigure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.liyulin.demo.common.support.annotation.YamlScan;
import com.liyulin.demo.common.util.ArrayUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 解析{@link YamlScan}
 * 
 * @author liyulin
 * @date 2019年6月21日 下午12:58:22
 */
public class YamlEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@Getter
	@Setter
	private static boolean init = false;

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (!isInit()) {
			setInit(true);
			loadYaml(environment, application);
		}
	}

	/**
	 * 将启动类注解上配置的yaml文件加到environment中
	 * 
	 * @param environment
	 * @param application
	 */
	private void loadYaml(ConfigurableEnvironment environment, SpringApplication application) {
		String[] locationPatterns = getLocationPatterns(application.getMainApplicationClass());
		if (ArrayUtil.isEmpty(locationPatterns)) {
			return;
		}
		
		loadYaml(locationPatterns, environment);
	}

	/**
	 * 将匹配的yaml文件加到environment中
	 * <p>
	 * <b>NOTE</b>：此时日志配置还没有加载，还打不了日志
	 * 
	 * @param locationPatterns
	 * @param environment
	 */
	private void loadYaml(String[] locationPatterns, ConfigurableEnvironment environment) {
		// 1、获取每个文件对应的Resource对象
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
				e.printStackTrace();
			}
		}

		if (resourceMap.size() == 0) {
			return;
		}

		// 2、将所有Resource加入Environment中
		try {
			for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {
				Resource resource = entry.getValue();
				System.out.println("load yaml ==> " + resource.getFilename());
				
				YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
				List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load(resource.getFilename(),
						resource);
				for (PropertySource<?> propertySource : propertySources) {
					environment.getPropertySources().addLast(propertySource);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取yaml文件路径
	 * 
	 * @param mainApplicationClass
	 * @return
	 */
	private String[] getLocationPatterns(Class<?> mainApplicationClass) {
		YamlScan yamlScan = AnnotationUtils.findAnnotation(mainApplicationClass, YamlScan.class);
		if (yamlScan == null) {
			return new String[0];
		}
		return yamlScan.locationPatterns();
	}

}