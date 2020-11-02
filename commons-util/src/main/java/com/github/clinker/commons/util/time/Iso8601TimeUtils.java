package com.github.clinker.commons.util.time;

import java.time.Instant;

/**
 * ISO 8601格式的时间转换。
 */
public class Iso8601TimeUtils {

	/**
	 * 格式化UTC时间。
	 *
	 * @param instant {@link Instant}
	 * @return 时间的ISO 8601格式，格式：<code>2015-08-18T03:35:20.774Z</code>
	 */
	public static String format(final Instant instant) {
		return instant == null ? "" : instant.toString();
	}

	/**
	 * UTC当前时间，格式为<code>ISO 8601</code>。
	 *
	 * @return 当前时间的ISO 8601格式，格式：<code>2015-08-18T03:35:20.774Z</code>
	 */
	public static String now() {
		return format(Instant.now());
	}

	/**
	 * 将<code>ISO 8601</code>格式的时间字符串转换为{@link Instant}。
	 *
	 * @param iso8601DateTime 格式是ISO 8601的时间，例如<code>2015-08-18T03:35:20.774Z</code>
	 * @return instant {@link Instant}
	 */
	public static Instant parse(final String iso8601DateTime) {
		return iso8601DateTime == null || iso8601DateTime.isEmpty() ? null : Instant.parse(iso8601DateTime);
	}

	/**
	 * 将<code>ISO 8601</code>格式的时间字符串转换为毫秒数。
	 *
	 * @param iso8661DateTime 格式是ISO 8601的时间，例如<code>2015-08-18T03:35:20.774Z</code>
	 * @return 毫秒数
	 */
	public static long toMilli(final String iso8601DateTime) {
		return parse(iso8601DateTime).toEpochMilli();
	}

	private Iso8601TimeUtils() {

	}

}
