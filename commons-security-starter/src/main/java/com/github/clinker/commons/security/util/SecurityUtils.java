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

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext()
				.getAuthentication();
	}

	public static Collection<? extends GrantedAuthority> getAuthorities() {
		final AuthAccountUserDetails userDetails = getUserDetails();
		if (userDetails == null) {
			return null;
		}

		return userDetails.getAuthorities();
	}

	public static AuthAccountUserDetails getUserDetails() {
		final Authentication auth = getAuthentication();
		if (auth == null) {
			return null;
		}

		return (AuthAccountUserDetails) auth.getPrincipal();
	}

	public static String getUserId() {
		final AuthAccountUserDetails userDetails = getUserDetails();
		if (userDetails == null) {
			return null;
		}

		return userDetails.getId();
	}

	public static String getUsername() {
		final AuthAccountUserDetails userDetails = getUserDetails();
		if (userDetails == null) {
			return null;
		}

		return userDetails.getUsername();
	}

	private SecurityUtils() {

	}
}
