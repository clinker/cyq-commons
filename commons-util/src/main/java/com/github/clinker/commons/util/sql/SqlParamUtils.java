package com.github.clinker.commons.util.sql;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SQL参数工具类。
 */
public class SqlParamUtils {

	private static final String LIKE_ESCAPE = "/";

	/**
	 * 将集合里的对象转换为被单引号包含的IN参数。不能用于占位符，必须作为SQL直接拼接。
	 *
	 * @param col 集合
	 * @return 用于SQL IN、逗号分隔的字符串，例如：'a','b'
	 */
	public static String in(final Collection<?> col) {
		return in(col.stream());
	}

	/**
	 * 将流里的对象转换为被单引号包含的IN参数。不能用于占位符，必须作为SQL直接拼接。
	 *
	 * @param stream 流
	 * @return 用于SQL IN、逗号分隔的字符串，例如：'a','b'
	 */
	public static String in(final Stream<?> stream) {
		return stream.map(t -> "'" + t + "'")
				.collect(Collectors.joining(","));
	}

	/**
	 * 把IN转变成多个OR。
	 *
	 * @param column 字段名
	 * @param values 字段值
	 */
	public static String inToOr(String column, Collection<?> values) {
		if (column == null || column.isBlank() || values == null || values.isEmpty()) {
			return null;
		}

		return values.stream()
				.map(value -> column + "='" + value + "'")
				.collect(Collectors.joining(" OR ", "(", ")"));
	}

	/**
	 * 在字符串前后增加百分号。并对参数转义。不能用于占位符，必须作为SQL直接拼接。
	 *
	 * @param arg 字符串
	 * @return '%arg%' ESCAPE '/'
	 */
	public static String like(final String arg) {
		if (arg == null) {
			return null;
		}
		return "'%" + likeEscape(arg) + "%' ESCAPE '" + LIKE_ESCAPE + "'";
	}

	/**
	 * LIKE语句的参数进行转义。用<code>/</code>作为转义符。
	 *
	 * @param arg 字符串
	 * @return 转义后的字符串
	 */
	private static String likeEscape(final String arg) {
		if (arg == null) {
			return arg;
		}
		String result = arg.replaceAll(LIKE_ESCAPE, LIKE_ESCAPE.repeat(2));
		result = result.replaceAll("_", LIKE_ESCAPE + "_");
		result = result.replaceAll("%", LIKE_ESCAPE + "%");

		return result;
	}

	private SqlParamUtils() {

	}

}
