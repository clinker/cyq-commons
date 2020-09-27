package com.github.clinker.commons.upload.directory;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.clinker.commons.upload.UploadProperties;

/**
 * 默认为不建立子目录，即所有文件在{@link UploadProperties}的<code>storagePath</code>下。
 */
public class DefaultDirectoryStrategy implements DirectoryStrategy {

	@Override
	public Path directory() {
		return Paths.get("");
	}

}
