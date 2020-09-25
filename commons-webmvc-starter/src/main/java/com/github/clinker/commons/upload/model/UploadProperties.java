package com.github.clinker.commons.upload.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传配置。
 */
@ConfigurationProperties(prefix = "cyq.upload")
public class UploadProperties {

	/**
	 * 存储路径，可以是相对路径或绝对路径
	 */
	private String storagePath;

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(final String storagePath) {
		this.storagePath = storagePath;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UploadProperties [storagePath=")
				.append(storagePath)
				.append(']');
		return builder.toString();
	}

}
