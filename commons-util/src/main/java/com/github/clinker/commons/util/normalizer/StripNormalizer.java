package com.github.clinker.commons.util.normalizer;

/**
 * 对字符串进行strip。如果字符串为null，则返回null。
 */
public class StripNormalizer implements Normalizer<String> {

	@Override
	public String normalize(final String str) {
		return str == null ? null : str.strip();
	}

}
