package com.liyulin.demo.common.web.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.PackageConfig;
import com.liyulin.demo.common.web.aspect.interceptor.MockInterceptor;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

@Configuration
@ConditionalOnProperty(name = "smart.aspect.mock", havingValue = "true")
public class MockAspectAutoConfigure {

	@Bean
	public MockInterceptor mockInterceptor() {
		return new MockInterceptor();
	}

	@Bean
	public Advisor mockAdvisor(final MockInterceptor mockInterceptor) {
		AspectJExpressionPointcut mockPointcut = new AspectJExpressionPointcut();
		String logExpression = AspectInterceptorUtil.getApiExpression(PackageConfig.getBasePackages());
		mockPointcut.setExpression(logExpression);

		DefaultBeanFactoryPointcutAdvisor mockAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		mockAdvisor.setAdvice(mockInterceptor);
		mockAdvisor.setPointcut(mockPointcut);

		return mockAdvisor;
	}

}