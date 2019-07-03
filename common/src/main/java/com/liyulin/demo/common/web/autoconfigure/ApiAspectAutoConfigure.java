package com.liyulin.demo.common.web.autoconfigure;

import org.redisson.Redisson;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import com.liyulin.demo.common.constants.CommonConstant;
import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;
import com.liyulin.demo.common.web.aspect.interceptor.ApiLogInterceptor;
import com.liyulin.demo.common.web.aspect.interceptor.ApiSecurityInterceptor;
import com.liyulin.demo.common.web.aspect.util.AspectInterceptorUtil;

/**
 * api切面配置
 * 
 * @author liyulin
 * @date 2019年7月3日 下午2:43:46
 */
@Configuration
@ConditionalOnExpression(ApiAspectAutoConfigure.API_ASPECT_CONDITION)
public class ApiAspectAutoConfigure {

	private static final String API_SECURITY_CONDITION_PROPERTY = "smart.aspect.apiSecurity";
	private static final String API_LOG_CONDITION_PROPERTY = "smart.aspect.apilog";
	/** api切面生效条件 */
	public static final String API_ASPECT_CONDITION = "${" + API_SECURITY_CONDITION_PROPERTY + ":false}||${"
			+ API_LOG_CONDITION_PROPERTY + ":false}";

	@Bean
	public AspectJExpressionPointcut apiPointcut() {
		AspectJExpressionPointcut apiPointcut = new AspectJExpressionPointcut();
		String logExpression = AspectInterceptorUtil.getApiExpression(CommonConstant.BASE_PACAKGE);
		apiPointcut.setExpression(logExpression);
		return apiPointcut;
	}

	@Configuration
	@ConditionalOnClass({ Redisson.class, RedisOperations.class })
	@ConditionalOnPropertyBoolean(name = API_SECURITY_CONDITION_PROPERTY)
	class ApiSecurityAutoConfigure {
		
		@Bean
		public ApiSecurityInterceptor apiSecurityInterceptor() {
			return new ApiSecurityInterceptor();
		}

		@Bean
		public Advisor apiSecurityAdvisor(final ApiSecurityInterceptor apiSecurityInterceptor,
				final AspectJExpressionPointcut apiPointcut) {
			DefaultBeanFactoryPointcutAdvisor apiSecurityAdvisor = new DefaultBeanFactoryPointcutAdvisor();
			apiSecurityAdvisor.setAdvice(apiSecurityInterceptor);
			apiSecurityAdvisor.setPointcut(apiPointcut);

			return apiSecurityAdvisor;
		}
	}

	@Configuration
	@ConditionalOnPropertyBoolean(name = API_LOG_CONDITION_PROPERTY)
	class ApiLogAutoConfigure {

		@Bean
		public ApiLogInterceptor apiLogInterceptor() {
			return new ApiLogInterceptor();
		}

		/**
		 * api日志切面
		 * 
		 * @param apiLogInterceptor
		 * @param apiPointcut
		 * @return
		 */
		@Bean
		public Advisor apiLogAdvisor(final ApiLogInterceptor apiLogInterceptor,
				final AspectJExpressionPointcut apiPointcut) {
			DefaultBeanFactoryPointcutAdvisor apiLogAdvisor = new DefaultBeanFactoryPointcutAdvisor();
			apiLogAdvisor.setAdvice(apiLogInterceptor);
			apiLogAdvisor.setPointcut(apiPointcut);

			return apiLogAdvisor;
		}
	}

}