package com.github.clinker.commons.util.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形数据结构操作。
 *
 * @param <T>
 * @param <ID>
 */
public interface TreeService<T extends TreeNode<T, ID>, ID> {

	/**
	 * 一组树节点转换为层级结构。
	 *
	 * @param nodes 树节点列表
	 * @return 有层级结构的树节点列表
	 */
	default List<T> build(final Collection<T> nodes) {
		if (nodes == null || nodes.isEmpty()) {
			return Collections.emptyList();
		}

		final List<T> tree = new ArrayList<>();
		// 加入跟节点
		final List<T> roots = findAndSortRootNodes(nodes);
		tree.addAll(roots);

		// 深度遍历根节点的子节点
		roots.forEach(root -> buildChildNodes(nodes.stream()
				.collect(Collectors.groupingBy(T::getParentId)), root));

		return tree;
	}

	/**
	 * 构建子节点。
	 *
	 * @param groupedByParent
	 * @param parent
	 */
	default void buildChildNodes(final Map<ID, List<T>> groupedByParent, final T parent) {
		final List<T> children = groupedByParent.get(parent.getId());
		if (children == null || children.isEmpty()) {
			return;
		}
		// 排序
		Collections.sort(children, this::compare);

		parent.getChildren()
				.addAll(children);

		children.forEach(root -> buildChildNodes(groupedByParent, root));
	}

	/**
	 * 排序。
	 *
	 * @return 排序
	 */
	int compare(T o1, T o2);

	default List<T> findAndSortRootNodes(final Collection<T> nodes) {
		return nodes.stream()
				.filter(this::isRoot)
				.sorted(this::compare)
				.collect(Collectors.toList());
	}

	/**
	 * 当parent id为null或空白字符串时，该节点为根节点。
	 *
	 * @param node
	 * @return 是否为根节点
	 */
	default boolean isRoot(final T node) {
		if (node.getParentId() == null) {
			return true;
		}

		if (node.getParentId() instanceof String) {
			return ((String) node.getParentId()).isEmpty();
		}

		return false;
	}

}
