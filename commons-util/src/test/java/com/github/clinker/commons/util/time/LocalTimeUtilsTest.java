/*******************************************************************************
 * Copyright (c) 陈华(clinker@163.com).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.github.clinker.commons.util.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class LocalTimeUtilsTest {

	@Test
	public void testFormatLocalDate() throws Exception {
		final LocalDate localDate = LocalDate.of(2001, Month.APRIL, 2);
		assertEquals("2001-04-02", LocalTimeUtils.format(localDate));
	}

	@Test
	public void testFormatLocalDateTime() throws Exception {
		final LocalDateTime localDateTime = LocalDateTime.of(2001, Month.APRIL, 2, 18, 19, 20);
		assertEquals("2001-04-02 18:19:20", LocalTimeUtils.format(localDateTime));
	}

	@Test
	public void testFormatToDateLocalDateTime() throws Exception {
		final LocalDateTime localDateTime = LocalDateTime.of(2001, Month.APRIL, 2, 18, 19, 20);
		assertEquals("2001-04-02", LocalTimeUtils.formatToDate(localDateTime));
	}

	@Test
	public void testFormatToDateString() throws Exception {
		assertEquals("2001-04-02", LocalTimeUtils.formatToDate("2001-04-02 18:19:20"));
	}

	@Test
	public void testFrom() throws Exception {
		final Instant instant = Instant.parse("2015-08-18T03:35:20.774Z");

		assertEquals(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()), LocalTimeUtils.from(instant));
	}

	@Test
	public void testFromString() throws Exception {
		final Instant instant = Instant.parse("2015-08-18T03:35:20.774Z");
		assertEquals(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
				LocalTimeUtils.from("2015-08-18T03:35:20.774Z"));
	}

	@Test
	public void testParse() throws Exception {
		assertEquals(LocalDateTime.of(2001, Month.APRIL, 2, 18, 19, 20), LocalTimeUtils.parse("2001-04-02 18:19:20"));
	}

	@Test
	public void testParseDate() throws Exception {
		assertEquals(LocalDate.of(2001, Month.APRIL, 2), LocalTimeUtils.parseDate("2001-04-02"));
	}

}
