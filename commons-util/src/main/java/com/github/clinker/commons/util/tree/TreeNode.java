package com.github.clinker.commons.util.tree;

import java.util.List;

/**
 * 树形节点。 有至少一个根节点。
 */
public interface TreeNode<T extends TreeNode<T, ID>, ID> extends Comparable<T> {

	/**
	 * 获取子列表。
	 *
	 * @return 子列表
	 */
	List<T> getChildren();

	/**
	 * 获取ID。
	 *
	 * @return ID
	 */
	ID getId();

	/**
	 * 获取上级ID。
	 *
	 * @return 上级ID
	 */
	ID getParentId();

}
