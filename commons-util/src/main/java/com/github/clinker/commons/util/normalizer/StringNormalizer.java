package com.github.clinker.commons.util.normalizer;

/**
 * 字符串规范化。
 * <p/>
 * 操作包括：null转换为空字符串，然后trim。
 */
public class StringNormalizer extends CompositeNormalizer<String> {

	public StringNormalizer() {
		add(new NullToEmptyStringNormalizer()).add(new StripNormalizer());
	}
}
