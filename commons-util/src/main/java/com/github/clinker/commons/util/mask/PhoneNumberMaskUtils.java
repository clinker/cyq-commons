package com.github.clinker.commons.util.mask;

/**
 * 隐藏手机号码中间5位。
 */
public final class PhoneNumberMaskUtils {

	/**
	 * 替换字符串
	 */
	private static final String MASKS = "****";

	/**
	 * 用星号替换号码的中间4位。
	 *
	 * @param number 手机号码
	 * @return 替换后的号码
	 */
	public static String mask(final String number) {
		return MaskUtils.mask(number, 3, 7, MASKS);
	}

	private PhoneNumberMaskUtils() {

	}

}
