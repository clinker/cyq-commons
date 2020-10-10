package com.github.clinker.commons.security;

import com.github.clinker.commons.security.entity.AuthAccount;

import lombok.Builder;
import lombok.Data;

/**
 * 登录响应。
 */
@Data
@Builder
public class LoginResult {

	/**
	 * 账号{@link AuthAccount}ID
	 */
	private String accountId;

	/**
	 * 账号{@link AuthAccount}登录名
	 */
	private String username;

}
