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
	 * 账号ID，{@link AuthAccount}
	 */
	private String accountId;

	/**
	 * 账号登录名，{@link AuthAccount}
	 */
	private String username;

}
