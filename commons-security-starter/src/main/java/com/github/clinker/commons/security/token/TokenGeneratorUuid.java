package com.github.clinker.commons.security.token;

import java.util.UUID;

/**
 * 生成UUID。
 */
public class TokenGeneratorUuid implements TokenGenerator {

	@Override
	public String generate(Object principal) {
		return UUID.randomUUID().toString();
	}

}
