package com.github.clinker.commons.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * 注销时删除认证token。
 */
@Slf4j
public class RestLogoutHandler implements LogoutHandler {

	private final TokenProperties tokenProperties;

	private final TokenService tokenService;

	public RestLogoutHandler(final TokenProperties tokenProperties, final TokenService tokenService) {
		this.tokenProperties = tokenProperties;
		this.tokenService = tokenService;
	}

	@Override
	public void logout(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) {
		// 删除token
		final String tokenHeaderValue = request.getHeader(tokenProperties.getHeader());
		log.debug("Logout: {}", tokenHeaderValue);

		if (StringUtils.isNotBlank(tokenHeaderValue)) {
			final String token = StringUtils
					.removeStartIgnoreCase(tokenHeaderValue, tokenProperties.getHeaderValuePrefix())
					.trim();
			tokenService.deleteByToken(token);
		}
	}

}
