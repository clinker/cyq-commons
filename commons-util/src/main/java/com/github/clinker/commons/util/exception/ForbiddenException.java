package com.github.clinker.commons.util.exception;

/**
 * 禁止操作异常。
 */
public class ForbiddenException extends ServiceException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误
	 */
	private static final ServiceError ERROR = ForbiddenError.FORBIDDEN;

	/**
	 * 错误码为ForbiddenError.FORBIDDEN。错误信息为空字符串。异常为null。
	 */
	public ForbiddenException() {
		super(ERROR);
	}

	/**
	 * 错误码为ForbiddenError.FORBIDDEN。自定义错误信息。异常为null。
	 *
	 * @param errorMessage 错误信息
	 */
	public ForbiddenException(final String errorMessage) {
		super(ERROR, errorMessage);
	}

	/**
	 * 错误码为ForbiddenError.FORBIDDEN。自定义错误信息。异常为cause。
	 *
	 * @param errorMessage 错误信息
	 * @param cause        异常
	 */
	public ForbiddenException(final String errorMessage, final Throwable cause) {
		super(ERROR, errorMessage, cause);
	}

}
