package com.github.clinker.commons.http;

/**
 * HTTP I/O异常。
 */
public class HttpIoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpIoException() {
	}

	public HttpIoException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
