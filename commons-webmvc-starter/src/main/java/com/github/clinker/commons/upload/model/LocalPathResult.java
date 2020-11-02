package com.github.clinker.commons.upload.model;

import java.nio.file.Path;

/**
 * 写入本地文件结果。
 */
public class LocalPathResult {

	/**
	 * 原始文件名
	 */
	private String originalFilename;

	/**
	 * 不包含路径的文件名
	 */
	private String filename;

	/**
	 * 仅包含DirectoryStrategy的路径，不包含文件名，不包含本地存储路径
	 */
	private Path directory;

	/**
	 * 文件的完整路径，即：<code>本地存储路径 + directory(即DirectoryStrategy的路径) + filename</code>
	 */
	private Path path;

	public LocalPathResult() {
	}

	public LocalPathResult(final String originalFilename, final String filename, final Path directory,
			final Path path) {
		this.originalFilename = originalFilename;
		this.filename = filename;
		this.directory = directory;
		this.path = path;
	}

	public Path getDirectory() {
		return directory;
	}

	public String getFilename() {
		return filename;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public Path getPath() {
		return path;
	}

	public void setDirectory(final Path directory) {
		this.directory = directory;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public void setOriginalFilename(final String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public void setPath(final Path path) {
		this.path = path;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("LocalPathResult [originalFilename=").append(originalFilename).append(", filename=")
				.append(filename).append(", directory=").append(directory).append(", path=").append(path).append(']');
		return builder.toString();
	}

}
