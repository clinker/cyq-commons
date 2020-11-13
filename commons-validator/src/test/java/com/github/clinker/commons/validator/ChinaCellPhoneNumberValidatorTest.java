package com.github.clinker.commons.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChinaCellPhoneNumberValidatorTest {

	private final ChinaCellPhoneNumberValidator validator = new ChinaCellPhoneNumberValidator();

	@Test
	void testIsValid_fail() {
		assertFalse(validator.isValid("1301234567", null));
	}

	@Test
	void testIsValid_success() {
		assertTrue(validator.isValid("13012345678", null));
	}

	@Test
	void testValid_null() {
		assertTrue(validator.isValid(null, null));
	}
}
