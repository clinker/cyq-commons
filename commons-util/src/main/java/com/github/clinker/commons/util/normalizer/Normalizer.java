package com.github.clinker.commons.util.normalizer;

/**
 * 数据规范化。例如对字符串进行trim。
 */
public interface Normalizer<T> {

	/**
	 * 对数据进行规范化处理。
	 *
	 * @param obj 被处理的数据
	 * @return 规范化后的数据
	 */
	T normalize(T obj);

}
