package com.github.clinker.commons.http;

import java.time.Duration;

/**
 * 超时。
 */
public class HttpTimeout {

	/**
	 * 如果为null，则忽略
	 */
	private Duration connect;

	/**
	 * 如果为null，则忽略
	 */
	private Duration write;

	/**
	 * 如果为null，则忽略
	 */
	private Duration read;

	public Duration getConnect() {
		return connect;
	}

	public Duration getRead() {
		return read;
	}

	public Duration getWrite() {
		return write;
	}

	public void setConnect(final Duration connect) {
		this.connect = connect;
	}

	public void setRead(final Duration read) {
		this.read = read;
	}

	public void setWrite(final Duration write) {
		this.write = write;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HttpTimeout [connect=");
		builder.append(connect);
		builder.append(", write=");
		builder.append(write);
		builder.append(", read=");
		builder.append(read);
		builder.append("]");
		return builder.toString();
	}

}
