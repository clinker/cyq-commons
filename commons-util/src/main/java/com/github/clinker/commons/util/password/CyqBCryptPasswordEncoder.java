/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.clinker.commons.util.password;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import com.github.clinker.commons.util.digest.BCrypt;

/**
 * Implementation of PasswordEncoder that uses the BCrypt strong hashing
 * function. Clients can optionally supply a "strength" (a.k.a. log rounds in
 * BCrypt) and a SecureRandom instance. The larger the strength parameter the
 * more work will have to be done (exponentially) to hash the passwords. The
 * default value is 10.
 *
 * @author Dave Syer
 *
 */
public class CyqBCryptPasswordEncoder implements CyqPasswordEncoder {

	private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

	private final int strength;

	private final SecureRandom random;

	public CyqBCryptPasswordEncoder() {
		this(-1);
	}

	/**
	 * @param strength the log rounds to use
	 */
	public CyqBCryptPasswordEncoder(final int strength) {
		this(strength, null);
	}

	/**
	 * @param strength the log rounds to use
	 * @param random   the secure random instance to use
	 *
	 */
	public CyqBCryptPasswordEncoder(final int strength, final SecureRandom random) {
		this.strength = strength;
		this.random = random;
	}

	@Override
	public String encode(final CharSequence rawPassword) {
		String salt;
		if (strength > 0) {
			if (random != null) {
				salt = BCrypt.gensalt(strength, random);
			} else {
				salt = BCrypt.gensalt(strength);
			}
		} else {
			salt = BCrypt.gensalt();
		}
		return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	@Override
	public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}

		if (!BCRYPT_PATTERN.matcher(encodedPassword)
				.matches()) {
			return false;
		}

		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
	}

}