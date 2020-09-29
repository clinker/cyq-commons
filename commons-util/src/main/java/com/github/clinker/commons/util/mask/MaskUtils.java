package com.github.clinker.commons.util.mask;

/**
 * 替换字符串里的字符。
 */
public class MaskUtils {

	/**
	 * 将src里的字符替换为replacement。
	 *
	 * @param src         被替换的字符串
	 * @param start       被替换部分在src里的开始索引，从0开始，含
	 * @param end         被替换部分在src里的结束索引，不含
	 * @param replacement 替换字符串
	 * @return
	 */
	public static String mask(final String src, final int start, final int end, final String replacement) {
		if (src == null) {
			return "";
		}

		return src.substring(0, start) + replacement + src.substring(end);
	}

	private MaskUtils() {

	}
}
