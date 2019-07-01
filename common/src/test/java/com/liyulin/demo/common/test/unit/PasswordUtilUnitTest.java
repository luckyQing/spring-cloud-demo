package com.liyulin.demo.common.test.unit;

import java.security.NoSuchAlgorithmException;

import org.assertj.core.api.Assertions;

import com.liyulin.demo.common.business.util.PasswordUtil;

import junit.framework.TestCase;

public class PasswordUtilUnitTest extends TestCase {

	public void testSecure() throws NoSuchAlgorithmException {
		String result = PasswordUtil.secure("123456", PasswordUtil.generateRandomSalt());
		System.out.println(result);
		Assertions.assertThat(result).isNotBlank();
	}

	public void testGenerateRandomSalt() throws NoSuchAlgorithmException {
		String randomSalt = PasswordUtil.generateRandomSalt();
		Assertions.assertThat(randomSalt.length()).isEqualTo(16);
	}
	
}