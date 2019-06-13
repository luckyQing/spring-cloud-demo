package com.liyulin.demo.common.web.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.support.annotation.SmartConditionalOnProperty;
import com.liyulin.demo.common.web.aspect.advice.MockAspectAdvice;
import com.liyulin.demo.common.web.aspect.util.AspectUtil;

@Configuration
@SmartConditionalOnProperty(name = SmartProperties.PropertiesName.ENABLE_MOCK)
public class MockAspectAutoConfigure {

	@Bean
	public MockAspectAdvice mockAspectAdvice() {
		return new MockAspectAdvice();
	}

	@Bean
	public Advisor mockAdvisor(final MockAspectAdvice mockAspectAdvice) {
		AspectJExpressionPointcut mockPointcut = new AspectJExpressionPointcut();
		String logExpression = AspectUtil.getApiExpression(CommonConstants.BASE_PACAKGE);
		mockPointcut.setExpression(logExpression);

		DefaultBeanFactoryPointcutAdvisor mockAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		mockAdvisor.setAdvice(mockAspectAdvice);
		mockAdvisor.setPointcut(mockPointcut);

		return mockAdvisor;
	}

}