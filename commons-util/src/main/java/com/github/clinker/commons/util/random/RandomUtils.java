package com.github.clinker.commons.util.random;

import java.security.SecureRandom;

/**
 * 随机工具类。
 */
public class RandomUtils {

	/**
	 * 从source里生成length个随机字符串。
	 *
	 * @param length 生成的字符串长度
	 * @param source 生成的字符串来源
	 * @return 随机字符串
	 */
	public static String randomString(final int length, final String source) {
		final int charsLength = source.length();
		final SecureRandom random = new SecureRandom();

		final StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(source.charAt(random.nextInt(charsLength)));
		}
		return builder.toString();
	}

	private RandomUtils() {

	}
}
