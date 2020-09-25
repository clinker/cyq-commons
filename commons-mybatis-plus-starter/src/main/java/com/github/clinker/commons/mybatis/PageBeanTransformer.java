package com.github.clinker.commons.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.clinker.commons.transformer.BeanTransformer;

/**
 * MyBatis Plus分页转换。
 *
 * @param <SOURCE> 源
 * @param <TARGET> 目标
 */
public interface PageBeanTransformer<SOURCE, TARGET> extends BeanTransformer<SOURCE, TARGET> {

	default IPage<TARGET> transform(final IPage<SOURCE> page) {
		final IPage<TARGET> result = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<TARGET>();
		result.setCurrent(page.getCurrent());
		result.setPages(page.getPages());
		result.setRecords(transform(page.getRecords()));
		result.setSize(page.getSize());
		result.setTotal(page.getTotal());

		return result;
	}
}
