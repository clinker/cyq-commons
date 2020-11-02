package com.github.clinker.commons.util.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 时间为{@link LocalDateTime}或{@link LocalDate}，以字符串表示。
 */
public class LocalTimeUtils {

	/**
	 * 格式化本地日期。
	 *
	 * @param localDate {@link LocalDate}
	 * @return 格式：{@link DateTimeFormatters#DATE}
	 */
	public static String format(final LocalDate localDate) {
		return DateTimeFormatters.DATE.format(localDate);
	}

	/**
	 * 格式化本地时间。
	 *
	 * @param localDateTime {@link LocalDateTime}
	 * @return 格式：{@link DateTimeFormatters#DATE_TIME}
	 */
	public static String format(final LocalDateTime localDateTime) {
		return DateTimeFormatters.DATE_TIME.format(localDateTime);
	}

	/**
	 * 格式化本地时间，只取日期部分。
	 *
	 * @param localDateTime {@link LocalDateTime}
	 * @return 格式：{@link DateTimeFormatters#DATE}
	 */
	public static String formatToDate(final LocalDateTime localDateTime) {
		return DateTimeFormatters.DATE.format(localDateTime);
	}

	/**
	 * 格式化本地时间，只取日期部分。
	 *
	 * @param localDateTime {@link LocalDateTime}，格式：{@link DateTimeFormatters#DATE_TIME}
	 * @return 格式：{@link DateTimeFormatters#DATE}
	 */
	public static String formatToDate(final String localDateTime) {
		return formatToDate(parse(localDateTime));
	}

	/**
	 * 将<code>Instant</code>转换为系统默认时区的日期时间。
	 *
	 * @param instant {@link Instant}
	 * @return 系统默认时区的日期时间
	 */
	public static LocalDateTime from(final Instant instant) {
		return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
				.toLocalDateTime();
	}

	/**
	 * 将<code>ISO 8601</code>格式的时间字符串转换为系统默认时区的日期时间。
	 *
	 * @param iso8601DateTime 格式是ISO 8601的时间，例如<code>2015-08-18T03:35:20.774Z</code>
	 * @return 系统默认时区的日期时间
	 */
	public static LocalDateTime from(final String iso8601DateTime) {
		return ZonedDateTime.ofInstant(Iso8601TimeUtils.parse(iso8601DateTime), ZoneId.systemDefault())
				.toLocalDateTime();
	}

	/**
	 * 本地当前时间。
	 *
	 * @return 当前时间，格式：{@link DateTimeFormatters#DATE_TIME}
	 */
	public static String now() {
		return format(LocalDateTime.now());
	}

	/**
	 * 本地当前日期。
	 *
	 * @return 当前日期，格式：{@link DateTimeFormatters#DATE}
	 */
	public static String nowDate() {
		return format(LocalDate.now());
	}

	/**
	 * 将字符串解析为 {@link LocalDateTime}。
	 *
	 * @param dateTime 格式：{@link DateTimeFormatters#DATE_TIME}
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime parse(final String dateTime) {
		return LocalDateTime.parse(dateTime, DateTimeFormatters.DATE_TIME);
	}

	/**
	 * 将字符串解析为 {@link LocalDate}。
	 *
	 * @param date 格式：{@link DateTimeFormatters#DATE}
	 * @return {@link LocalDate}
	 */
	public static LocalDate parseDate(final String date) {
		return LocalDate.parse(date, DateTimeFormatters.DATE);
	}

	private LocalTimeUtils() {

	}

}
