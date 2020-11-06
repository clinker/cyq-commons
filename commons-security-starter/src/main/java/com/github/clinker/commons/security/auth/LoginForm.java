package com.github.clinker.commons.security.auth;

import lombok.Data;

/**
 * 登录请求。
 */
@Data
public class LoginForm {

	/**
	 * 登录名
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

}
