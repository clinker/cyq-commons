package com.github.clinker.commons.upload.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.github.clinker.commons.upload.UploadProperties;
import com.github.clinker.commons.upload.WebPathUtils;
import com.github.clinker.commons.upload.model.LocalPathResult;
import com.github.clinker.commons.upload.model.UploadResult;
import com.github.clinker.commons.upload.service.UploadService;
import com.github.clinker.commons.upload.writer.local.MultipartFileWriter;

/**
 * 上传Spring的{@link MultipartFile}。
 */
public class MultipartFileUploadService implements UploadService {

	private static final Logger log = LoggerFactory.getLogger(MultipartFileUploadService.class);

	private MultipartFileWriter fileWriter;

	public void setFileWriter(final MultipartFileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	@Override
	public UploadResult upload(final HttpServletRequest request, final MultipartFile file,
			final UploadProperties uploadProperties) {
		log.trace("Uploading file: {}, {} bytes", file.getOriginalFilename(), file.getSize());

		final long size = file.getSize();

		final LocalPathResult pathResult = fileWriter.write(request, file);

		log.trace("Local path result: {}", pathResult);

		final String path = FilenameUtils
				.separatorsToUnix(pathResult.getDirectory().resolve(pathResult.getFilename()).toString());

		return new UploadResult(pathResult.getOriginalFilename(),
				path,
				WebPathUtils.getFileUri(request, uploadProperties.getUriPrefix(), path),
				file.getOriginalFilename(),
				pathResult.getFilename(),
				size,
				pathResult.getPath());
	}

}
