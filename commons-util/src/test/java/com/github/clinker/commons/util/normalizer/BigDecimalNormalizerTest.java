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
package com.github.clinker.commons.util.normalizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class BigDecimalNormalizerTest {

	@Test
	public void testNormalize() throws Exception {
		final BigDecimalNormalizer bigDecimalNormalizer = new BigDecimalNormalizer();

		assertNull(bigDecimalNormalizer.normalize(null));
		assertEquals("2.11", bigDecimalNormalizer.normalize(new BigDecimal("2.114"))
				.toString());
		assertEquals("2.12", bigDecimalNormalizer.normalize(new BigDecimal("2.115"))
				.toString());
		assertEquals("2.12", bigDecimalNormalizer.normalize(new BigDecimal("2.116"))
				.toString());
		assertEquals("2.10", bigDecimalNormalizer.normalize(new BigDecimal("2.1"))
				.toString());
	}

}
