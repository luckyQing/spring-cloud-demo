package com.liyulin.demo.common.web.autoconfigure;

import java.util.Arrays;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;
import com.liyulin.demo.common.support.annotation.SmartFeignClient;
import com.liyulin.demo.common.web.aspect.interceptor.FeignInterceptor;
import com.liyulin.demo.common.web.aspect.interceptor.FeignSecurityInterceptor;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

/**
 * feign切面配置
 * 
 * @author liyulin
 * @date 2019年7月3日 下午2:43:38
 */
@Configuration
@ConditionalOnExpression(FeignAspectAutoConfigure.FEIGN_ASPECT_CONDITION)
public class FeignAspectAutoConfigure {

	private static final String FEIGN_SECURITY_CONDITION_PROPERTY = "smart.aspect.rpcSecurity";
	private static final String FEIGN_LOG_CONDITION_PROPERTY = "smart.aspect.rpclog";
	/** rpc切面生效条件 */
	public static final String FEIGN_ASPECT_CONDITION = "${" + FEIGN_SECURITY_CONDITION_PROPERTY + ":false}||${"
			+ FEIGN_LOG_CONDITION_PROPERTY + ":false}";

	/**
	 * feign切面
	 * 
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcut feignClientPointcut() {
		AspectJExpressionPointcut feignClientPointcut = new AspectJExpressionPointcut();
		String feignExpression = AspectInterceptorUtil
				.getWithinExpression(Arrays.asList(FeignClient.class, SmartFeignClient.class));
		feignClientPointcut.setExpression(feignExpression);
		return feignClientPointcut;
	}

	@Configuration
	@ConditionalOnPropertyBoolean(name = FEIGN_SECURITY_CONDITION_PROPERTY)
	class FeignSecurityAutoConfigure {

		@Bean
		public FeignSecurityInterceptor feignSecurityInterceptor() {
			return new FeignSecurityInterceptor();
		}

		@Bean
		public Advisor feignSecurityAdvisor(final FeignSecurityInterceptor feignSecurityInterceptor,
				final AspectJExpressionPointcut feignClientPointcut) {
			DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
			feignAdvisor.setAdvice(feignSecurityInterceptor);
			feignAdvisor.setPointcut(feignClientPointcut);

			return feignAdvisor;
		}

	}

	@Configuration
	@ConditionalOnPropertyBoolean(name = FEIGN_LOG_CONDITION_PROPERTY)
	class FeignLogAutoConfigure {

		@Bean
		public FeignInterceptor feignInterceptor() {
			return new FeignInterceptor();
		}

		@Bean
		public Advisor feignLogAdvisor(final FeignInterceptor feignInterceptor,
				final AspectJExpressionPointcut feignClientPointcut) {
			DefaultBeanFactoryPointcutAdvisor feignAdvisor = new DefaultBeanFactoryPointcutAdvisor();
			feignAdvisor.setAdvice(feignInterceptor);
			feignAdvisor.setPointcut(feignClientPointcut);

			return feignAdvisor;
		}

	}

}