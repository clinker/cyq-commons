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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class HexUtilsTest {

	@Test
	public void testBytesToHexByteArray() throws Exception {
		assertEquals("7fffffffffffffff", HexUtils.bytesToHex(new byte[] { 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
				(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff }));
	}

	@Test
	public void testBytesToHexByteArrayIntInt() throws Exception {
		assertEquals("7fffffffffffffff", HexUtils.bytesToHex(new byte[] { 0x01, 0x02, 0x7f, (byte) 0xff, (byte) 0xff,
				(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x03 }, 2, 8));
	}

	@Test
	public void testBytesToHexByteArrayIntInt_one_byte() throws Exception {
		assertEquals("09", HexUtils.bytesToHex(new byte[] { 0x09 }, 0, 1));
	}

	@Test
	public void testHexToBytes() throws Exception {
		assertArrayEquals(new byte[] { 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
				(byte) 0xff, (byte) 0xff }, HexUtils.hexToBytes("7fffffffffffffff"));
	}

	@Test
	public void testHexToBytes_null() throws Exception {
		assertNull(HexUtils.hexToBytes(null));
	}

}
