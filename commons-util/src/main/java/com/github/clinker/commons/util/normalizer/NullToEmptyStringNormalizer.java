package com.github.clinker.commons.util.normalizer;

/**
 * 将<code>null</code>转换成空字符串。
 */
public class NullToEmptyStringNormalizer implements Normalizer<String> {

	@Override
	public String normalize(final String str) {
		return str == null ? "" : str;
	}

}
