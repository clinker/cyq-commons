package com.github.clinker.commons.util.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES算法。
 */
public class TripleDesUtils {

	private static String TRIPPLE_DES = "DESede";

	private static String TRIPPLE_DES_CBC_PKSC5 = "DESede/CBC/PKCS5Padding";

	private static String TRIPPLE_DES_ECB_PKSC5 = "DESede/ECB/PKCS5Padding";

	/**
	 * 算法:DESede/ECB/PKCS5Padding。
	 *
	 * @param src 密文的字节数组。
	 * @param key 密钥，长度192位。
	 * @return 明文的字节数组。
	 */
	public static byte[] decrypt(final byte[] src, final byte[] key) throws Exception {
		final DESedeKeySpec dks = new DESedeKeySpec(key);
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPPLE_DES);
		final SecretKey secretKey = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(TRIPPLE_DES_ECB_PKSC5);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		final byte[] b = cipher.doFinal(src);
		return b;
	}

	/**
	 * 使用向量，对字节数组进行3DES解密。
	 *
	 * @param src 明文的字节数组。
	 * @param key 密钥，长度192位。
	 * @param iv  向量，长度64位。
	 * @return 解密后的字节数组。
	 * @throws Exception
	 */
	public static byte[] decrypt(final byte[] src, final byte[] key, final byte[] iv) throws Exception {
		final Key k = keyGenerator(key);
		final IvParameterSpec IVSpec = ivGenerator(iv);
		final Cipher c = Cipher.getInstance(TRIPPLE_DES_CBC_PKSC5);
		c.init(Cipher.DECRYPT_MODE, k, IVSpec);
		final byte output[] = c.doFinal(src);
		return output;
	}

	/**
	 * 算法:DESede/ECB/PKCS5Padding。
	 *
	 * @param src 密文的字节数组。
	 * @param key 密钥，长度192位。
	 * @return 明文的字节数组。
	 */
	public static byte[] encrypt(final byte[] src, final byte[] key) throws Exception {
		final DESedeKeySpec dks = new DESedeKeySpec(key);
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPPLE_DES);
		final SecretKey secretKey = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(TRIPPLE_DES_ECB_PKSC5);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		final byte[] b = cipher.doFinal(src);
		return b;
	}

	/**
	 * 使用向量，对字节数组进行3DES加密。
	 *
	 * @param src 密文的字节数组。
	 * @param key 密钥，长度192位。
	 * @param iv  向量，长度64位。
	 * @return 加密后的字节数组。
	 * @throws Exception
	 */
	public static byte[] encrypt(final byte[] src, final byte[] key, final byte[] iv) throws Exception {
		final Key k = keyGenerator(key);
		final IvParameterSpec IVSpec = ivGenerator(iv);
		final Cipher c = Cipher.getInstance(TRIPPLE_DES_CBC_PKSC5);
		c.init(Cipher.ENCRYPT_MODE, k, IVSpec);
		final byte output[] = c.doFinal(src);
		return output;
	}

	private static IvParameterSpec ivGenerator(final byte b[]) throws Exception {
		return new IvParameterSpec(b);
	}

	private static Key keyGenerator(final byte[] b) throws Exception {
		final DESedeKeySpec KeySpec = new DESedeKeySpec(b);
		final SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(TRIPPLE_DES);
		return KeyFactory.generateSecret(KeySpec);
	}

	private TripleDesUtils() {

	}
}
