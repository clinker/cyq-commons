package com.github.clinker.commons.upload.model;

import java.nio.file.Path;

import com.github.clinker.commons.upload.UploadProperties;

/**
 * 文件上传结果。
 */
public class UploadResult {

	/**
	 * 上传后的文件标识
	 */
	private String id;

	/**
	 * 文件相对路径，UNIX分隔符，相对于存储路径{@link UploadProperties#getStoragePath()}
	 */
	private String path;

	/**
	 * Web访问路径，例如：http://www.sample.com/a/b/abcdefg.jpg
	 */
	private String uri;

	/**
	 * 原始文件名
	 */
	private String originalFilename;

	/**
	 * 重命名后的文件名
	 */
	private String renamedFilename;

	/**
	 * 文件长度
	 */
	private long size;

	/**
	 * 文件的完整路径，即：<code>本地存储路径 + directory(即DirectoryStrategy的路径) + filename</code>
	 */
	private Path localPath;

	/**
	 *
	 */
	public UploadResult() {
	}

	public UploadResult(final String id, final String path, final String uri, final String originalFilename,
			final String renamedFilename, final long size, final Path localPath) {
		this.id = id;
		this.path = path;
		this.uri = uri;
		this.originalFilename = originalFilename;
		this.renamedFilename = renamedFilename;
		this.size = size;
		this.localPath = localPath;
	}

	public String getId() {
		return id;
	}

	public Path getLocalPath() {
		return localPath;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public String getPath() {
		return path;
	}

	public String getRenamedFilename() {
		return renamedFilename;
	}

	public long getSize() {
		return size;
	}

	public String getUri() {
		return uri;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setLocalPath(final Path localPath) {
		this.localPath = localPath;
	}

	public void setOriginalFilename(final String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setRenamedFilename(final String renamedFilename) {
		this.renamedFilename = renamedFilename;
	}

	public void setSize(final long size) {
		this.size = size;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UploadResult [id=");
		builder.append(id);
		builder.append(", path=");
		builder.append(path);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", originalFilename=");
		builder.append(originalFilename);
		builder.append(", renamedFilename=");
		builder.append(renamedFilename);
		builder.append(", size=");
		builder.append(size);
		builder.append(", localPath=");
		builder.append(localPath);
		builder.append("]");
		return builder.toString();
	}

}
