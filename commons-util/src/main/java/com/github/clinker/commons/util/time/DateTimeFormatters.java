package com.github.clinker.commons.util.time;

import java.time.format.DateTimeFormatter;

/**
 * 日期时间格式化。
 */
public class DateTimeFormatters {

	/**
	 * 日期时间格式
	 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期格式
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 时间格式
	 */
	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern(DATE_PATTERN);

	public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern(TIME_PATTERN);

	private DateTimeFormatters() {

	}

}
