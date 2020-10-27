package com.github.clinker.commons.http;

/**
 * HTTP状态不是200时的异常。
 */
public class HttpStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int httpStatusCode;

	private final String responseString;

	public HttpStatusException(final int httpStatusCode, final String responseString) {
		super(responseString);
		this.httpStatusCode = httpStatusCode;
		this.responseString = responseString;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getResponseString() {
		return responseString;
	}

}
