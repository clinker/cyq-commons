package com.github.clinker.commons.util.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期工具类。
 */
public final class DateUtils {

	/**
	 * 零点
	 */
	private static final String ZERO = " 00:00:00";

	/**
	 * 计算日期之间的天数。
	 *
	 * @param startDate startDate 开始日期，含，例如：2099-01-02
	 * @param endDate   endDate 结束日期，含，例如：2099-01-02
	 * @return 日期之间的天数
	 */
	public static long days(final LocalDate startDate, final LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
	}

	/**
	 * 将结束日期转换为日期时间。含。
	 *
	 * @param endDateInclusive 结束日期，含，格式：yyyy-MM-dd。例如：2099-01-02
	 * @return 结束日期的第二天的零点
	 */
	public static String endDateTimeExclusive(final String endDateInclusive) {
		String endDateTimeExclusive = null;
		if (endDateInclusive != null && !endDateInclusive.isEmpty()) {
			final LocalDate tomorrow = LocalTimeUtils.parseDate(endDateInclusive)
					.plusDays(1);

			endDateTimeExclusive = LocalTimeUtils.format(tomorrow) + ZERO;
		}

		return endDateTimeExclusive;
	}

	/**
	 * 计算日期所在月的第一天和最后一天。
	 *
	 * @param date 日期，例如：2099-01-02
	 * @return 日期所在月的第一天和最后一天
	 */
	public static LocalDate[] monthStartAndEndDate(final LocalDate date) {
		return new LocalDate[] { date.with(TemporalAdjusters.firstDayOfMonth()),
				date.with(TemporalAdjusters.lastDayOfMonth()) };
	}

	/**
	 * 将开始日期转换为日期时间。不含。
	 *
	 * @param startDateInclusive 开始日期，含，格式：yyyy-MM-dd。例如：2099-01-02
	 * @return 指定日期第二天的零点
	 */
	public static String startDateTimeInclusive(final String startDateInclusive) {
		String startDateTimeInclusive = null;
		if (startDateInclusive != null && !startDateInclusive.isEmpty()) {
			startDateTimeInclusive = startDateInclusive + ZERO;
		}

		return startDateTimeInclusive;
	}

	/**
	 * 计算日期所在周的第一天和最后一天。第一天是周一。
	 *
	 * @param date 日期，例如：2099-01-02
	 * @return 日期所在周的第一天和最后一天
	 */
	public static LocalDate[] weekStartAndEndDate(final LocalDate date) {
		return new LocalDate[] { date.with(DayOfWeek.MONDAY), date.with(DayOfWeek.SUNDAY) };
	}

	private DateUtils() {

	}
}
