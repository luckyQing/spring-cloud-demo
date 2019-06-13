package com.liyulin.demo.common.web.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.support.annotation.SmartConditionalOnProperty;
import com.liyulin.demo.common.web.aspect.advice.LogAspectAdvice;
import com.liyulin.demo.common.web.aspect.util.AspectUtil;

@Configuration
@SmartConditionalOnProperty(name = SmartProperties.PropertiesName.ENABLE_LOG_AOP)
public class LogAspectAutoConfigure {

	@Bean
	public LogAspectAdvice logAspectAdvice() {
		return new LogAspectAdvice();
	}

	@Bean
	public Advisor logAdvisor(final LogAspectAdvice logAspectAdvice) {
		AspectJExpressionPointcut logPointcut = new AspectJExpressionPointcut();
		String logExpression = AspectUtil.getApiExpression(CommonConstants.BASE_PACAKGE);
		logPointcut.setExpression(logExpression);

		DefaultBeanFactoryPointcutAdvisor logAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		logAdvisor.setAdvice(logAspectAdvice);
		logAdvisor.setPointcut(logPointcut);

		return logAdvisor;
	}

}