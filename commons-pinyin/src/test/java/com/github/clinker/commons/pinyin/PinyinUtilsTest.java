package com.github.clinker.commons.pinyin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PinyinUtilsTest {

	@Test
	public void testInitials() {
		assertEquals("djh，hello", PinyinUtils.initials("大家好，hello"));
	}

	@Test
	public void testIsChinese() {
		assertTrue(PinyinUtils.isChinese('我'));
		assertFalse(PinyinUtils.isChinese('a'));
	}

	@Test
	public void testPinyinChar() {
		assertEquals("da", PinyinUtils.pinyin('大'));
	}

	@Test
	void testPinyinString() {
		assertEquals("dajia", PinyinUtils.pinyin("大家"));
	}

	@Test
	void testPinyinStringString() {
		assertEquals("da#jia", PinyinUtils.pinyin("大家", "#"));
	}

}
