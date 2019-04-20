package com.liyulin.demo.common.util;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

	/**
	 * 判断是否是集成测试环境
	 * 
	 * @param applicationContext
	 * @return
	 */
	public boolean isTestEnv(ApplicationContext applicationContext) {
		if (applicationContext instanceof WebApplicationContext) {
			WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
			ServletContext servletContext = webApplicationContext.getServletContext();
			if (ObjectUtil.isNotNull(servletContext)) {
				String servletContextName = servletContext.getServletContextName();
				if (StringUtils.isNotBlank(servletContextName) && servletContextName.toLowerCase().contains("mock")) {
					return true;
				}
			}
		}

		return false;
	}

}