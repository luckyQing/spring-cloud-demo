package com.liyulin.demo.common.listener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

//@Component
public class ApplicationStartedEventListener
		implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

	@Override
	public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
	}

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment env = event.getEnvironment();
		String appName = env.getProperty("spring.application.name");
		if (StringUtils.isNotBlank(appName)) {
			MDC.put("appName", appName);
		}
	}

}