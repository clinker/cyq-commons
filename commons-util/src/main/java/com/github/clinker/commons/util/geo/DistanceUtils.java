package com.github.clinker.commons.util.geo;

/**
 * 距离工具类。
 */
public class DistanceUtils {

	/**
	 * 根据距离长短加上不同的单位。有两个单位：米和千米。小于1000则表示为米；否则，表示为千米，十分位四舍五入到百分位。例如800->800米， 1001
	 * ->1km，1500->1.5km，
	 *
	 * @param distance 距离，单位：米
	 * @return 单位为千米或米的距离
	 */
	public static String smartDistance(final double distance) {
		final boolean isKm = distance >= 1000;

		final String unit = isKm ? "km" : "m";
		final double num = isKm ? distance / 1000.0 : distance;

		String formatted = String.format("%.1f", num);
		if (formatted.endsWith(".0")) {
			formatted = formatted.substring(0, formatted.length() - 2);
		}

		return formatted + unit;
	}

	private DistanceUtils() {

	}
}
