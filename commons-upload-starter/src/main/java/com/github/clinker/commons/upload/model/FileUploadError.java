package com.github.clinker.commons.upload.model;

import com.github.clinker.commons.util.exception.ServiceError;

/**
 * 文件上传错误。
 */
public enum FileUploadError implements ServiceError {

	/**
	 * 创建目录失败
	 */
	CREATE_DIRECTORY,

	/**
	 * 不明确的错误
	 */
	FILE_UPLOAD,

	/**
	 * 无效的路径
	 */
	PATH_INVALID
}
