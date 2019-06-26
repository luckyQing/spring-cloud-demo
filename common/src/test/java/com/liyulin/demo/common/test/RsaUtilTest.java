package com.liyulin.demo.common.test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.assertj.core.api.Assertions;

import com.liyulin.demo.common.util.security.RsaUtil;

import junit.framework.TestCase;

public class RsaUtilTest extends TestCase {

	/**
	 * 加密、解密
	 * 
	 * @throws Exception
	 */
	public void testEncryptAndDecrypt() throws Exception {
		KeyPair keyPair = RsaUtil.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		String plainText = "hello world!";
		// 加密后的文本
		String encryptText = RsaUtil.encryptString(publicKey, plainText);

		String modulus = new String(Hex.encodeHex(privateKey.getModulus().toByteArray()));
		String privateExponent = new String(Hex.encodeHex(privateKey.getPrivateExponent().toByteArray()));
		RSAPrivateKey decryptPrivateKey = RsaUtil.getRSAPrivateKey(modulus, privateExponent);

		// 解密后的文本
		String decryptText = RsaUtil.decryptString(decryptPrivateKey, encryptText);

		Assertions.assertThat(plainText).isEqualTo(decryptText);
	}

	/**
	 * 签名
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws SignatureException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws DecoderException
	 */
	public void testSign() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
			SignatureException, UnsupportedEncodingException, DecoderException {
		KeyPair keyPair = RsaUtil.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

		String content = "hello world!";
		String sign = RsaUtil.sign(content, rsaPrivateKey);
		boolean result = RsaUtil.checkSign(content, sign, rsaPublicKey);
		Assertions.assertThat(result).isTrue();
	}

}