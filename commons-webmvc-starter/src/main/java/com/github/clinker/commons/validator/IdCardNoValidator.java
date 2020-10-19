package com.github.clinker.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 身份证号码验证器。
 */
public class IdCardNoValidator implements ConstraintValidator<IdCardNoConstraint, String> {

	@Override
	public boolean isValid(final String str, final ConstraintValidatorContext cxt) {
		return IdCardUtils.validateCard(str);
	}
}
