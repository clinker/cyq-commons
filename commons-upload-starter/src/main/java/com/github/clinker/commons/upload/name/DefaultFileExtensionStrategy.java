package com.github.clinker.commons.upload.name;

import org.apache.commons.io.FilenameUtils;

/**
 * 默认文件扩展名策略。
 */
public class DefaultFileExtensionStrategy implements ExtensionStrategy {

	@Override
	public String extension(final String orignialFilename) {
		final String ext = FilenameUtils.getExtension(orignialFilename);

		return ext == null ? "" : ext;
	}

}
