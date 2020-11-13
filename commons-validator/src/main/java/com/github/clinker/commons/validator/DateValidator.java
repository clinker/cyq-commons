package com.github.clinker.commons.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.clinker.commons.util.time.DateTimeFormatters;

/**
 * 日期验证器。格式：yyyy-MM-dd。null和空字符串视为有效。
 */
public class DateValidator implements ConstraintValidator<DateConstraint, String> {

	@Override
	public boolean isValid(final String date, final ConstraintValidatorContext context) {
		if (date == null || date.isEmpty()) {
			return true;
		}
		try {
			LocalDate.parse(date, DateTimeFormatters.DATE);
			return true;
		} catch (final DateTimeParseException e) {
			// 空操作
		}

		return false;
	}

}
