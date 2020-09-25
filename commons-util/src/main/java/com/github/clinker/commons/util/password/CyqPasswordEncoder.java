package com.github.clinker.commons.util.password;

public interface CyqPasswordEncoder {

	/**
	 * 加密密码。
	 *
	 * @param rawPassword 密码明文
	 * @return 密码密文
	 */
	String encode(CharSequence rawPassword);

	/**
	 * 密码是否正确。
	 *
	 * @param rawPassword     密码明文
	 * @param encodedPassword 密码密文
	 * @return 正确则返回true，否则返回false
	 */
	boolean matches(CharSequence rawPassword, String encodedPassword);

}