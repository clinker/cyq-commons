package com.github.clinker.commons.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.token.GrantedAuthorityConverter;
import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;
import com.github.clinker.commons.security.token.TokenValue;

import lombok.AllArgsConstructor;

/**
 * 认证成功后，返回状态码200而不是301；同时返回用户信息。
 * <p/>
 * 生成并存储认证token。
 */
@AllArgsConstructor
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final GrantedAuthorityConverter grantedAuthorityConverter;

	private final ObjectMapper objectMapper;

	private final TokenProperties tokenProperties;

	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		final UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		final AuthAccountUserDetails userDetails = (AuthAccountUserDetails) authToken.getPrincipal();

		// 生成并存储token
		final String token = tokenService.create(userDetails, TokenValue.builder()
				.accountId(userDetails.getId())
				.username(userDetails.getUsername())
				.authorities(grantedAuthorityConverter.encode(userDetails.getAuthorities()))
				.build());

		// 返回用户信息
		final LoginResult result = LoginResult.builder()
				.accountId(userDetails.getId())
				.username(userDetails.getUsername())
				.build();

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		// header里返回token
		response.addHeader(tokenProperties.getHeader(), token);

		objectMapper.writeValue(response.getOutputStream(), result);
	}

}
