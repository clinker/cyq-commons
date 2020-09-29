package com.github.clinker.commons.util.image;

import java.util.Base64;

/**
 * 图片转换为Base64字符串。
 */
public class ImageToBase64Utils {

	/**
	 * 转换为字符串。
	 *
	 * @param ba     图片的字节数组
	 * @param format 格式，例如：jpeg
	 * @return base64字符串
	 */
	public static String convert(final byte[] ba, final String format) {
		return "data:image/" + format + ";base64," + Base64.getEncoder()
				.encodeToString(ba);
	}

	/**
	 * JPEG图片转换为字符串。
	 *
	 * @param ba 图片的字节数组
	 * @return base64字符串
	 */
	public static String convertJpeg(final byte[] ba) {
		return convert(ba, "jpeg");
	}

	/**
	 * PNG图片转换为字符串。
	 *
	 * @param ba 图片的字节数组
	 * @return base64字符串
	 */
	public static String convertPng(final byte[] ba) {
		return convert(ba, "png");
	}

	private ImageToBase64Utils() {

	}
}
