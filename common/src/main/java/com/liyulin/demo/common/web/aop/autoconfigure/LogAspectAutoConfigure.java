package com.liyulin.demo.common.web.aop.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.web.aop.advice.LogAspectAdvice;

@Configuration
public class LogAspectAutoConfigure {

	@Bean
	public LogAspectAdvice logAspectAdvice() {
		return new LogAspectAdvice();
	}

	@Bean
	public Advisor logAdvisor(@Autowired LogAspectAdvice logAspectAdvice) {
		AspectJExpressionPointcut logPointcut = new AspectJExpressionPointcut();
		logPointcut.setExpression(CommonConstants.LOG_AOP_EXECUTION);

		DefaultBeanFactoryPointcutAdvisor logAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		logAdvisor.setAdvice(logAspectAdvice);
		logAdvisor.setPointcut(logPointcut);

		return logAdvisor;
	}

}