package com.github.clinker.commons.sms.verifycode;

/**
 * 短信验证码生成器。
 */
public interface SmsVerifyCodeGenerator {

	/**
	 * 生成短信验证码。
	 *
	 * @return 短信验证码
	 */
	String generate();

}
