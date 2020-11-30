package com.github.clinker.commons.verifycode;

import com.github.clinker.commons.util.exception.ServiceError;

/**
 * 短信验证码错误。
 */
public enum VerifyCodeError implements ServiceError {

	/**
	 * 请求发送过于频繁
	 */
	TOO_FREQUENT
}
