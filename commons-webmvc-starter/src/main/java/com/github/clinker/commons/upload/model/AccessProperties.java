package com.github.clinker.commons.upload.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.clinker.commons.upload.directory.DirectoryStrategy;

/**
 * 访问配置。
 */
@ConfigurationProperties(prefix = "cyq.upload")
public class AccessProperties {

	/**
	 * Web访问前缀，文件的最终访问uri=uriPrefix+{@link DirectoryStrategy}+filename
	 */
	private String uriPrefix;

	public String getUriPrefix() {
		return uriPrefix;
	}

	public void setUriPrefix(final String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AccessProperties [uriPrefix=")
				.append(uriPrefix)
				.append(']');
		return builder.toString();
	}

}
