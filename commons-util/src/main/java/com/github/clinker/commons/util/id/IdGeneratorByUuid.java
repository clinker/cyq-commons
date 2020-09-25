package com.github.clinker.commons.util.id;

import java.util.UUID;

/**
 * ID = UUID.
 */
public class IdGeneratorByUuid implements CyqIdGenerator<String> {

	/**
	 * {@inheritDoc} ID = UUID.
	 *
	 * @return ID
	 */
	@Override
	public String generate() {
		return UUID.randomUUID()
				.toString();
	}
}
