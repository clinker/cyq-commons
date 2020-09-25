package com.github.clinker.commons.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTimeValidatorTest {

	private final DateTimeValidator validator = new DateTimeValidator();

	@Test
	void testIsValid_fail() {
		assertFalse(validator.isValid("2099-01-02 03:04", null));
	}

	@Test
	void testIsValid_success() {
		assertTrue(validator.isValid("2099-01-02 03:04:05", null));
	}

	@Test
	void testValid_null() {
		assertTrue(validator.isValid(null, null));
	}
}
