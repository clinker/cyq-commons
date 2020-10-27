package com.github.clinker.commons.http;

/**
 * HTTP异常。
 */
public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpException() {
	}

	public HttpException(final Throwable cause) {
		super(cause);
	}

}
