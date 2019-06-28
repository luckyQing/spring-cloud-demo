package com.liyulin.demo.common.web.autoconfigure;

import java.util.Arrays;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;
import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.common.web.aspect.interceptor.FeignInterceptor;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

@Configuration
@ConditionalOnPropertyBoolean(name = "smart.aspect.rpclog")
public class FeignAspectAutoConfigure {

	@Bean
	public FeignInterceptor feignInterceptor() {
		return new FeignInterceptor();
	}

	/**
	 * feign切面
	 * 
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcut feignClientPointcut() {
		AspectJExpressionPointcut feignClientPointcut = new AspectJExpressionPointcut();
		String feignExpression = AspectInterceptorUtil.getWithinExpression(Arrays.asList(FeignClient.class, SmartFeignClient.class));
		feignClientPointcut.setExpression(feignExpression);
		return feignClientPointcut;
	}

	@Bean
	public Advisor feignAdvisor(final FeignInterceptor feignInterceptor,
			final AspectJExpressionPointcut feignClientPointcut) {
		DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
		feignAdvisor.setAdvice(feignInterceptor);
		feignAdvisor.setPointcut(feignClientPointcut);

		return feignAdvisor;
	}

}