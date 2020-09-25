package com.github.clinker.commons.util.id;

/**
 * 生成全局唯一ID。
 */
public interface CyqIdGenerator<T> {

	/**
	 * 生成ID。
	 *
	 * @return ID
	 */
	T generate();

}
