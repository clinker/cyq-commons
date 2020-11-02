package com.github.clinker.commons.webmvc.error;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.clinker.commons.util.exception.ErrorUtils;

/**
 * 错误处理器。返回JSON格式消息。
 */
@RestController
public class RestErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	private HttpStatus getStatus(final HttpServletRequest request) {
		final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (final Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@RequestMapping("/error")
	public ResponseEntity<?> handleError(final HttpServletRequest request) {
		final HttpStatus status = getStatus(request);

		final Map<String, Object> body = new ConcurrentHashMap<>();
		body.put(ErrorUtils.CODE, status.value());

		return new ResponseEntity<>(body,
				status);
	}

}
