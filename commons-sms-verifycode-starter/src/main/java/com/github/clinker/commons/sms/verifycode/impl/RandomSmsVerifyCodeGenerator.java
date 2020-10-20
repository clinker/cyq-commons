package com.github.clinker.commons.sms.verifycode.impl;

import java.security.SecureRandom;

import com.github.clinker.commons.sms.verifycode.SmsVerifyCodeGenerator;

/**
 * 随机短信验证码生成器。
 */
public class RandomSmsVerifyCodeGenerator implements SmsVerifyCodeGenerator {

	private final int length;

	private final String source;

	public RandomSmsVerifyCodeGenerator(final int length, final String source) {
		this.length = length;
		this.source = source;
	}

	@Override
	public String generate() {
		final int charsLength = source.length();
		final SecureRandom random = new SecureRandom();
		final StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(source.charAt(random.nextInt(charsLength)));
		}
		return sb.toString();
	}

}
