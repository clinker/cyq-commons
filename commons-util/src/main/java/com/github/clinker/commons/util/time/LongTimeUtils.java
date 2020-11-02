package com.github.clinker.commons.util.time;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 时间默认为本地时区，以长整数表示。
 */
public class LongTimeUtils {

	/**
	 * 系统默认时区。
	 */
	private static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

	/**
	 * 按系统默认时区格式化时间。格式：{@link DateTimeFormatters#DATE_TIME}。
	 *
	 * @param epochMilli the number of milliseconds since the epoch of
	 *                   1970-01-01T00:00:00Z
	 * @return 格式化后的系统时区当前时间
	 */
	public static String format(final long epochMilli) {
		return format(epochMilli, SYSTEM_ZONE_ID);
	}

	/**
	 * 按指定时区格式化时间。格式：{@link DateTimeFormatters#DATE_TIME}。
	 *
	 * @param epochMilli the number of milliseconds since the epoch of
	 *                   1970-01-01T00:00:00Z
	 * @param zoneId     zone ID
	 * @return 格式化后的时间
	 */
	public static String format(final long epochMilli, final ZoneId zoneId) {
		return DateTimeFormatters.DATE_TIME.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), zoneId));
	}

	/**
	 * 按系统默认时区格式化时间。格式：{@link DateTimeFormatters#DATE}。
	 *
	 * @param epochMilli the number of milliseconds since the epoch of
	 *                   1970-01-01T00:00:00Z
	 * @return 格式化后的日期
	 */
	public static String formatToDate(final long epochMilli) {
		return formatToDate(epochMilli, SYSTEM_ZONE_ID);
	}

	/**
	 * 按指定时区格式化时间。格式：{@link DateTimeFormatters#DATE}。
	 *
	 * @param epochMilli the number of milliseconds since the epoch of
	 *                   1970-01-01T00:00:00Z
	 * @param zoneId     zone ID
	 * @return 格式化后的日期
	 */
	public static String formatToDate(final long epochMilli, final ZoneId zoneId) {
		return DateTimeFormatters.DATE.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), zoneId));
	}

	/**
	 * 将本地时间转换为UTC时间。
	 *
	 * @param dateTime 格式为yyyy-MM-dd HH:mm:ss的本地时间
	 * @return epoch milli
	 */
	public static long localTimeToEpochMilli(final String dateTime) {
		final LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatters.DATE_TIME);
		final ZonedDateTime zdt = localDateTime.atZone(SYSTEM_ZONE_ID);

		return zdt.toInstant()
				.toEpochMilli();
	}

	/**
	 * UTC当前时间，单位：毫秒 。
	 *
	 * @return the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
	 */
	public static long now() {
		return Instant.now()
				.toEpochMilli();
	}

	/**
	 * Obtains an instance of {@code Instant} using milliseconds from the epoch of
	 * 1970-01-01T00:00:00Z.
	 * <p>
	 * The seconds and nanoseconds are extracted from the specified milliseconds.
	 *
	 * @param epochMilli the number of milliseconds from 1970-01-01T00:00:00Z
	 * @return an instant, not null
	 * @throws DateTimeException if the instant exceeds the maximum or minimum
	 *                           instant
	 */
	public static Instant toInstant(final long epochMilli) {
		return Instant.ofEpochMilli(epochMilli);
	}

	private LongTimeUtils() {

	}

}
