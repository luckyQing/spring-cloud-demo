package com.liyulin.demo.code.auto.generate.config;

import com.liyulin.demo.code.auto.generate.utils.ConfigFileUtils;

public class ConfigFactory {

	public static Config config = null;


	public static void init(String configFile) {
		config = ConfigFileUtils.getConfig(configFile);
	}



}
