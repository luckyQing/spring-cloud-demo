package com.liyulin.demo.common.business.signature.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.liyulin.demo.common.business.signature.ReqHttpHeadersEnum;
import com.liyulin.demo.common.business.signature.dto.ReqHttpHeadersDto;
import com.liyulin.demo.common.util.security.RsaUtil;

import lombok.experimental.UtilityClass;

/**
 * 请求响应信息签名工具类
 * 
 * @author liyulin
 * @date 2019年6月27日 下午1:15:41
 */
@UtilityClass
public class SmartSignatureUtil {
	private static final String SIGN_HEAD_NAME = "head";
	private static final String SIGN_BODY_NAME = "body";

	/**
	 * 请求参数签名
	 * 
	 * @param reqHttpHeaders 请求头参数
	 * @param encryptedBody  加密后的请求体
	 * @param rsaPrivateKey  签名私钥
	 * @return
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	public static String signReq(ReqHttpHeadersDto reqHttpHeaders, String encryptedBody, RSAPrivateKey rsaPrivateKey)
			throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException,
			UnsupportedEncodingException {
		SortedMap<String, String> signParams = new TreeMap<>();
		signParams.put(ReqHttpHeadersEnum.SMART_TIMESTAMP.getHeaderName(), reqHttpHeaders.getTimestamp());
		signParams.put(ReqHttpHeadersEnum.SMART_TOKEN.getHeaderName(), reqHttpHeaders.getToken());
		signParams.put(ReqHttpHeadersEnum.SMART_NONCE.getHeaderName(), reqHttpHeaders.getNonce());
		signParams.put(SIGN_BODY_NAME, encryptedBody);

		String signContent = JSON.toJSONString(signParams);
		return RsaUtil.sign(signContent, rsaPrivateKey);
	}

	/**
	 * 响应信息签名
	 * 
	 * @param encryptedHead
	 * @param encryptedBody
	 * @param rsaPrivateKey
	 * @return
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	public static String signResp(String encryptedHead, String encryptedBody, RSAPrivateKey rsaPrivateKey)
			throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException,
			UnsupportedEncodingException {
		SortedMap<String, String> signParams = new TreeMap<>();
		signParams.put(SIGN_HEAD_NAME, encryptedHead);
		signParams.put(SIGN_BODY_NAME, encryptedBody);

		String signContent = JSON.toJSONString(signParams);
		return RsaUtil.sign(signContent, rsaPrivateKey);
	}

}