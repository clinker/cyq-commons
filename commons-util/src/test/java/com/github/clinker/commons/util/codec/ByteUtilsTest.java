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
package com.github.clinker.commons.util.codec;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest {

	@Test
	public void testBytesToInt() throws Exception {
		assertEquals(72, ByteUtils.bytesToInt(new byte[] { 0x00, 0x00, 0x00, 0x48 }, 0, 4));
	}

	@Test
	public void testBytesToLong() throws Exception {
		assertEquals(Long.MAX_VALUE, ByteUtils.bytesToLong(new byte[] { 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
				(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff }, 0, 8));
	}

	@Test
	public void testLongToBytes() throws Exception {
		assertArrayEquals(new byte[] { 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
				(byte) 0xff, (byte) 0xff }, ByteUtils.longToBytes(Long.MAX_VALUE, 0, 8));
	}

}
