package com.github.clinker.commons.upload.name;

import com.github.clinker.commons.util.id.CyqIdGenerator;
import com.github.clinker.commons.util.id.IdGeneratorByUuid;

/**
 * 生成随机文件名，文件名全部为小写字母。文件名=随机字符串(.扩展名)
 *
 * @param <F>文件
 */
public class RandomFilenameStrategy implements FilenameStrategy {

	private ExtensionStrategy extensionStrategy = new DefaultFileExtensionStrategy();

	private CyqIdGenerator<String> idGenerator = new IdGeneratorByUuid();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String filename(final String orignialFilename) {
		// 文件后缀
		final String ext = extensionStrategy.extension(orignialFilename);

		// 文件名
		return (idGenerator.generate() + (ext == null || ext.length() == 0 ? "" : "." + ext)).toLowerCase();
	}

	public void setExtensionStrategy(final ExtensionStrategy extensionStrategy) {
		this.extensionStrategy = extensionStrategy;
	}

	public void setIdGenerator(final CyqIdGenerator<String> idGenerator) {
		this.idGenerator = idGenerator;
	}

}
