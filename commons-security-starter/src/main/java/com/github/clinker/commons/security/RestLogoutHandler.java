package com.github.clinker.commons.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 注销时删除认证token。
 */
@Component
@AllArgsConstructor
@Slf4j
public class RestLogoutHandler implements LogoutHandler {

	private final TokenProperties tokenProperties;

	private final TokenService tokenService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// 删除token
		String token = request.getHeader(tokenProperties.getHeader());
		log.debug("Logout, token: {}", token);

		if (StringUtils.isNotBlank(token)) {
			token = StringUtils.removeStart(token, tokenProperties.getHeaderValuePrefix())
					.trim();
			tokenService.delete(token);
		}
	}

}
