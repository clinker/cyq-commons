package com.github.clinker.commons.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.clinker.commons.util.time.DateTimeFormatters;

/**
 * 日期时间验证器。格式：yyyy-MM-dd HH:mm:ss。null和空字符串视为有效。
 */
public class DateTimeValidator implements ConstraintValidator<DateTimeConstraint, String> {

	@Override
	public boolean isValid(final String dateTime, final ConstraintValidatorContext context) {
		if (dateTime == null || dateTime.isEmpty()) {
			return true;
		}
		try {
			LocalDateTime.parse(dateTime, DateTimeFormatters.DATE_TIME);
			return true;
		} catch (final DateTimeParseException e) {
			// 空操作
		}

		return false;
	}

}
