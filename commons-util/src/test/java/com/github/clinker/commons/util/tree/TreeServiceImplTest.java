package com.github.clinker.commons.util.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TreeServiceImplTest {

	public static class Node implements TreeNode<Node, String> {

		private String id;

		private String parentId;

		private List<Node> children = new ArrayList<>();

		private int displayOrder;

		public Node() {
		}

		public Node(final String id, final String parentId, final int displayOrder) {
			this.id = id;
			this.parentId = parentId;
			this.displayOrder = displayOrder;
		}

		@Override
		public List<Node> getChildren() {
			return children;
		}

		public int getDisplayOrder() {
			return displayOrder;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getParentId() {
			return parentId;
		}

		public void setChildren(final List<Node> children) {
			this.children = children;
		}

		public void setDisplayOrder(final int displayOrder) {
			this.displayOrder = displayOrder;
		}

		public void setId(final String id) {
			this.id = id;
		}

		public void setParentId(final String parentId) {
			this.parentId = parentId;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("Node [id=");
			builder.append(id);
			builder.append(", parentId=");
			builder.append(parentId);
			builder.append(", children=");
			builder.append(children);
			builder.append(", displayOrder=");
			builder.append(displayOrder);
			builder.append("]");
			return builder.toString();
		}

	}

	@Test
	public void testBuild() throws Exception {
		final var treeService = new TreeServiceImpl();

		final List<Node> nodes = Arrays.asList(new Node("id-root-1", "", 1), new Node("id-root-0", "", 0),
				new Node("id-of-0-1", "id-root-0", 1), new Node("id-of-0-0", "id-root-0", 0));
		final var result = treeService.build(nodes);

		// 根
		assertEquals(nodes.get(0)
				.getId(),
				result.get(1)
						.getId());
		assertEquals(nodes.get(1)
				.getId(),
				result.get(0)
						.getId());

		// 子
		assertEquals(nodes.get(2)
				.getId(),
				result.get(0)
						.getChildren()
						.get(1)
						.getId());
		assertEquals(nodes.get(3)
				.getId(),
				result.get(0)
						.getChildren()
						.get(0)
						.getId());
	}

}
