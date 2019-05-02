package com.liyulin.demo.common.business.autoconfigure;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.util.LogUtil;

@Configuration
@ConditionalOnProperty(prefix = CommonConstants.SMART_PROPERTIES_PREFIX, name = SmartProperties.PropertiesName.ENABLE_ASYNC, havingValue = "true", matchIfMissing = false)
public class AsyncAutoConfigure extends AsyncConfigurerSupport {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setMaxPoolSize(100);
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setKeepAliveSeconds(120);
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskExecutor.setAwaitTerminationSeconds(60);

		return threadPoolTaskExecutor;
	}

	@Override
	@Nullable
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, obj) -> {
			LogUtil.error("asyncException@method=" + method.getName() + "; param=" + JSON.toJSONString(obj), throwable);
		};
	}

}