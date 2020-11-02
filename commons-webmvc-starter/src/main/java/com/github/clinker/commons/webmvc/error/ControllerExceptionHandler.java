package com.github.clinker.commons.webmvc.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.clinker.commons.util.exception.ErrorUtils;
import com.github.clinker.commons.util.exception.ForbiddenError;
import com.github.clinker.commons.util.exception.ForbiddenException;
import com.github.clinker.commons.util.exception.ServiceError;
import com.github.clinker.commons.util.exception.ServiceException;
import com.github.clinker.commons.util.exception.SystemError;
import com.github.clinker.commons.util.exception.UnauthorizedError;
import com.github.clinker.commons.util.exception.UnauthorizedException;
import com.github.clinker.commons.util.exception.ValidationError;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理。返回两个字段：{@link ErrorUtils#CODE}和{@link ErrorConstants#ERROR_MESSAGE。}
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	/**
	 * 参数校验错误的{@link #MESSAGE}值，格式：message1;message2...messageN
	 *
	 * @param fieldErrors
	 * @return
	 */
	private String fieldErrorToString(final List<FieldError> fieldErrors) {
		return fieldErrors.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(";"));
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<?, ?> handleBindException(final BindException ex) {
		log.error("Handle bind exception: {}", ex.getMessage());

		return response(ValidationError.INVALID, fieldErrorToString(ex.getBindingResult()
				.getFieldErrors()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<?, ?> handleException(final Exception ex) {
		final Throwable cause = ex.getCause();
		if (cause instanceof ServiceException) {
			return handleServiceException((ServiceException) cause);
		}

		log.error("Handle exception: {}", ex.getMessage(), ex);

		return response(SystemError.SYSTEM_ERROR, "系统内部错误");
	}

	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Map<?, ?> handleForbiddenException(final ForbiddenException ex) {
		log.error("Handle forbidden exception: {}", ex.getMessage());

		return response(ForbiddenError.FORBIDDEN, "没有权限");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<?, ?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
		log.error("Handle validation exception: {}", ex.getMessage());

		return response(ValidationError.INVALID, fieldErrorToString(ex.getBindingResult()
				.getFieldErrors()));
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<?, ?> handleServiceException(final ServiceException ex) {
		log.error("Handle service exception: {}. {}", ex.getMessage(), ex.getStackTrace()[0]);

		return response(ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<?, ?> handleUnauthorizedException(final UnauthorizedException ex) {
		log.error("Handle unauthorized exception: {}", ex.getMessage());

		return response(UnauthorizedError.UNAUTHORIZED, "您尚未登录，请登录");
	}

	private Map<?, ?> response(final ServiceError error, final String errorMessage) {
		return response(error.getErrorCode(), errorMessage);
	}

	private Map<?, ?> response(final String errorCode, final String errorMessage) {
		final Map<String, String> errorResponse = new HashMap<>(2);

		errorResponse.put(ErrorUtils.CODE, errorCode);
		errorResponse.put(ErrorUtils.MESSAGE, errorMessage);

		return errorResponse;
	}

}
