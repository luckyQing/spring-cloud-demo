package com.liyulin.demo.common.web.aspect.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyulin.demo.common.business.signature.dto.ReqHttpHeadersDto;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.constants.OrderConstant;

public class ApiSecurityInterceptor implements MethodInterceptor, Ordered {

	@Override
	public int getOrder() {
		return OrderConstant.API_SECURITY;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		ReqHttpHeadersDto reqHttpHeaders = ReqHttpHeadersUtil.getReqHttpHeadersDto(request);
		String token = reqHttpHeaders.getToken();
		if(StringUtils.isBlank(token)) {
			return invocation.proceed();
		}
		
//		SmartSignatureUtil.checkReqSign(reqHttpHeaders, encryptedBody, rsaPublicKey);
		return null;
	}
	
	private String getTokenRedisPrefix(String token) {
		return "token:"+token;
	}

}