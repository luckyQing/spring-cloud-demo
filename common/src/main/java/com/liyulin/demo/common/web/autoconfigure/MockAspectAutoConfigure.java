package com.liyulin.demo.common.web.autoconfigure;

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
import com.liyulin.demo.common.web.aop.util.AspectUtil;

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
		String logExpression = AspectUtil.getApiExpression(CommonConstants.BASE_PACAKGE);
		mockPointcut.setExpression(logExpression);

		DefaultBeanFactoryPointcutAdvisor mockAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		mockAdvisor.setAdvice(mockAspectAdvice);
		mockAdvisor.setPointcut(mockPointcut);

		return mockAdvisor;
	}

}