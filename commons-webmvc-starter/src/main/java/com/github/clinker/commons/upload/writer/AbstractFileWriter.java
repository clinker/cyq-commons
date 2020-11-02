package com.github.clinker.commons.upload.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.clinker.commons.upload.UploadProperties;
import com.github.clinker.commons.upload.directory.DefaultDirectoryStrategy;
import com.github.clinker.commons.upload.directory.DirectoryStrategy;
import com.github.clinker.commons.upload.model.FileUploadError;
import com.github.clinker.commons.upload.model.LocalPathResult;
import com.github.clinker.commons.upload.name.FilenameStrategy;
import com.github.clinker.commons.upload.name.RandomFilenameStrategy;
import com.github.clinker.commons.util.exception.ServiceException;

/**
 * 文件写入过程中，创建必需的目录、生成文件名。
 *
 * @param <F> 文件
 */
public abstract class AbstractFileWriter<F> implements FileWriter<F> {

	private static final Logger log = LoggerFactory.getLogger(AbstractFileWriter.class);

	private DirectoryStrategy directoryStrategy = new DefaultDirectoryStrategy();

	private FilenameStrategy filenameStrategy = new RandomFilenameStrategy();

	private UploadProperties uploadProperties;

	/**
	 * 执行写入。
	 *
	 * @param file                待写入的文件
	 * @param absoluteDestination 生成的文件的绝对路径
	 * @throws Exception
	 */
	protected abstract void doWrite(F file, Path absoluteDestination) throws Exception;

	/**
	 * 获取原始文件名。
	 *
	 * @param file 文件
	 * @return 原始文件名
	 */
	protected abstract String orginalFilename(F file);

	/**
	 * 创建必需的目录。
	 *
	 * @param directory 生成的文件的所在目录
	 */
	private void prepareDirectories(final Path directory) {
		if (!Files.exists(directory)) {
			log.debug("Path not exists. Create: {}", directory);
			try {
				Files.createDirectories(directory);
			} catch (final IOException e) {
				log.error("Directories creation error", e);
				throw new ServiceException(FileUploadError.CREATE_DIRECTORY);
			}
		}
	}

	public void setDirectoryStrategy(final DirectoryStrategy directoryStrategy) {
		this.directoryStrategy = directoryStrategy;
	}

	public void setFilenameStrategy(final FilenameStrategy filenameStrategy) {
		this.filenameStrategy = filenameStrategy;
	}

	public void setUploadProperties(UploadProperties uploadProperties) {
		this.uploadProperties = uploadProperties;
	}

	@Override
	public LocalPathResult write(final HttpServletRequest request, final F file) {
		final String orginalFilename = orginalFilename(file);
		// 重命名后的文件名
		final String filename = filenameStrategy.filename(orginalFilename);

		// 待写入的文件的目的地目录
		Path fullDirectory = Paths.get(uploadProperties.getStoragePath());
		if (!fullDirectory.isAbsolute()) {
			// 如果不是绝对路径，则相对于real path
			fullDirectory = Paths.get(request.getServletContext()
					.getRealPath(""))
					.resolve(uploadProperties.getStoragePath());
		}
		final Path directory = directoryStrategy.directory();
		if (directory != null) {
			fullDirectory = fullDirectory.resolve(directory);
		}
		prepareDirectories(fullDirectory);

		final Path fullPath = fullDirectory.resolve(filename);
		log.trace("Write to path: {}", fullPath);
		try {
			doWrite(file, fullPath);
		} catch (final Exception e) {
			log.error("Upload file error", e);
			throw new ServiceException(FileUploadError.FILE_UPLOAD);
		}

		return new LocalPathResult(orginalFilename, filename, directory, fullPath);
	}

}
