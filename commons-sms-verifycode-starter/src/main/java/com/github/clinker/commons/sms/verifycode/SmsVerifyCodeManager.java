package com.github.clinker.commons.sms.verifycode;

/**
 * 短信验证码管理器。
 */
public interface SmsVerifyCodeManager {

	/**
	 * 新建。
	 *
	 * @param key        唯一键
	 * @param verifyCode 验证码
	 */
	void create(String key, String verifyCode);

	/**
	 * 删除。
	 *
	 * @param key 唯一键
	 */
	void delete(String key);

	/**
	 * 判断用户输入的验证码是否正确。
	 *
	 * @param key                 唯一键
	 * @param userInputVerifyCode 用户输入的验证码
	 * @return 用户输入的验证码是否正确
	 */
	boolean matches(String key, String userInputVerifyCode);

}
