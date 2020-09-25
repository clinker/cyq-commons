package com.github.clinker.commons.pinyin;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * 拼音工具类。
 */
public class PinyinUtils {

	/**
	 * 获取拼音首字母小写。
	 *
	 * @param str 字符串
	 * @return 小写首字母
	 */
	public static String initials(String str) {
		final StringBuilder sb = new StringBuilder();
		for (final char c : str.toCharArray()) {
			sb.append(pinyin(c).charAt(0));
		}

		return sb.toString()
				.toLowerCase();
	}

	/**
	 * 是否是汉字。
	 *
	 * @param c 字符
	 * @return 是否是汉字
	 */
	public static boolean isChinese(char c) {
		return Pinyin.isChinese(c);
	}

	/**
	 * 中文字符转为小写拼音。非中文返回String.valueOf(c)
	 *
	 * @param c 字符
	 * @return 小写拼音
	 */
	public static String pinyin(char c) {
		return Pinyin.toPinyin(c)
				.toLowerCase();
	}

	/**
	 * 输入字符串里的中文转为小写拼音。
	 *
	 * @param str 字符串
	 * @return 小写拼音
	 */
	public static String pinyin(String str) {
		return Pinyin.toPinyin(str, "")
				.toLowerCase();
	}

	/**
	 * 输入字符串里的中文转为小写拼音，拼音之间插入分隔符。
	 *
	 * @param str       字符串
	 * @param separator 分隔符
	 * @return 小写拼音
	 */
	public static String pinyin(String str, String separator) {
		return Pinyin.toPinyin(str, separator)
				.toLowerCase();
	}

	private PinyinUtils() {
		Pinyin.init(Pinyin.newConfig());
	}
}
