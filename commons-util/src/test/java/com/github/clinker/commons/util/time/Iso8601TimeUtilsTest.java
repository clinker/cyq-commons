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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class Iso8601TimeUtilsTest {

	private static final String instantString = "2015-08-18T03:35:20.774Z";

	@Test
	public void testFormat() throws Exception {
		final Instant instant = Instant.parse(instantString);

		assertEquals(instantString, Iso8601TimeUtils.format(instant));
	}

	@Test
	public void testParse() throws Exception {
		assertTrue(Iso8601TimeUtils.parse(instantString)
				.equals(Instant.parse(instantString)));
	}

	@Test
	public void testToMilli() throws Exception {
		assertEquals(1439868920774L, Iso8601TimeUtils.toMilli(instantString));
	}

}
