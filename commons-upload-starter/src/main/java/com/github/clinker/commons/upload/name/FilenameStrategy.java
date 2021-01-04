package com.github.clinker.commons.upload.name;

/**
 * 文件名生成策略。
 *
 */
public interface FilenameStrategy {

	/**
	 * 生成文件名。
	 *
	 * @param orignialFilename 原始文件名
	 * @return 新文件名
	 */
	String filename(String orignialFilename);

}
