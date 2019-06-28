package com.liyulin.demo.common.web.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.CommonConstant;
import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;
import com.liyulin.demo.common.web.aspect.interceptor.MockInterceptor;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

@Configuration
@ConditionalOnPropertyBoolean(name = "smart.aspect.mock")
public class MockAspectAutoConfigure {

	@Bean
	public MockInterceptor mockInterceptor() {
		return new MockInterceptor();
	}

	@Bean
	public Advisor mockAdvisor(final MockInterceptor mockInterceptor) {
		AspectJExpressionPointcut mockPointcut = new AspectJExpressionPointcut();
		String logExpression = AspectInterceptorUtil.getApiExpression(CommonConstant.BASE_PACAKGE);
		mockPointcut.setExpression(logExpression);

		DefaultBeanFactoryPointcutAdvisor mockAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		mockAdvisor.setAdvice(mockInterceptor);
		mockAdvisor.setPointcut(mockPointcut);

		return mockAdvisor;
	}

}