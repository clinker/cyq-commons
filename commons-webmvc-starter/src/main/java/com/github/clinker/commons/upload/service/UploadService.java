package com.github.clinker.commons.upload.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.clinker.commons.upload.UploadProperties;
import com.github.clinker.commons.upload.model.UploadResult;

/**
 * 文件上传业务。
 */
public interface UploadService {

	/**
	 * 上传文件。
	 *
	 * @param request          请求
	 * @param file             被上传的文件
	 * @param uploadProperties 上传配置
	 * @return 上传结果
	 */
	UploadResult upload(HttpServletRequest request, MultipartFile file, UploadProperties uploadProperties);
}
