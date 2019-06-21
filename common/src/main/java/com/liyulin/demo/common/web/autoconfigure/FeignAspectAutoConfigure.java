package com.liyulin.demo.common.web.autoconfigure;

import java.util.Arrays;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.common.web.aspect.advice.FeignAspectAdvice;
import com.liyulin.demo.common.web.aspect.util.AspectUtil;

@Configuration
@ConditionalOnProperty(name = "smart.aspect.rpclog", havingValue = "true")
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
		String feignExpression = AspectUtil.getWithinExpression(Arrays.asList(FeignClient.class, SmartFeignClient.class));
		feignClientPointcut.setExpression(feignExpression);
		return feignClientPointcut;
	}

	@Bean
	public Advisor feignAdvisor(final FeignAspectAdvice feignAspectAdvice,
			final AspectJExpressionPointcut feignClientPointcut) {
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(feignAspectAdvice);
		feignAdvisor.setPointcut(feignClientPointcut);

		return feignAdvisor;
	}

}