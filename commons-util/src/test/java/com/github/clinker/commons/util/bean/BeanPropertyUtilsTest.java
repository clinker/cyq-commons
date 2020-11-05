package com.github.clinker.commons.util.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class BeanPropertyUtilsTest {

	class Person {

		private String name;

		private String name1;

		private int age;

		private String name3;

		private String name2;

		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}

		public String getName1() {
			return name1;
		}

		public String getName2() {
			return name2;
		}

		public String getName3() {
			return name3;
		}

		public void setAge(final int age) {
			this.age = age;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public void setName1(final String name1) {
			this.name1 = name1;
		}

		public void setName2(final String name2) {
			this.name2 = name2;
		}

		public void setName3(final String name3) {
			this.name3 = name3;
		}

	}

	@Test
	void testParse() {
		final Person person = new Person();
		person.setName("zhangsan");
		person.setAge(18);

		final LinkedHashMap<String, Object> keyValues = BeanPropertyUtils.parse(person, false);
		assertEquals(5, keyValues.size());

		// key是否有序
		final List<String> keys = new ArrayList<>(keyValues.keySet());
		assertEquals("age", keys.get(0));
		assertEquals("name", keys.get(1));
		assertEquals("name1", keys.get(2));
		assertEquals("name2", keys.get(3));
		assertEquals("name3", keys.get(4));

		// 值是否正确
		assertEquals(18, keyValues.get("age"));
		assertEquals("zhangsan", keyValues.get("name"));
		assertNull(keyValues.get("name1"));
		assertNull(keyValues.get("name2"));
		assertNull(keyValues.get("name3"));
	}

	@Test
	void testParseExcludeNullValue() {
		final Person person = new Person();
		person.setName("zhangsan");
		person.setAge(18);

		final LinkedHashMap<String, Object> keyValues = BeanPropertyUtils.parseExcludeNullValue(person);
		assertEquals(2, keyValues.size());

		// key是否有序
		final List<String> keys = new ArrayList<>(keyValues.keySet());
		assertEquals("age", keys.get(0));
		assertEquals("name", keys.get(1));

		// 值是否正确
		assertEquals(18, keyValues.get("age"));
		assertEquals("zhangsan", keyValues.get("name"));
	}

}
