package com.github.clinker.commons.util.mask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NameMaskUtilsTest {

	@Test
	public void testMask() throws Exception {
		assertEquals("张**", NameMaskUtils.mask("张三四"));
		assertEquals("张**", NameMaskUtils.mask("张三"));
	}

}
