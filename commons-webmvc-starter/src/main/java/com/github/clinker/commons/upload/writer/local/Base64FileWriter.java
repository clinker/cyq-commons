package com.github.clinker.commons.upload.writer.local;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.UUID;

import com.github.clinker.commons.upload.writer.AbstractFileWriter;

/**
 * 写入Base64字符串。
 */
public class Base64FileWriter extends AbstractFileWriter<String> {

	@Override
	protected void doWrite(final String base64, final Path absoluteDestination) throws Exception {
		Files.deleteIfExists(absoluteDestination);

		Files.write(absoluteDestination, Base64.getDecoder()
				.decode(base64), StandardOpenOption.CREATE_NEW);
	}

	@Override
	protected String orginalFilename(final String file) {
		return UUID.randomUUID()
				.toString();
	}

}
