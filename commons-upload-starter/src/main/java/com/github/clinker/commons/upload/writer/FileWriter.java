package com.github.clinker.commons.upload.writer;

import javax.servlet.http.HttpServletRequest;

import com.github.clinker.commons.upload.model.LocalPathResult;

/**
 * 将上传的文件写入目的地。
 *
 */
public interface FileWriter<F> {

	/**
	 * 将上传的文件写入目的地。
	 *
	 * @param request 请求
	 * @param file    上传的文件
	 * @return 上传后的文件路径结果
	 */
	LocalPathResult write(HttpServletRequest request, F file);

}
