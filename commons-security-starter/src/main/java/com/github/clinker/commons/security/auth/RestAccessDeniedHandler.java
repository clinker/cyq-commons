package com.github.clinker.commons.security.auth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.clinker.commons.util.exception.ErrorUtils;
import com.github.clinker.commons.util.exception.ForbiddenError;

/**
 * 无授权时，返回状态码403、JSON错误消息。
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private static final Map<?, ?> FORBIDDEN = Map.of(ErrorUtils.CODE, ForbiddenError.FORBIDDEN.getErrorCode(),
			ErrorUtils.MESSAGE, "没有权限");

	private final ObjectMapper objectMapper;

	public RestAccessDeniedHandler(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		objectMapper.writeValue(response.getOutputStream(), FORBIDDEN);
	}

}
