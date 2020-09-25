package com.github.clinker.commons.util.exception;

/**
 * 未授权访问异常。例如，未登录。
 */
public class UnauthorizedException extends ServiceException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误
	 */
	private static final ServiceError ERROR = UnauthorizedError.UNAUTHORIZED;

	/**
	 * 错误码为UnauthorizedError.UNAUTHORIZED。错误信息为空字符串。异常为null。
	 */
	public UnauthorizedException() {
		super(ERROR);
	}

	/**
	 * 错误码为UnauthorizedError.UNAUTHORIZED。自定义错误信息。异常为null。
	 *
	 * @param errorMessage 错误信息
	 */
	public UnauthorizedException(final String errorMessage) {
		super(ERROR, errorMessage);
	}

	/**
	 * 错误码为UnauthorizedError.UNAUTHORIZED。自定义错误信息。异常为cause。
	 *
	 * @param errorMessage 错误信息
	 * @param cause        异常
	 */
	public UnauthorizedException(final String errorMessage, final Throwable cause) {
		super(ERROR, errorMessage, cause);
	}

}
