package com.github.clinker.commons.util.mask;

/**
 * 姓名只保留姓，后面加两个星号。
 */
public class NameMaskUtils {

	/**
	 * 替换字符串
	 */
	private static final String MASKS = "**";

	/**
	 * 用星号替换姓名里的名。
	 *
	 * @param name 姓名
	 * @return 替换后的姓名
	 */
	public static String mask(final String name) {
		return MaskUtils.mask(name, 1, name.length(), MASKS);
	}

	private NameMaskUtils() {

	}

}
