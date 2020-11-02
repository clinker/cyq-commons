package com.github.clinker.commons.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.util.exception.ErrorUtils;

/**
 * 认证失败后，返回状态码400、JSON错误消息。
 */
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Map<?, ?> LOGIN_FAIL = Map.of(ErrorUtils.CODE, LoginError.FAIL.getErrorCode(),
			ErrorUtils.MESSAGE, "账号或密码错误");

	private final ObjectMapper objectMapper;

	public RestAuthenticationFailureHandler(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		System.err.println(exception.getMessage());
		objectMapper.writeValue(response.getOutputStream(), LOGIN_FAIL);
	}

}
