package com.liyulin.demo.common.test;

import org.assertj.core.api.Assertions;

import com.liyulin.demo.common.util.security.AesUtil;

import junit.framework.TestCase;

public class AesUtilTest extends TestCase{

	public void testEncryptAndDecrypt() {
		String password = "123456";
		String plainText = "hello world!";
		String encryptText = AesUtil.encrypt(plainText, password);
		String decryptText = AesUtil.decrypt(encryptText, password);
		Assertions.assertThat(plainText).isEqualTo(decryptText);
	}
	
}