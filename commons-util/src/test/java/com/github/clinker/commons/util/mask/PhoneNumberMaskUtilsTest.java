package com.github.clinker.commons.util.mask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PhoneNumberMaskUtilsTest {

	@Test
	public void testMask() throws Exception {
		assertEquals("130****5678" + "", PhoneNumberMaskUtils.mask("13012345678"));
	}

}
