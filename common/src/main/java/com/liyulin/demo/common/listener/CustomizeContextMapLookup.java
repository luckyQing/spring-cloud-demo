package com.liyulin.demo.common.listener;

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

	private static final Map<String, String> DATA = new HashMap<>();
	static {
		// 解析yaml
		ClassPathResource resource = new ClassPathResource("application.yml");
		Yaml yaml = new Yaml();
		try (InputStream yamlInputStream = resource.getInputStream()) {
			Map<String, Object> yamlJson = yaml.load(yamlInputStream);
			String appName = getYamlValue("spring.application.name", yamlJson);

			DATA.put("appName", appName);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		String separator = "\\.";
		String[] keys = name.split(separator);
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

	@Override
	public String lookup(String key) {
		return DATA.get(key);
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return DATA.get(key);
	}

}