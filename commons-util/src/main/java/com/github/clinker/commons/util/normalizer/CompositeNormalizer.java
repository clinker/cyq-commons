package com.github.clinker.commons.util.normalizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 组合了多个{@link Normalizer}。下一个{@link Normalizer}的输入是上一个的输出。
 */
public class CompositeNormalizer<T> implements Normalizer<T> {

	private final List<Normalizer<T>> normalizers = new ArrayList<>();

	public CompositeNormalizer<T> add(final Normalizer<T> normalizer) {
		normalizers.add(normalizer);
		return this;
	}

	@Override
	public T normalize(final T obj) {
		T normalized = obj;
		if (normalizers != null) {
			final Iterator<Normalizer<T>> it = normalizers.iterator();
			while (it.hasNext()) {
				normalized = it.next()
						.normalize(normalized);
			}
		}
		return normalized;
	}

}
