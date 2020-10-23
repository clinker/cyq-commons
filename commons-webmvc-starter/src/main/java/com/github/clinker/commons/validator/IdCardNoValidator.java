package com.github.clinker.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 身份证号码验证器。null和空字符串视为有效。
 */
public class IdCardNoValidator implements ConstraintValidator<IdCardNoConstraint, String> {

	@Override
	public boolean isValid(final String str, final ConstraintValidatorContext cxt) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return IdCardUtils.validateCard(str);
	}

}
