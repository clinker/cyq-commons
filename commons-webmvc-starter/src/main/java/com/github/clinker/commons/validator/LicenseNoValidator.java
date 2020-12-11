package com.github.clinker.commons.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 车牌号验证器。null和空字符串视为有效。
 */
public class LicenseNoValidator implements ConstraintValidator<LicenseNoConstraint, String> {

	private static final Pattern PATTERN = Pattern.compile(
			"^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|"
					+ "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|"
					+ "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");

	@Override
	public boolean isValid(final String str, final ConstraintValidatorContext context) {
		if (str == null || str.isEmpty()) {
			// 允许为空
			return true;
		}

		return PATTERN.matcher(str)
				.matches();
	}

}
