package com.github.clinker.commons.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.AuthAccountUserDetails;
import com.github.clinker.commons.security.auth.impl.LoginResult;
import com.github.clinker.commons.security.token.GrantedAuthorityConverter;
import com.github.clinker.commons.security.token.TokenProperties;
import com.github.clinker.commons.security.token.TokenService;
import com.github.clinker.commons.security.token.TokenValue;

/**
 * 认证成功后，返回状态码200而不是301；同时返回用户信息。
 * <p/>
 * 生成并存储认证token。
 */
public abstract class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final GrantedAuthorityConverter grantedAuthorityConverter;

	private final ObjectMapper objectMapper;

	private final TokenProperties tokenProperties;

	private final TokenService tokenService;

	public RestAuthenticationSuccessHandler(final GrantedAuthorityConverter grantedAuthorityConverter,
			final ObjectMapper objectMapper, final TokenProperties tokenProperties, final TokenService tokenService) {
		this.grantedAuthorityConverter = grantedAuthorityConverter;
		this.objectMapper = objectMapper;
		this.tokenProperties = tokenProperties;
		this.tokenService = tokenService;
	}

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws ServletException, IOException {
		final UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		final AuthAccountUserDetails userDetails = (AuthAccountUserDetails) authToken.getPrincipal();

		// 生成并存储token
		final String token = tokenService.create(userDetails.getId(), TokenValue.builder()
				.accountId(userDetails.getId())
				.username(userDetails.getUsername())
				.authorities(grantedAuthorityConverter.encode(userDetails.getAuthorities()))
				.build());

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		// header里返回token
		response.addHeader(tokenProperties.getHeader(), token);

		output(request, response, authentication, authToken, userDetails, token);
	}

	/**
	 * 输出认证成功响应。
	 *
	 * @param request                             HttpServletRequest
	 * @param response                            HttpServletResponse
	 * @param authentication                      Authentication
	 * @param usernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken
	 * @param authAccountUserDetails              AuthAccountUserDetails
	 * @param token                               生成并已保存的token
	 * @throws IOException IOException
	 */
	protected void output(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication,
			final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
			final AuthAccountUserDetails authAccountUserDetails, final String token) throws IOException {

		// 返回用户信息
		final LoginResult result = LoginResult.builder()
				.accountId(authAccountUserDetails.getId())
				.username(authAccountUserDetails.getUsername())
				.build();

		objectMapper.writeValue(response.getOutputStream(), result);
	}

}
