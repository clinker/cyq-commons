package com.github.clinker.commons.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 中国大陆手机号码验证器。验证规则：以1开头的11位数字。null和空字符串视为有效。
 */
public class ChinaCellPhoneNumberValidator implements ConstraintValidator<ChinaCellPhoneNumberConstraint, String> {

	private static final Pattern PATTERN = Pattern.compile("^1\\d{10}$");

	@Override
	public boolean isValid(final String phoneNumber, final ConstraintValidatorContext cxt) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			return false;
		}
		return PATTERN.matcher(phoneNumber)
				.matches();
	}
}
