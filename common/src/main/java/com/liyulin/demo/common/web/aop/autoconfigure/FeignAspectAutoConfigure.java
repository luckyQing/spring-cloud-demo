package com.liyulin.demo.common.web.aop.autoconfigure;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.common.web.aop.advice.FeignAspectAdvice;

@Configuration
public class FeignAspectAutoConfigure {

	@Bean
	public FeignAspectAdvice feignAspectAdvice() {
		return new FeignAspectAdvice();
	}

	/**
	 * feign切面
	 * 
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcut feignClientPointcut() {
		AspectJExpressionPointcut feignClientPointcut = new AspectJExpressionPointcut();
		// feign切面：如果没有配置，则取默认的
		String feignExpression = getWithinExpression(Arrays.asList(FeignClient.class, SmartFeignClient.class));
		feignClientPointcut.setExpression(feignExpression);
		return feignClientPointcut;
	}

	@Bean
	public Advisor feignAdvisor(@Autowired FeignAspectAdvice feignAspectAdvice,
			@Autowired AspectJExpressionPointcut feignClientPointcut) {
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(feignAspectAdvice);
		feignAdvisor.setPointcut(feignClientPointcut);

		return feignAdvisor;
	}

	/**
	 * 获取被注解标记的类切面表达式
	 * 
	 * @param annotations
	 * @return
	 */
	private String getWithinExpression(List<Class<? extends Annotation>> annotations) {
		StringBuilder expression = new StringBuilder();
		for (int i = 0; i < annotations.size(); i++) {
			expression.append("@within(" + annotations.get(i).getTypeName() + ")");
			if (i != annotations.size() - 1) {
				expression.append(" || ");
			}
		}
		return expression.toString();
	}

}