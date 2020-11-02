package com.github.clinker.commons.security.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.clinker.commons.security.AuthAccountUserDetails;

/**
 * 安全工具类型。获取当前用户的信息。
 */
public class SecurityUtils {

	public static String getAccountId() {
		final AuthAccountUserDetails userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getId();
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext()
				.getAuthentication();
	}

	public static Collection<? extends GrantedAuthority> getAuthorities() {
		final AuthAccountUserDetails userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getAuthorities();
	}

	public static AuthAccountUserDetails getUserDetails() {
		final Authentication auth = getAuthentication();

		return auth == null ? null : (AuthAccountUserDetails) auth.getPrincipal();
	}

	public static String getUsername() {
		final AuthAccountUserDetails userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getUsername();
	}

	private SecurityUtils() {

	}

}
