package com.github.clinker.commons.util.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DistanceUtilsTest {

	@Test
	public void testSmartDistanceKm() throws Exception {
		assertEquals("1.1km", DistanceUtils.smartDistance(1100));
	}

	@Test
	public void testSmartDistanceKmRounded() throws Exception {
		assertEquals("1km", DistanceUtils.smartDistance(1001));
	}

	@Test
	public void testSmartDistanceMeter() throws Exception {
		assertEquals("1.1km", DistanceUtils.smartDistance(1100));
	}

}
