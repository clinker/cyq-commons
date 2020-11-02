package com.github.clinker.commons.upload.name;

/**
 * 文件扩展名生成策略。
 *
 * @param <F>文件
 */
public interface ExtensionStrategy {

	/**
	 * 获取文件的扩展名。
	 *
	 * @param orignialFilename 原始文件名
	 * @return 扩展名
	 */
	String extension(String orignialFilename);

}
