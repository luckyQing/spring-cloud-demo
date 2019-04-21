package com.liyulin.demo.common.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

	@Getter
	@Setter
	private static boolean test = false;
	private static volatile boolean init = false;

	/**
	 * 判断是否是集成测试环境
	 * 
	 * @param applicationContext
	 * @return
	 */
	public static boolean isTest(ApplicationContext applicationContext) {
		if (!init) {
			synchronized (TestUtil.class) {
				if (!init) {
					if (applicationContext instanceof WebApplicationContext) {
						WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
						test = isTestEnv(webApplicationContext);
					}
				}
			}
			init = true;
		}

		return test;
	}

	private static boolean isTestEnv(WebApplicationContext webApplicationContext) {
		ServletContext servletContext = webApplicationContext.getServletContext();
		return ObjectUtil.isNotNull(servletContext) && servletContext instanceof MockServletContext;
	}

}