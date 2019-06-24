package com.liyulin.demo.common.util.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lombok.experimental.UtilityClass;

/**
 * RSA非对称加密工具类
 * 
 * @author liyulin
 * @date 2019年6月24日 下午8:59:59
 */
@UtilityClass
public class RsaUtil {
	
	/** 算法名称 */
	private static final String ALGORITHOM = "RSA";
	/** 保存生成的密钥对的文件名称。 */
	private static final String RSA_PAIR_FILENAME = "/rsa_pair.txt";
	/** 密钥大小 */
	private static final int KEY_SIZE = 1024;
	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	private static KeyPairGenerator keyPairGen = null;
	private static KeyFactory keyFactory = null;
	/** 缓存的密钥对 */
	private static KeyPair oneKeyPair = null;

	private static File rsaPairFile = null;

	static {
		try {
			keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
			keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		rsaPairFile = new File(getRSAPairFilePath());
	}

	/**
	 * 生成并返回RSA密钥对。
	 */
	private static synchronized KeyPair generateKeyPair() {
		try {
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			oneKeyPair = keyPairGen.generateKeyPair();
			saveKeyPair(oneKeyPair);
			return oneKeyPair;
		} catch (InvalidParameterException | NullPointerException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 返回生成/读取的密钥对文件的路径。
	 */
	private static String getRSAPairFilePath() {
		String urlPath = FileUtils.getTempDirectoryPath();
		return (new File(urlPath).getParent() + RSA_PAIR_FILENAME);
	}

	/**
	 * 若需要创建新的密钥对文件，则返回 {@code true}，否则 {@code false}。
	 */
	private static boolean isCreateKeyPairFile() {
		// 是否创建新的密钥对文件
		boolean createNewKeyPair = false;
		if (!rsaPairFile.exists() || rsaPairFile.isDirectory()) {
			createNewKeyPair = true;
		}
		return createNewKeyPair;
	}

	/**
	 * 将指定的RSA密钥对以文件形式保存。
	 *
	 * @param keyPair 要保存的密钥对。
	 */
	private static void saveKeyPair(KeyPair keyPair) {
		try (FileOutputStream fos = FileUtils.openOutputStream(rsaPairFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(keyPair);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 返回RSA密钥对。
	 */
	public static KeyPair getKeyPair() {
		// 首先判断是否需要重新生成新的密钥对文件
		if (isCreateKeyPairFile()) {
			// 直接强制生成密钥对文件，并存入缓存。
			return generateKeyPair();
		}
		if (oneKeyPair != null) {
			return oneKeyPair;
		}
		return readKeyPair();
	}

	// 同步读出保存的密钥对
	private static KeyPair readKeyPair() {
		try (FileInputStream fis = FileUtils.openInputStream(rsaPairFile);
				ObjectInputStream ois = new ObjectInputStream(fis);) {

			oneKeyPair = (KeyPair) ois.readObject();
			return oneKeyPair;
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
	 *
	 * @param modulus        系数。
	 * @param publicExponent 专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
	 *
	 * @param modulus         系数。
	 * @param privateExponent 专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
				new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
	 *
	 * @param hexModulus         系数。
	 * @param hexPrivateExponent 专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
		if (StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
			return null;
		}
		byte[] modulus = null;
		byte[] privateExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e.getMessage());
		}
		if (modulus != null && privateExponent != null) {
			return generateRSAPrivateKey(modulus, privateExponent);
		}
		return null;
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
	 *
	 * @param hexModulus        系数。
	 * @param hexPublicExponent 专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
		if (StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
			return null;
		}
		byte[] modulus = null;
		byte[] publicExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e.getMessage());
		}
		if (modulus != null && publicExponent != null) {
			return generateRSAPublicKey(modulus, publicExponent);
		}
		return null;
	}

	/**
	 * 使用指定的公钥加密数据。
	 *
	 * @param publicKey 给定的公钥。
	 * @param data      要加密的数据。
	 * @return 加密后的数据。
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.ENCRYPT_MODE, publicKey);
		return ci.doFinal(data);
	}

	/**
	 * 使用指定的私钥解密数据。
	 *
	 * @param privateKey 给定的私钥。
	 * @param data       要解密的数据。
	 * @return 原数据。
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.DECRYPT_MODE, privateKey);
		return ci.doFinal(data);
	}

	/**
	 * 使用给定的公钥加密给定的字符串。
	 * <p />
	 * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回
	 * {@code
	 * null}。
	 *
	 * @param publicKey 给定的公钥。
	 * @param plaintext 字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(PublicKey publicKey, String plaintext) {
		if (publicKey == null || plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		try {
			byte[] encryptdata = encrypt(publicKey, data);
			return new String(Hex.encodeHex(encryptdata));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 使用默认的公钥加密给定的字符串。
	 * <p />
	 * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
	 *
	 * @param plaintext 字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(String plaintext) {
		if (plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		KeyPair keyPair = getKeyPair();
		try {
			byte[] encryptdata = encrypt((RSAPublicKey) keyPair.getPublic(), data);
			return new String(Hex.encodeHex(encryptdata));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 使用给定的私钥解密给定的字符串。
	 * <p />
	 * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
	 * 私钥不匹配时，返回 {@code null}。
	 *
	 * @param privateKey  给定的私钥。
	 * @param encrypttext 密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(PrivateKey privateKey, String encrypttext) {
		if (privateKey == null || StringUtils.isBlank(encrypttext)) {
			return null;
		}
		try {
			byte[] encryptdata = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt(privateKey, encryptdata);
			return new String(data);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 使用默认的私钥解密给定的字符串。
	 * <p />
	 * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。 私钥不匹配时，返回
	 * {@code null}。
	 *
	 * @param encrypttext 密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(String encrypttext) {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		}
		KeyPair keyPair = getKeyPair();
		try {
			byte[] encryptdata = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt((RSAPrivateKey) keyPair.getPrivate(), encryptdata);
			return new String(data);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
	 *
	 * @param encrypttext 密文。
	 * @return {@code encrypttext} 的原文字符串。
	 */
	public static String decryptStringByJs(String encrypttext) {
		String text = decryptString(encrypttext);
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	/**
	 * 返回已初始化的默认的公钥。
	 */
	public static RSAPublicKey getDefaultPublicKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPublicKey) keyPair.getPublic();
		}
		return null;
	}

	/**
	 * 返回已初始化的默认的私钥。
	 */
	public static RSAPrivateKey getDefaultPrivateKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPrivateKey) keyPair.getPrivate();
		}
		return null;
	}

	public static void main(String[] agrs) {
		RSAPublicKey publicKey = RsaUtil.getDefaultPublicKey();
		String modulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
		String public_exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));

		RSAPrivateKey privateKey = RsaUtil.getDefaultPrivateKey();

		String text = "hello world!";
		String mi = RsaUtil.encryptString(text);
		String ming = RsaUtil.decryptString(mi);
		System.out.println("mi===>" + mi);
		System.out.println("ming===>" + ming);
	}
}