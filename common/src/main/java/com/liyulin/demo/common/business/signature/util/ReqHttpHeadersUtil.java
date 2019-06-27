package com.liyulin.demo.common.business.signature.util;

import javax.servlet.http.HttpServletRequest;

import com.liyulin.demo.common.business.signature.ReqHttpHeadersEnum;
import com.liyulin.demo.common.business.signature.dto.ReqHttpHeadersDto;

import lombok.experimental.UtilityClass;

/**
 * {@link ReqHttpHeadersDto}工具类
 * 
 * @author liyulin
 * @date 2019年6月27日 下午12:09:20
 */
@UtilityClass
public class ReqHttpHeadersUtil {

	/**
	 * 从<code>HttpServletRequest</code>中获取请求头信息
	 * 
	 * @param request
	 * @return
	 */
	public static ReqHttpHeadersDto getReqHttpHeadersDto(HttpServletRequest request) {
		String token = request.getHeader(ReqHttpHeadersEnum.SMART_TOKEN.getHeaderName());
		String nonce = request.getHeader(ReqHttpHeadersEnum.SMART_NONCE.getHeaderName());
		String timestamp = request.getHeader(ReqHttpHeadersEnum.SMART_TIMESTAMP.getHeaderName());
		String sign = request.getHeader(ReqHttpHeadersEnum.SMART_SIGN.getHeaderName());

		return ReqHttpHeadersDto.builder().token(token).nonce(nonce).timestamp(timestamp).sign(sign).build();
	}

}