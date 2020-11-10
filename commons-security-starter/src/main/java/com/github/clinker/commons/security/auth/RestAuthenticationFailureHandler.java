package com.github.clinker.commons.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 认证失败后，返回状态码400、JSON错误消息。
 */
public interface RestAuthenticationFailureHandler extends AuthenticationFailureHandler {

	@Override
	default void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException exception) throws IOException, ServletException {
		setContentType(response);
		setContentType(response);

		output(request, response, exception);
	}

	/**
	 * 输出响应。
	 *
	 * @param request   HttpServletRequest
	 * @param response  HttpServletResponse
	 * @param exception AuthenticationException
	 */
	void output(HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
			throws IOException, ServletException;

	default void setContentType(final HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}

	default void setStatus(final HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
