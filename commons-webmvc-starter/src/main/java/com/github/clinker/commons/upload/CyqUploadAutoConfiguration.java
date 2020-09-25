package com.github.clinker.commons.upload;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.clinker.commons.upload.directory.DirectoryStrategy;
import com.github.clinker.commons.upload.model.AccessProperties;
import com.github.clinker.commons.upload.model.UploadProperties;
import com.github.clinker.commons.upload.name.DefaultFileExtensionStrategy;
import com.github.clinker.commons.upload.name.ExtensionStrategy;
import com.github.clinker.commons.upload.name.FilenameStrategy;
import com.github.clinker.commons.upload.name.RandomFilenameStrategy;
import com.github.clinker.commons.upload.service.impl.MultipartFileUploadService;
import com.github.clinker.commons.upload.writer.local.MultipartFileWriter;
import com.github.clinker.commons.util.id.CyqIdGenerator;
import com.github.clinker.commons.util.id.IdGeneratorByUuid;

/**
 * 文件上传自动配置。
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(MultipartFileWriter.class)
@EnableConfigurationProperties({ AccessProperties.class, UploadProperties.class })
@ConditionalOnProperty(prefix = "cyq.upload", name = "enabled", havingValue = "true", matchIfMissing = false)
public class CyqUploadAutoConfiguration {

	@Autowired
	private UploadProperties uploadProperties;

	/**
	 * 按年月分目录。
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DirectoryStrategy directoryStrategy() {
		final LocalDateTime now = LocalDateTime.now();
		return () -> Paths.get(String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()));
	}

	@Bean
	@ConditionalOnMissingBean
	public ExtensionStrategy extensionStrategy() {
		return new DefaultFileExtensionStrategy();
	}

	@Bean
	@ConditionalOnMissingBean
	public FilenameStrategy filenameStrategy() {
		final RandomFilenameStrategy strategy = new RandomFilenameStrategy();

		strategy.setExtensionStrategy(extensionStrategy());
		strategy.setIdGenerator(idGenerator());

		return strategy;
	}

	@Bean
	@ConditionalOnMissingBean
	public MultipartFileWriter fileWriter() {
		final MultipartFileWriter writer = new MultipartFileWriter();

		writer.setDirectoryStrategy(directoryStrategy());
		writer.setFilenameStrategy(filenameStrategy());
		writer.setUploadProperties(uploadProperties);

		return writer;
	}

	@Bean
	@ConditionalOnMissingBean
	public CyqIdGenerator<String> idGenerator() {
		return new IdGeneratorByUuid();
	}

	@Bean
	@ConditionalOnMissingBean
	public MultipartFileUploadService uploadService() {
		final MultipartFileUploadService service = new MultipartFileUploadService();

		service.setFileWriter(fileWriter());
		return service;
	}
}
