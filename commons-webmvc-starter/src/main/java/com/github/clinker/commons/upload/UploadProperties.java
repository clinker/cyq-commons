package com.github.clinker.commons.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.clinker.commons.upload.directory.DirectoryStrategy;

/**
 * 上传配置。
 */
@ConfigurationProperties(prefix = "cyq.upload")
public class UploadProperties {

	/**
	 * 是否启用上传
	 */
	private final boolean enabled = false;

	/**
	 * 存储路径，可以是相对路径或绝对路径
	 */
	private String storagePath;

	/**
	 * Web访问前缀，文件的最终访问uri=uriPrefix+{@link DirectoryStrategy}+filename
	 */
	private String uriPrefix;

	public String getStoragePath() {
		return storagePath;
	}

	public String getUriPrefix() {
		return uriPrefix;
	}

	public void setStoragePath(final String storagePath) {
		this.storagePath = storagePath;
	}

	public void setUriPrefix(final String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UploadProperties [enabled=");
		builder.append(enabled);
		builder.append(", storagePath=");
		builder.append(storagePath);
		builder.append(", uriPrefix=");
		builder.append(uriPrefix);
		builder.append("]");
		return builder.toString();
	}

}
