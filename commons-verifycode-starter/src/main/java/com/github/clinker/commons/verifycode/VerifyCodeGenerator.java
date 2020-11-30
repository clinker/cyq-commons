package com.github.clinker.commons.verifycode;

/**
 * 短信验证码生成器。
 */
public interface VerifyCodeGenerator {

	/**
	 * 生成短信验证码。
	 *
	 * @return 短信验证码
	 */
	String generate();

}
