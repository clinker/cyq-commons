package com.github.clinker.commons.util.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RsaUtils {

	private static final String KEY_ALGORITHM = "RSA";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 解密。 用私钥解密。
	 *
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(final byte[] src, final byte[] key) throws Exception {
		// 取得私钥
		final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		final Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(src);
	}

	/**
	 * 解密。 用私钥解密。
	 *
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(final byte[] src, final byte[] key) throws Exception {
		// 取得公钥
		final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		final Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(src);
	}

	/**
	 * 加密。 用私钥加密。
	 *
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(final byte[] src, final byte[] key) throws Exception {
		// 取得私钥
		final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		final Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(src);
	}

	/**
	 * 加密。 用公钥加密。
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(final byte[] data, final byte[] key) throws Exception {
		// 取得公钥
		final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		final Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥。
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(final Map<String, Object> keyMap) throws Exception {
		final Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥。
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(final Map<String, Object> keyMap) throws Exception {
		final Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 初始化密钥。
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		final KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		final Map<String, Object> keyMap = new HashMap<>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 用私钥对信息生成数字签名。
	 *
	 * @param data       加密数据
	 * @param privateKey 私钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(final byte[] data, final byte[] privateKey) throws Exception {
		// 构造PKCS8EncodedKeySpec对象
		final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

		// KEY_ALGORITHM 指定的加密算法
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		final PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return signature.sign();
	}

	/**
	 * 校验数字签名。
	 *
	 * @param data      加密数据
	 * @param publicKey 公钥
	 * @param sign      数字签名
	 *
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 *
	 */
	public static boolean verify(final byte[] data, final byte[] publicKey, final byte[] sign) throws Exception {
		// 构造X509EncodedKeySpec对象
		final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

		// KEY_ALGORITHM 指定的加密算法
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		final PublicKey pubKey = keyFactory.generatePublic(keySpec);

		final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(sign);
	}

	private RsaUtils() {

	}

}
