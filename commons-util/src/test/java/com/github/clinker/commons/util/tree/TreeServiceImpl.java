package com.github.clinker.commons.util.tree;

import com.github.clinker.commons.util.tree.TreeServiceImplTest.Node;

public class TreeServiceImpl implements TreeService<Node, String> {

	@Override
	public int compare(final Node o1, final Node o2) {
		return Integer.compare(o1.getDisplayOrder(), o2.getDisplayOrder());
	}

}
