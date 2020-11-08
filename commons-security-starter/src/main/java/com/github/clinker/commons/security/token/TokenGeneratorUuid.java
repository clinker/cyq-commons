package com.github.clinker.commons.security.token;

import java.util.UUID;

/**
 * 生成UUID。
 */
public class TokenGeneratorUuid implements TokenGenerator {

	@Override
	public String generate(final String accountId) {
		return UUID.randomUUID()
				.toString();
	}

}
