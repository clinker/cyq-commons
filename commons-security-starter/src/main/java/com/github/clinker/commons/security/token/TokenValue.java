package com.github.clinker.commons.security.token;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证token的值。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValue {

	private String accountId;

	private String username;

	/**
	 * 对应UserDetails的authorities
	 */
	private Set<String> authorities;
}
