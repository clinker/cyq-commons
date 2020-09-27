package com.github.clinker.commons.upload.directory;

import java.nio.file.Path;

import com.github.clinker.commons.upload.UploadProperties;

/**
 * 文件保存路径策略，相对于{@link UploadProperties}的<code>storagePath</code>。
 */
public interface DirectoryStrategy {

	/**
	 * 所有上传的文件都放在本目录下。
	 *
	 * @return 存储文件的目录
	 */
	Path directory();

}
