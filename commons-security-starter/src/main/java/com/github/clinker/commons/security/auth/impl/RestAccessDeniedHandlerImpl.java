package com.github.clinker.commons.security.auth.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.security.auth.RestAccessDeniedHandler;
import com.github.clinker.commons.util.exception.ErrorUtils;
import com.github.clinker.commons.util.exception.ForbiddenError;

/**
 * 返回“没有权限“。
 */
public class RestAccessDeniedHandlerImpl implements RestAccessDeniedHandler {

	private static final Map<?, ?> FORBIDDEN = Map.of(ErrorUtils.CODE, ForbiddenError.FORBIDDEN.getErrorCode(),
			ErrorUtils.MESSAGE, "没有权限");

	private final ObjectMapper objectMapper;

	public RestAccessDeniedHandlerImpl(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void output(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		objectMapper.writeValue(response.getOutputStream(), FORBIDDEN);
	}

}
