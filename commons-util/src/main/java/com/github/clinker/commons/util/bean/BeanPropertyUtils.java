package com.github.clinker.commons.util.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析Bean属性。不支持嵌套类，即成员只能是基本类型和String。
 */
public class BeanPropertyUtils {

	/**
	 * 缓存反射结果
	 */
	private static final Map<Class<?>, PropertyDescriptor[]> CACHE = new ConcurrentHashMap<>();

	/**
	 * 解析对象，生成有序的键值对。按键的字母顺序排序，区分大小写。
	 *
	 * @param o                 对象
	 * @param ignoreNullValue   是否忽略值为null的属性
	 * @param ignoreEmptyString 是否忽略值为""的字符串
	 * @return 对象属性的键值对
	 */
	public static LinkedHashMap<String, Object> parse(final Object o, final boolean ignoreNullValue,
			final boolean ignoreEmptyString) {
		final List<String> keys = new ArrayList<>();
		final Map<String, Object> keyValues = new HashMap<>();
		final Class<?> cls = o.getClass();

		// 如果不在缓存里，则获取bean信息后，加入缓存
		final PropertyDescriptor[] propertyDescriptors = CACHE.computeIfAbsent(cls, v -> {
			try {
				return Introspector.getBeanInfo(cls)
						.getPropertyDescriptors();
			} catch (final IntrospectionException e) {
				throw new RuntimeException(e);
			}
		});

		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if ("class".equals(propertyDescriptor.getName())) {
				// 不支持嵌套
				continue;
			}
			if (propertyDescriptor.getReadMethod() == null) {
				// 必须有getter
				continue;
			}
			Object value = null;
			try {
				value = propertyDescriptor.getReadMethod()
						.invoke(o);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
			if (value == null && ignoreNullValue) {
				// 忽略
				continue;
			}
			if ("".equals(value) && ignoreEmptyString) {
				// 忽略
				continue;
			}
			keys.add(propertyDescriptor.getName());
			keyValues.put(propertyDescriptor.getName(), value);
		}

		// 排序，区分大小写
		Collections.sort(keys, String::compareTo);

		final LinkedHashMap<String, Object> result = new LinkedHashMap<>(keys.size());
		keys.forEach(key -> {
			result.put(key, keyValues.get(key));
		});

		return result;
	}

	/**
	 * 解析对象，生成有序的键值对。按键的字母顺序排序，区分大小写。不包括值为null、空字符串的属性。
	 *
	 * @param o 对象
	 * @return 对象属性的键值对。不包括值为null、空字符串的属性
	 */
	public static LinkedHashMap<String, Object> parseExcludeEmptyValue(final Object o) {
		return parse(o, true, true);
	}

	private BeanPropertyUtils() {

	}

}
