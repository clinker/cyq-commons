/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.clinker.commons.util.digest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.github.clinker.commons.util.codec.HexUtils;

/**
 * Operations to simplify common {@link java.security.MessageDigest} tasks. This
 * class is immutable and thread-safe.
 */
public class DigestUtils {

	/**
	 * UTF-8
	 */
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * The MD5 message digest algorithm defined in RFC 1321.
	 */
	private static final String MD5 = "MD5";

	/**
	 * The SHA-1 hash algorithm defined in the FIPS PUB 180-2.
	 */
	private static final String SHA_1 = "SHA-1";

	/**
	 * The SHA-256 hash algorithm defined in the FIPS PUB 180-2.
	 */
	private static final String SHA_256 = "SHA-256";

	/**
	 * The SHA-512 hash algorithm defined in the FIPS PUB 180-2.
	 */
	private static final String SHA_512 = "SHA-512";

	private static final int STREAM_BUFFER_LENGTH = 1024;

	/**
	 * Read through an InputStream and returns the digest for the data
	 *
	 * @param digest The MessageDigest to use (e.g. MD5)
	 * @param data   Data to digest
	 * @return the digest
	 * @throws IOException On error reading from the stream
	 */
	private static byte[] digest(final MessageDigest digest, final InputStream data) throws IOException {
		return updateDigest(digest, data).digest();
	}

	/**
	 * Encodes the given string into a sequence of bytes using the UTF-8 charset,
	 * storing the result into a new byte array.
	 *
	 * @param string the String to encode, may be <code>null</code>
	 * @return encoded bytes, or <code>null</code> if the input string was
	 *         <code>null</code>
	 * @throws NullPointerException Thrown if {@link Charsets#UTF_8} is not
	 *                              initialized, which should never happen since it
	 *                              is required by the Java platform specification.
	 * @since As of 1.7, throws {@link NullPointerException} instead of
	 *        UnsupportedEncodingException
	 * @see <a href=
	 *      "http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">
	 *      Standard charsets</a>
	 * @see #getBytesUnchecked(String, String)
	 */
	private static byte[] getBytesUtf8(final String string) {
		return string.getBytes(UTF_8);
	}

