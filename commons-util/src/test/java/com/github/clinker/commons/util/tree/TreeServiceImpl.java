package com.github.clinker.commons.util.tree;

import java.util.Comparator;

import com.github.clinker.commons.util.tree.TreeServiceImplTest.Node;

public class TreeServiceImpl implements TreeService<Node, String> {

	@Override
	public Comparator<Node> comparator() {
		return Comparator.comparingInt(Node::getDisplayOrder);
	}

}
