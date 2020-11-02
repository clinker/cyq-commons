package com.github.clinker.commons.util.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCbcPkcs5paddingUtils {

	/**
	 * AES解密。密钥长度128位，矢量长度128位。ECB/PKCS5Padding模式。
	 *
	 * @param src Encrypted bytes.
	 * @param key 128 bits key.
	 * @param iv  128 bits IV.
	 *
	 * @return Plain bytes.
	 * @throws Exception Any exceptions in decryption.
	 */
	public static byte[] decrypt(final byte[] src, final byte[] key, final byte[] iv) throws Exception {
		final Key k = new SecretKeySpec(key, "AES");
		final Cipher in = Cipher.getInstance("AES/CBC/PKCS5Padding");
		in.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(iv));
		final byte[] enc = in.doFinal(src);
		return enc;
	}

	/**
	 * AES加密。密钥长度128位，矢量长度128位。ECB/PKCS5Padding模式。
	 *
	 * @param src Plain bytes.
	 * @param key 128 bits key.
	 * @param iv  128 bits IV.
	 * @return Encrypted bytes.
	 * @throws Exception Any exceptions in encryption.
	 */
	public static byte[] encrypt(final byte[] src, final byte[] key, final byte[] iv) throws Exception {
		final Key k = new SecretKeySpec(key, "AES");
		final Cipher out = Cipher.getInstance("AES/CBC/PKCS5Padding");
		out.init(Cipher.ENCRYPT_MODE, k, new IvParameterSpec(iv));
		final byte[] dec = out.doFinal(src);
		return dec;
	}

	private AesCbcPkcs5paddingUtils() {

	}

}
