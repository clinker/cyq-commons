package com.github.clinker.commons.security.auth.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.auth.LoginError;
import com.github.clinker.commons.security.auth.RestAuthenticationFailureHandler;
import com.github.clinker.commons.util.exception.ErrorUtils;

/**
 * 返回“输入账号或密码错误”。
 */
public class RestAuthenticationFailureHandlerImpl implements RestAuthenticationFailureHandler {

	private static final Map<?, ?> LOGIN_FAIL = Map.of(ErrorUtils.CODE, LoginError.FAIL.getErrorCode(),
			ErrorUtils.MESSAGE, "账号或密码错误");

	private final ObjectMapper objectMapper;

	public RestAuthenticationFailureHandlerImpl(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void output(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException exception) throws IOException, ServletException {
		objectMapper.writeValue(response.getOutputStream(), LOGIN_FAIL);
	}

}
