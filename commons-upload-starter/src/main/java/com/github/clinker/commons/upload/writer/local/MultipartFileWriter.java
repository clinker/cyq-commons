package com.github.clinker.commons.upload.writer.local;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.github.clinker.commons.upload.writer.AbstractFileWriter;

/**
 * MultipartFile文件写入。
 */
public class MultipartFileWriter extends AbstractFileWriter<MultipartFile> {

	@Override
	protected void doWrite(final MultipartFile file, final Path absoluteDestination) throws Exception {
		file.transferTo(absoluteDestination.toFile());
	}

	@Override
	protected String orginalFilename(final MultipartFile file) {
		return file.getOriginalFilename();
	}

}
