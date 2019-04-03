package com.liyulin.demo.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Spring工具类
 *
 * @author liyulin
 * @date 2019年4月3日下午10:06:52
 */
@Component
public class SpringUtil implements ApplicationListener<ContextRefreshedEvent> {

	private static ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = event.getApplicationContext();
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取class对应的bean
	 *
	 * @param <T> 类型
	 * @return bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

}