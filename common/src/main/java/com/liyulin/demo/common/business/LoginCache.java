package com.liyulin.demo.common.business;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.liyulin.demo.common.business.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登陆缓存信息
 *
 * @author liyulin
 * @date 2019年6月29日下午4:50:26
 */
@Getter
@Setter
public class LoginCache extends BaseDto {

	private static final long serialVersionUID = 1L;

	/** 是否登陆 */
	private boolean login;
	/** 访问token */
	private String token;
	/** 用户id */
	private Long userId;
	/** aes加密key */
	private String aesKey;
	/** rsa私钥 */
	private RSAPublicKey rsaPublicKey;
	/** rsa公钥 */
	private RSAPrivateKey rsaPrivateKey;

}