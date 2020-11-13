package com.github.clinker.commons.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateValidatorTest {

	private final DateValidator validator = new DateValidator();

	@Test
	void testIsValid_fail() {
		assertFalse(validator.isValid("2099-01-0", null));
	}

	@Test
	void testIsValid_success() {
		assertTrue(validator.isValid("2099-01-02", null));
	}

	@Test
	void testValid_null() {
		assertTrue(validator.isValid(null, null));
	}
}