	/**
	 * Returns a <code>MessageDigest</code> for the given <code>algorithm</code> .
	 *
	 * @param algorithm the name of the algorithm requested. See <a href=
	 *                  "http://docs.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA"
	 *                  >Appendix A in the Java Cryptography Architecture Reference
	 *                  Guide</a> for information about standard algorithm names.
	 * @return A digest instance.
	 * @see MessageDigest#getInstance(String)
	 * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is
	 *                                  caught.
	 */
	private static MessageDigest getDigest(final String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Returns an MD5 MessageDigest.
	 *
	 * @return An MD5 digest instance.
	 * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is
	 *                                  caught, which should never happen because
	 *                                  MD5 is a built-in algorithm
	 * @see MessageDigestAlgorithms#MD5
	 */
	public static MessageDigest getMd5Digest() {
		return getDigest(MD5);
	}

	/**
	 * Returns an SHA-1 digest.
	 *
	 * @return An SHA-1 digest instance.
	 * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is
	 *                                  caught, which should never happen because
	 *                                  SHA-1 is a built-in algorithm
	 * @see MessageDigestAlgorithms#SHA_1
	 * @since 1.7
	 */
	public static MessageDigest getSha1Digest() {
		return getDigest(SHA_1);
	}

	/**
	 * Returns an SHA-256 digest.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @return An SHA-256 digest instance.
	 * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is
	 *                                  caught, which should never happen because
	 *                                  SHA-256 is a built-in algorithm
	 * @see MessageDigestAlgorithms#SHA_256
	 */
	public static MessageDigest getSha256Digest() {
		return getDigest(SHA_256);
	}

	/**
	 * Returns an SHA-512 digest.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @return An SHA-512 digest instance.
	 * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is
	 *                                  caught, which should never happen because
	 *                                  SHA-512 is a built-in algorithm
	 * @see MessageDigestAlgorithms#SHA_512
	 */
	public static MessageDigest getSha512Digest() {
		return getDigest(SHA_512);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 *
	 * @param data Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(final byte[] data) {
		return getMd5Digest().digest(data);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 *
	 * @param data Data to digest
	 * @return MD5 digest
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static byte[] md5(final InputStream data) throws IOException {
		return digest(getMd5Digest(), data);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 *
	 * @param data Data to digest; converted to bytes using
	 *             {@link StringUtils#getBytesUtf8(String)}
	 * @return MD5 digest
	 */
	public static byte[] md5(final String data) {
		return md5(getBytesUtf8(data));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex string.
	 *
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(final byte[] data) {
		return HexUtils.bytesToHex(md5(data));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex string.
	 *
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static String md5Hex(final InputStream data) throws IOException {
		return HexUtils.bytesToHex(md5(data));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex string.
	 *
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(final String data) {
		return HexUtils.bytesToHex(md5(data));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
	 *
	 * @param data Data to digest
	 * @return SHA-1 digest
	 * @since 1.7
	 */
	public static byte[] sha1(final byte[] data) {
		return getSha1Digest().digest(data);
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
	 *
	 * @param data Data to digest
	 * @return SHA-1 digest
	 * @throws IOException On error reading from the stream
	 * @since 1.7
	 */
	public static byte[] sha1(final InputStream data) throws IOException {
		return digest(getSha1Digest(), data);
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
	 *
	 * @param data Data to digest; converted to bytes using
	 *             {@link StringUtils#getBytesUtf8(String)}
	 * @return SHA-1 digest
	 */
	public static byte[] sha1(final String data) {
		return sha1(getBytesUtf8(data));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a hex string.
	 *
	 * @param data Data to digest
	 * @return SHA-1 digest as a hex string
	 * @since 1.7
	 */
	public static String sha1Hex(final byte[] data) {
		return HexUtils.bytesToHex(sha1(data));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a hex string.
	 *
	 * @param data Data to digest
	 * @return SHA-1 digest as a hex string
	 * @throws IOException On error reading from the stream
	 * @since 1.7
	 */
	public static String sha1Hex(final InputStream data) throws IOException {
		return HexUtils.bytesToHex(sha1(data));
	}

	/**
	 * Calculates the SHA-1 digest and returns the value as a hex string.
	 *
	 * @param data Data to digest
	 * @return SHA-1 digest as a hex string
	 * @since 1.7
	 */
	public static String sha1Hex(final String data) {
		return HexUtils.bytesToHex(sha1(data));
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-256 digest
	 * @since 1.4
	 */
	public static byte[] sha256(final byte[] data) {
		return getSha256Digest().digest(data);
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-256 digest
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static byte[] sha256(final InputStream data) throws IOException {
		return digest(getSha256Digest(), data);
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest; converted to bytes using
	 *             {@link StringUtils#getBytesUtf8(String)}
	 * @return SHA-256 digest
	 * @since 1.4
	 */
	public static byte[] sha256(final String data) {
		return sha256(getBytesUtf8(data));
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-256 digest as a hex string
	 * @since 1.4
	 */
	public static String sha256Hex(final byte[] data) {
		return HexUtils.bytesToHex(sha256(data));
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-256 digest as a hex string
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static String sha256Hex(final InputStream data) throws IOException {
		return HexUtils.bytesToHex(sha256(data));
	}

	/**
	 * Calculates the SHA-256 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-256 digest as a hex string
	 * @since 1.4
	 */
	public static String sha256Hex(final String data) {
		return HexUtils.bytesToHex(sha256(data));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-512 digest
	 * @since 1.4
	 */
	public static byte[] sha512(final byte[] data) {
		return getSha512Digest().digest(data);
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-512 digest
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static byte[] sha512(final InputStream data) throws IOException {
		return digest(getSha512Digest(), data);
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest; converted to bytes using
	 *             {@link StringUtils#getBytesUtf8(String)}
	 * @return SHA-512 digest
	 * @since 1.4
	 */
	public static byte[] sha512(final String data) {
		return sha512(getBytesUtf8(data));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-512 digest as a hex string
	 * @since 1.4
	 */
	public static String sha512Hex(final byte[] data) {
		return HexUtils.bytesToHex(sha512(data));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-512 digest as a hex string
	 * @throws IOException On error reading from the stream
	 * @since 1.4
	 */
	public static String sha512Hex(final InputStream data) throws IOException {
		return HexUtils.bytesToHex(sha512(data));
	}

	/**
	 * Calculates the SHA-512 digest and returns the value as a hex string.
	 * <p>
	 * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
	 * </p>
	 *
	 * @param data Data to digest
	 * @return SHA-512 digest as a hex string
	 * @since 1.4
	 */
	public static String sha512Hex(final String data) {
		return HexUtils.bytesToHex(sha512(data));
	}

	/**
	 * Reads through an InputStream and updates the digest for the data
	 *
	 * @param digest The MessageDigest to use (e.g. MD5)
	 * @param data   Data to digest
	 * @return the digest
	 * @throws IOException On error reading from the stream
	 * @since 1.8
	 */
	private static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws IOException {
		final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
		int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

		while (read > -1) {
			digest.update(buffer, 0, read);
			read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
		}

		return digest;
	}

	private DigestUtils() {

	}
}
