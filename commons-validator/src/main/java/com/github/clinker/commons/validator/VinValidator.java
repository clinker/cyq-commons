package com.github.clinker.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 车架号验证器。17位字符串。null和空字符串视为有效。
 */
public class VinValidator implements ConstraintValidator<VinConstraint, String> {

	@Override
	public boolean isValid(final String str, final ConstraintValidatorContext context) {
		if (str == null || str.isEmpty()) {
			return true;
		}

		return str.length() == 17;
	}

}
