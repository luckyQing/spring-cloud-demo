package com.liyulin.demo.common.business.security.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.liyulin.demo.common.business.exception.ParamValidateException;
import com.liyulin.demo.common.business.exception.confg.ParamValidateMessage;
import com.liyulin.demo.common.business.security.dto.ReqHttpHeadersDto;
import com.liyulin.demo.common.business.security.enums.ReqHttpHeadersEnum;
import com.liyulin.demo.common.util.RandomUtil;
import com.liyulin.demo.common.util.SnowFlakeIdUtil;
import com.liyulin.demo.common.util.WebUtil;

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
	 * @return
	 */
	public static ReqHttpHeadersDto getReqHttpHeadersDto() {
		HttpServletRequest request = WebUtil.getHttpServletRequest();

		String token = request.getHeader(ReqHttpHeadersEnum.SMART_TOKEN.getHeaderName());
		String nonce = request.getHeader(ReqHttpHeadersEnum.SMART_NONCE.getHeaderName());
		String timestamp = request.getHeader(ReqHttpHeadersEnum.SMART_TIMESTAMP.getHeaderName());
		String sign = request.getHeader(ReqHttpHeadersEnum.SMART_SIGN.getHeaderName());

		return ReqHttpHeadersDto.builder().token(token).nonce(nonce).timestamp(timestamp).sign(sign).build();
	}

	/**
	 * 生成token
	 * 
	 * @return
	 */
	public static String generateToken() {
		// 产生规则：16进制（雪花算法）+2位随机字符混淆
		return Long.toHexString(SnowFlakeIdUtil.getInstance().nextId()) + RandomUtil.generateRandom(false, 2);
	}

	/**
	 * 获取请求参数中的token；如果不存在，则抛异常
	 * 
	 * @return
	 */
	public static String getTokenMustExist() {
		String token = getTokenable();
		if (StringUtils.isBlank(token)) {
			throw new ParamValidateException(ParamValidateMessage.TOKEN_MISSING);
		}
		return token;
	}

	/**
	 * 获取请求参数中的token（有可能不存在）
	 * 
	 * @return
	 */
	public static String getTokenable() {
		HttpServletRequest request = WebUtil.getHttpServletRequest();
		return request.getHeader(ReqHttpHeadersEnum.SMART_TOKEN.getHeaderName());
	}

}