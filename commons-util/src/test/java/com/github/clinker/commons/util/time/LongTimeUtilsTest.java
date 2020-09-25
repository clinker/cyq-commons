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

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class LongTimeUtilsTest {

	@Test
	public void testFormatLong() throws Exception {
		assertEquals("2099-01-02 03:04:05", LongTimeUtils.format(4070977445000L));
	}

	@Test
	public void testFormatLongZoneId() throws Exception {
		assertEquals("2099-01-02 04:04:05", LongTimeUtils.format(4070977445000L, ZoneId.of("Asia/Tokyo")));
	}

	@Test
	public void testFormatToDateLong() throws Exception {
		assertEquals("2099-01-02", LongTimeUtils.formatToDate(4070977445000L));
	}

	@Test
	public void testFormatToDateLongZoneId() throws Exception {
		assertEquals("2099-01-02", LongTimeUtils.formatToDate(4070977445000L, ZoneId.of("Asia/Tokyo")));
	}

	@Test
	public void testLocalTimeToEpochMilli() throws Exception {
		assertEquals(4070977445000L, LongTimeUtils.localTimeToEpochMilli("2099-01-02 03:04:05"));
	}

}
