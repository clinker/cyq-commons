package com.github.clinker.commons.util.codec;

/**
 * 字节、字节数组与其他数据类型的转换。
 */
public class ByteUtils {

	/**
	 * Convert a byte array (or part thereof) into an integer. The byte array should
	 * be in big-endian form. That is, the byte at index 'offset' should be the
	 * MSB(most significant bit).
	 *
	 * @param byes   The array containing the bytes
	 * @param offset The array index of the MSB
	 * @param size   The number of bytes to convert into the integer
	 * @return An integer value represented by the specified bytes.
	 */
	public static int bytesToInt(final byte[] byes, final int offset, final int size) {
		int num = 0;
		int sw = 8 * (size - 1);
		for (int loop = 0; loop < size; loop++) {
			num |= (byes[offset + loop] & 0x00ff) << sw;
			sw -= 8;
		}
		return num;
	}

	/**
	 * Convert a byte array (or part thereof) into a long. The byte array should be
	 * in big-endian form. That is, the byte at index 'offset' should be the MSB.
	 *
	 * @param bytes  The array containing the bytes
	 * @param offset The array index of the MSB
	 * @param size   The number of bytes to convert into the long
	 * @return An long value represented by the specified bytes.
	 */
	public static long bytesToLong(final byte[] bytes, final int offset, final int size) {
		long num = 0;
		long sw = 8L * (size - 1L);

		for (int loop = 0; loop < size; loop++) {
			num |= ((long) bytes[offset + loop] & 0x00ff) << sw;
			sw -= 8;
		}
		return num;
	}

	/**
	 * 长整数转换为字节数组。
	 *
	 * @param num    被转换的长整数
	 * @param offset 开始索引在字节数组里的偏移量
	 * @param size   字节长度
	 * @return 字节数组
	 */
	public static byte[] longToBytes(final long num, final int offset, final int size) {
		final byte[] b = new byte[size];
		long sw = (size - 1) * 8;
		long mask = 0xffL << sw;
		for (int l = 0; l < size; l++) {
			b[offset + l] = (byte) ((num & mask) >>> sw);
			sw -= 8;
			mask >>>= 8;
		}
		return b;
	}

	private ByteUtils() {
	}

}
