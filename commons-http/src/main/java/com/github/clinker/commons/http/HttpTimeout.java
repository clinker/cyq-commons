package com.github.clinker.commons.http;

/**
 * 超时。单位：秒
 */
public class HttpTimeout {

	/**
	 * 如果<=0，则忽略
	 */
	private long connect;

	/**
	 * 如果<=0，则忽略
	 */
	private long write;

	/**
	 * 如果<=0，则忽略
	 */
	private long read;

	public long getConnect() {
		return connect;
	}

	public long getRead() {
		return read;
	}

	public long getWrite() {
		return write;
	}

	public void setConnect(final long connect) {
		this.connect = connect;
	}

	public void setRead(final long read) {
		this.read = read;
	}

	public void setWrite(final long write) {
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
