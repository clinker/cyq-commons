package com.github.clinker.commons.util.id;

import java.util.UUID;

/**
 * ID = UUID，删除连字符。
 */
public class IdGeneratorByUuid implements CyqIdGenerator<String> {

	/**
	 * {@inheritDoc} ID = UUID，删除连字符。
	 *
	 * @return ID
	 */
	@Override
	public String generate() {
		return UUID.randomUUID()
				.toString()
				.replaceAll("-", "");
	}

}
