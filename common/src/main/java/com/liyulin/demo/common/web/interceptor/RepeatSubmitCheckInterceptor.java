package com.liyulin.demo.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;

/**
 * 重复提交校验拦截器
 * 
 * @author liyulin
 * @date 2019年6月13日 上午9:24:18
 */
@Configuration
@ConditionalOnPropertyBoolean(name = SmartProperties.PropertiesName.REPEAT_SUBMIT_CHECK)
public class RepeatSubmitCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}