package com.liyulin.demo.common.business;

import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;

/**
 * 请求上下文
 * 
 * @author liyulin
 * @date 2019年6月26日 下午5:07:13
 */
public class ReqContextHolder extends BaseDto {
	private static final long serialVersionUID = 1L;
	
	private static ThreadLocal<LoginCache> loginCacheThreadLocal = new ThreadLocal<>();
	
	public static LoginCache getLoginCache() {
		LoginCache loginCache = loginCacheThreadLocal.get();
		if (loginCache != null) {
			return loginCache;
		}
		String token = ReqHttpHeadersUtil.getToken();
		return loginCache;
	}
	
}