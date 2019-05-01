package com.liyulin.demo.common.web.aop.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.CommonProperties;
import com.liyulin.demo.common.web.aop.advice.MockAspectAdvice;

@Configuration
@ConditionalOnProperty(prefix = CommonConstants.COMMON_PROPERTIES_PREFIX, name = CommonProperties.PropertiesName.MOCK, havingValue = "true", matchIfMissing = false)
public class MockAspectAutoConfigure {

	@Bean
	public MockAspectAdvice mockAspectAdvice() {
		return new MockAspectAdvice();
	}

	@Bean
	public Advisor mockAdvisor(@Autowired MockAspectAdvice mockAspectAdvice) {
		AspectJExpressionPointcut mockPointcut = new AspectJExpressionPointcut();
		mockPointcut.setExpression(CommonConstants.LOG_AOP_EXECUTION);

		DefaultBeanFactoryPointcutAdvisor mockAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		mockAdvisor.setAdvice(mockAspectAdvice);
		mockAdvisor.setPointcut(mockPointcut);

		return mockAdvisor;
	}

}