package com.github.clinker.commons.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 无授权时，返回状态码403、JSON错误消息。
 */
public interface RestAccessDeniedHandler extends AccessDeniedHandler {

	@Override
	default void handle(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		setContentType(response);
		setContentType(response);

		output(request, response, accessDeniedException);

	}

	/**
	 * 输出响应。
	 *
	 * @param request               HttpServletRequest
	 * @param response              HttpServletResponse
	 * @param accessDeniedException AuthenticationException
	 */
	void output(HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException, ServletException;

	default void setContentType(final HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}

	default void setStatus(final HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
