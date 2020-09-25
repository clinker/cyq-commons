package com.github.clinker.commons.transformer;

import java.util.List;

/**
 * 除复制Bean外，没有其他操作。
 */
public final class BeanTransformerUtils {

	/**
	 * 转换集合。
	 *
	 * @param sources     源类型的集合
	 * @param targetClass 目标类
	 * @return 目标类型列表，不可改动
	 */
	public static <SOURCE, TARGET> List<TARGET> transform(final List<SOURCE> sources, final Class<TARGET> targetClass) {
		return new BeanTransformer<SOURCE, TARGET>() {

			@Override
			public Class<TARGET> targetClass() {
				return targetClass;
			}
		}.transform(sources);
	}

	/**
	 * 转换对象。
	 *
	 * @param sources     源类型对象
	 * @param targetClass 目标类
	 * @return 目标类型对象，不可改动
	 */
	public static <SOURCE, TARGET> TARGET transform(final SOURCE source, final Class<TARGET> targetClass) {
		return new BeanTransformer<SOURCE, TARGET>() {

			@Override
			public Class<TARGET> targetClass() {
				return targetClass;
			}
		}.transform(source);
	}

	private BeanTransformerUtils() {
	}

}
