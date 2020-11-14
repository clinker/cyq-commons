package com.github.clinker.commons.util.num;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字工具类。
 */
public class NumUtils {

	/**
	 * 加法。任意因子为null时，return返回另一因子。
	 *
	 * @param a 数字
	 * @param b 数字
	 * @return a+b
	 */
	public static BigDecimal add(final BigDecimal a, final BigDecimal b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}

		return a.add(b);
	}

	/**
	 * 相乘。如果任意因子为null或空字符串，则返回null。
	 *
	 * @param a 数字
	 * @param b 0.02表示2%
	 * @return a*b
	 */
	public static BigDecimal multiply(final String a, final BigDecimal b) {
		if (a == null || a.isBlank() || b == null) {
			return null;
		}

		return toBigDecimal(a).multiply(b);
	}

	/**
	 * 四舍五入。
	 *
	 * @param num 数字
	 * @return 四舍五入
	 */
	public static BigDecimal round(final BigDecimal num) {
		return num.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 转换成字符串。
	 *
	 * @param num 数字
	 * @return 字符串
	 */
	public static String stringify(final BigDecimal num) {
		if (num == null) {
			return null;
		}
		return num.toPlainString();
	}

	/**
	 * 合计。
	 *
	 * @param nums 字符串
	 * @return 合计
	 */
	public static BigDecimal sum(final String... nums) {
		BigDecimal sum = BigDecimal.ZERO;
		for (final String num : nums) {
			if (num != null && !num.isBlank()) {
				sum = sum.add(toBigDecimal(num));
			}
		}
		return sum;
	}

	/**
	 * 转换成BigDecimal。
	 *
	 * @param str 字符串
	 * @return BigDecimal
	 */
	public static BigDecimal toBigDecimal(final String str) {
		if (str == null || str.isBlank()) {
			return null;
		}

		return new BigDecimal(str);
	}

	private NumUtils() {

	}

}
