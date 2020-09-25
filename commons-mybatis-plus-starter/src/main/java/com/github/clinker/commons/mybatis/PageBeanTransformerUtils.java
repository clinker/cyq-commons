package com.github.clinker.commons.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 除复制Bean外，没有其他操作。
 */
public final class PageBeanTransformerUtils {

	/**
	 * 转换分页。
	 *
	 * @param page   源类型的分页
	 * @param mapper Bean转换器
	 * @return 目标类型分页，不可改动
	 */
	public static <SOURCE, TARGET> IPage<TARGET> transform(final IPage<SOURCE> sources,
			final Class<TARGET> targetClass) {
		return new PageBeanTransformer<SOURCE, TARGET>() {

			@Override
			public Class<TARGET> targetClass() {
				return targetClass;
			}
		}.transform(sources);
	}

	private PageBeanTransformerUtils() {
	}

}
