package com.github.clinker.commons.util.geo;

import java.text.DecimalFormat;

/**
 * 地理工具类。
 */
public final class GeoUtils {

	/**
	 * 赤道半径，单位：米
	 */
	private static final double EQUATORIAL_RADIUS = 6_372_800;

	/**
	 * 度分秒(DMS)转换为小数度数 (DDD)。
	 * <p>
	 * 坐标格式:
	 * <ul>
	 * <li>度分秒 (DMS)：41° 24' 12.1674"，2° 10' 26.508"</li>
	 * <li>度和十进制分 (DMM)：41 24.2028，2 10.4418</li>
	 * <li>小数度数 (DDD)：41.40338，2.17403</li>
	 * </ul>
	 *
	 * </p>
	 *
	 * @param lngLat 经度或纬度
	 * @return 保留6位小数的小数度数
	 */
	public static double dmsToDdd(final String lngLat) {
		final String[] array = lngLat.split(" ");

		final double degree = Double.parseDouble(array[0].replaceAll("°", ""));
		final double minute = Double.parseDouble(array[1].replaceAll("'", ""));
		final double second = Double.parseDouble(array[2].replaceAll("\"", ""));

		final double minuteAndSecond = (minute + second / 60) / 60;
		final double result = degree < 0 ? -(Math.abs(degree) + minuteAndSecond) : degree + minuteAndSecond;

		return Double.parseDouble(new DecimalFormat("#.######").format(result));
	}

	/**
	 * 格式化坐标，保留15位小数，最后一位五舍六入。
	 *
	 * @param num 被格式化的数字
	 * @return 格式化后的数字
	 */
	public static double format(final double num) {
		return Double.parseDouble(new DecimalFormat("#.###############").format(num));
	}

	/**
	 * 用Haversine（半正矢）算法计算亮两点距离。源自
	 * <code>http://rosettacode.org/wiki/Haversine_formula#Java</code>.
	 *
	 * @param latitude1  第一点的纬度
	 * @param longitude1 第一点的经度
	 * @param latitude2  第二点的纬度
	 * @param longitude2 第二点的经度
	 * @return 两点间的距离，单位：米
	 */
	public static double haversine(final double latitude1, final double longitude1, final double latitude2,
			final double longitude2) {
		final double dLon = Math.toRadians(longitude2 - longitude1);
		final double dLat = Math.toRadians(latitude2 - latitude1);
		final double lat1 = Math.toRadians(latitude1);
		final double lat2 = Math.toRadians(latitude2);

		return EQUATORIAL_RADIUS * 2 * Math.asin(Math.sqrt(Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)));
	}

	/**
	 * 以坐标为中心，得到与其距离小于半径的正方形的四个顶点的坐标。
	 *
	 * @param latitude  中心点的纬度
	 * @param longitude 中心点的经度
	 * @param raidus    正方形边与中心点的距离，单位：米
	 * @return 4个坐标，依次为左上角、右上角、左下角和右下角。坐标第一个元素是纬度，第二个元素是经度
	 */
	public static double[][] square(final double latitude, final double longitude, final double distance) {
		// 计算经度弧度,从弧度转换为角度
		final double dLongitude = Math.toDegrees(
				2 * Math.asin(Math.sin(distance / (2 * EQUATORIAL_RADIUS)) / Math.cos(Math.toRadians(latitude))));
		// 计算纬度角度
		final double dLatitude = Math.toDegrees(distance / EQUATORIAL_RADIUS);
		// 正方形
		final double[] leftTopPoint = { latitude + dLatitude, longitude - dLongitude };
		final double[] rightTopPoint = { latitude + dLatitude, longitude + dLongitude };
		final double[] leftBottomPoint = { latitude - dLatitude, longitude - dLongitude };
		final double[] rightBottomPoint = { latitude - dLatitude, longitude + dLongitude };

		return new double[][] { leftTopPoint, rightTopPoint, leftBottomPoint, rightBottomPoint };
	}

	private GeoUtils() {

	}
}
