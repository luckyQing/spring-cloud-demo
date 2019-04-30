package com.liyulin.demo.common.web.aop.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.web.aop.advice.FeignAspectAdvice;
import com.liyulin.demo.common.web.aop.pointCut.FeignClientPointcut;

@Configuration
public class FeignAspectAutoConfigure {

	@Bean
	public FeignAspectAdvice feignAspectAdvice() {
		return new FeignAspectAdvice();
	}

	@Bean
	public Advisor feignAdvisor(@Autowired FeignAspectAdvice feignAspectAdvice) {
		// feign切面：如果没有配置，则取默认的
		/*AspectJExpressionPointcut logPointcut = new AspectJExpressionPointcut();
		logPointcut.setExpression(CommonConstants.LOG_AOP_EXECUTION);*/
		FeignClientPointcut feignClientPointcut = new FeignClientPointcut();
		
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(feignAspectAdvice);
		feignAdvisor.setPointcut(feignClientPointcut);

		return feignAdvisor;
	}

}