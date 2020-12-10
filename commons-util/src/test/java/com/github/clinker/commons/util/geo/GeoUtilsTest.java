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
package com.github.clinker.commons.util.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GeoUtilsTest {

	@Test
	public void testDmsToDdd() throws Exception {
		assertEquals(GeoUtils.dmsToDdd("30° 20' 10"), 30.336111);
		assertEquals(GeoUtils.dmsToDdd("-30° 20' 10"), -30.336111);
	}

	@Test
	public void testFormat() throws Exception {
		assertEquals(GeoUtils.format(1.1234567890123454), 1.123456789012345);
		assertEquals(GeoUtils.format(1.1234567890123455), 1.123456789012345);
		assertEquals(GeoUtils.format(1.1234567890123456), 1.123456789012346);
	}

	@Test
	public void testHaversine() throws Exception {
		assertEquals(GeoUtils.haversine(36.12, -86.67, 33.94, -118.40), 2887259.95);
	}

	@Test
	public void testSquare() throws Exception {
		final double[] center = new double[] { 30.66357, 104.072212 };

		final double[][] square = GeoUtils.square(center[0], center[1], 3000);

		assertEquals(square[0][0], 30.690542027764756);
		assertEquals(square[0][1], 104.04085562002946);

		assertEquals(square[1][0], 30.690542027764756);
		assertEquals(square[1][1], 104.10356837997053);

		assertEquals(square[2][0], 30.636597972235244);
		assertEquals(square[2][1], 104.04085562002946);

		assertEquals(square[3][0], 30.636597972235244);
		assertEquals(square[3][1], 104.10356837997053);

		// 中心点到四个顶点的直线距离
		final double centerToVertexDistance = Math.sqrt(Math.pow(3000, 2) + Math.pow(3000, 2));

		// 允许的最大误差，单位：米
		final double range = 100;
		// 直接距离与haversine距离作对比
		for (final double[] vertex : square) {
			final double distance = GeoUtils.haversine(vertex[0], vertex[1], center[0], center[01]);
			assertTrue(distance - centerToVertexDistance <= range);
		}
	}

}
