package com.github.clinker.commons.util.mask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MaskUtilsTest {

	@Test
	public void testMask() throws Exception {
		final String src = "1234567890";
		final String replacement = "abcd";

		assertEquals("123abcd90", MaskUtils.mask(src, 3, 8, replacement));
	}

	@Test
	public void testMask_srcNull() throws Exception {
		final String replacement = "abcd";

		assertEquals("", MaskUtils.mask(null, 3, 8, replacement));
	}

}
