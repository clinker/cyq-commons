package com.github.clinker.commons.util.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {

	@Test
	public void testEndDateTimeExclusive() throws Exception {
		final String date = "2099-01-02";
		assertEquals("2099-01-03 00:00:00", DateUtils.endDateTimeExclusive(date));
	}

	@Test
	public void testMonthStartAndEndDate() throws Exception {
		int year = 2099, month = 1, day = 5;
		LocalDate date = LocalDate.of(year, month, day);

		LocalDate[] dates = DateUtils.monthStartAndEndDate(date);
		assertEquals(LocalDate.of(year, month, 1), dates[0]);
		assertEquals(LocalDate.of(year, month, 31), dates[1]);
	}

	@Test
	public void testStartDateTimeInclusive() throws Exception {
		final String date = "2099-01-02";
		assertEquals("2099-01-02 00:00:00", DateUtils.startDateTimeInclusive(date));
	}

	@Test
	public void testWeekStartAndEndDate() throws Exception {
		LocalDate date = LocalDate.of(2099, 1, 5);

		LocalDate[] dates = DateUtils.weekStartAndEndDate(date);
		assertEquals(date.with(DayOfWeek.MONDAY), dates[0]);
		assertEquals(date.with(DayOfWeek.SUNDAY), dates[1]);
	}

}
