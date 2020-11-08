package com.github.clinker.commons.security.token;

/**
 * 认证token生成器接口。
 */
public interface TokenGenerator {

	/**
	 * 生成userDetails的token。
	 *
	 * @param accountId 账号ID
	 * @return token
	 */
	String generate(String accountId);

}
