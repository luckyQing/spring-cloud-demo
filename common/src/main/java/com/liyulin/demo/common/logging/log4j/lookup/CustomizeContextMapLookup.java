package com.liyulin.demo.common.logging.log4j.lookup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

/**
 * 自定义lo4j2插件，用于设置变量
 *
 * @author liyulin
 * @date 2019年3月19日上午12:13:57
 */
@Plugin(name = "cctx", category = StrLookup.CATEGORY)
public class CustomizeContextMapLookup implements StrLookup {
	
	/** yaml文件名 */
	private static final String YAML_FILE_NAME = "application.yml";
	/** spring yaml文件中key的分隔符 */
	private static final String SPRING_YAML_KEY_SEPARATOR = "\\.";
	/** yaml文件中的应用名key */
	private static final String APP_NAME_KEY = "spring.application.name";
	/** 存储设置的变量 */
	private static final Map<String, String> DATA = new HashMap<>();

	static {
		// 解析yaml
		ClassPathResource resource = new ClassPathResource(YAML_FILE_NAME);
		Yaml yaml = new Yaml();
		String appName = null;
		try (InputStream yamlInputStream = resource.getInputStream()) {
			Map<String, Object> yamlJson = yaml.load(yamlInputStream);
			appName = getYamlValue(APP_NAME_KEY, yamlJson);
		} catch (IOException e) {
			// 抛异常，则取当前jar名
			appName = "common";
			e.printStackTrace();
		}
		DATA.put("appName", appName);
	}
	
	@Override
	public String lookup(String key) {
		return DATA.get(key);
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return DATA.get(key);
	}

	/**
	 * 解析yaml文件中的json
	 * 
	 * @param name
	 * @param yamlJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getYamlValue(String name, Map<String, Object> yamlJson) {
		String[] keys = name.split(SPRING_YAML_KEY_SEPARATOR);
		if (Objects.isNull(keys) || keys.length == 0) {
			return null;
		}

		if (keys.length == 1) {
			return String.valueOf(yamlJson.get(keys[0]));
		}

		Map<String, Object> tempMap = null;
		String value = null;
		for (int i = 0; i < keys.length; i++) {
			if (i == 0) {
				tempMap = (Map<String, Object>) yamlJson.get(keys[i]);
			} else if (i < keys.length - 1) {
				tempMap = (Map<String, Object>) tempMap.get(keys[i]);
			} else {
				value = String.valueOf(tempMap.get(keys[i]));
			}
		}

		return value;
	}

}