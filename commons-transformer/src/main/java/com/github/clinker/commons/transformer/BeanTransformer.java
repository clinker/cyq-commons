package com.github.clinker.commons.transformer;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

/**
 * Bean之间复制属性。
 *
 * @param <SOURCE> 源
 * @param <TARGET> 目标
 */
public interface BeanTransformer<SOURCE, TARGET> {

	/**
	 * 创建target，并将source的属性赋值给target。
	 *
	 * @param source 源bean
	 * @return 目标bean
	 */
	private TARGET copyProperties(final SOURCE source) {
		final Class<TARGET> targetClass = targetClass();
		if (source == null || targetClass == null) {
			return null;
		}
		TARGET target;
		try {
			final Constructor<TARGET> constructor = targetClass.getDeclaredConstructor();
			constructor.setAccessible(true);

			target = constructor.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		BeanUtils.copyProperties(source, target);

		customCopyProperties(source, target);

		return target;
	}

	/**
	 * 在{@link #copyProperties(Object)}之后，执行额外的属性。默认为不操作。
	 *
	 * @param source 源bean
	 * @param target 目标bean
	 */
	default void customCopyProperties(final SOURCE source, final TARGET target) {
		// do nothing
	}

	/**
	 * 在{@link #transform(Collection)}之后，执行额外的属性。默认为不操作。
	 * <p/>
	 * 相比{@link #copyProperties(Object)}比，本方法适合对集合进行统一批量处理。
	 * 例如，源bean是市City，其有provinceId省ID和provinceName属性。如果是copyProperties方法里设置provinceName属性，就必须查询若干次province。
	 * 为了减少查询次数，可在本方法查询出全部province，并转换为id-province的map，在遍历cites时，设置city的provinceName属性。
	 *
	 * @param sources 源bean集合
	 * @param targets 目标bean集合
	 */
	default void customTransform(final List<SOURCE> sources, final List<TARGET> targets) {
		// do nothing
	}

	Class<TARGET> targetClass();

	/**
	 * 转换集合。
	 *
	 * @param sources 源类型的集合
	 * @param mapper  Bean转换器
	 * @return 目标类型列表
	 */
	default List<TARGET> transform(final List<SOURCE> sources) {
		if (sources == null || sources.isEmpty()) {
			return Collections.emptyList();
		}

		final List<TARGET> targets = sources.stream()
				.map(this::transform)
				.collect(Collectors.toList());

		// 执行自定义转换
		customTransform(sources, targets);

		return targets;
	}

	/**
	 * 将source转换为target。
	 *
	 * @param source 源bean
	 * @return 目标bean
	 */
	default TARGET transform(final SOURCE source) {
		return copyProperties(source);
	}

}
