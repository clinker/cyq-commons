package com.github.clinker.commons.util.codec;

/**
 * 16进制与字节数组的相互转换。
 */
public final class HexUtils {

	/**
	 * 将字节数组转换成大写16进制字符串。
	 *
	 * @param bytes 字节数组
	 * @return 16进制字符串
	 */
	public static String bytesToHex(final byte[] bytes) {
		return bytesToHex(bytes, 0, bytes.length);
	}

	/**
	 * 将字节数组从索引offset开始，长度为length的字节，转换成小写16进制字符串。
	 *
	 * @param bytes  字节数组
	 * @param offset 开始索引在字节数组里的偏移量
	 * @param size   字节长度
	 * @return 小写16进制字符串
	 */
	public static String bytesToHex(final byte[] bytes, final int offset, final int size) {
		final StringBuilder hex = new StringBuilder(bytes.length * 2);
		final int end = offset + size;
		for (int n = offset; n < end; n++) {
			final String stmp = Integer.toHexString(bytes[n] & 0Xff);
			hex.append(stmp.length() == 1 ? "0" + stmp : stmp);
		}
		return hex.toString();
	}

	/**
	 * 16进制字符串转换成字节数组。如果hex为null，则返回null。
	 *
	 * @param hex 16进制字符串，不区分大小写
	 * @return 字节数组
	 */
	public static byte[] hexToBytes(final String hex) {
		if (hex == null) {
			return null;
		}
		final char[] arr = hex.toCharArray();
		final byte[] b = new byte[hex.length() / 2];
		String swap = "";
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			swap = Character.toString(arr[i++]) + Character.toString(arr[i]);
			final int byteint = Integer.parseInt(swap, 16) & 0xff;
			b[j] = (byte) byteint;
		}
		return b;
	}

	private HexUtils() {

	}

}
