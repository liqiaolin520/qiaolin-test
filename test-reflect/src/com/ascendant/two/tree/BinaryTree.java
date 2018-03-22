package com.ascendant.two.tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
	private Node root;
	private List<Node> list = new ArrayList<Node>();

	public BinaryTree() {
		init();
	}

	// ���ĳ�ʼ��:�ȴ�Ҷ�ڵ㿪ʼ,��Ҷ����
	public void init() {
		Node x = new Node("X", null, null);
		Node y = new Node("Y", null, null);
		Node d = new Node("d", x, y);
		Node e = new Node("e", null, null);
		Node f = new Node("f", null, null);
		Node c = new Node("c", e, f);
		Node b = new Node("b", d, null);
		Node a = new Node("a", b, c);
		root = a;
	}

	// ����ڵ��ࣺ
	private class Node {
		private String data;
		private Node lchid;// ����ָ����������ָ��
		private Node rchild;// ����ָ����������ָ��

		public Node(String data, Node lchild, Node rchild) {
			this.data = data;
			this.lchid = lchild;
			this.rchild = rchild;
		}
	}

	/**
	 * �Ըö���������ǰ����� ����洢��list�� ǰ�����:ABDXYCEF
	 */
	public void preOrder(Node node) {

		list.add(node); // �Ƚ����ڵ����list
		// �����������Ϊ�ռ��������ң��ڵݹ���÷�����ʱ��һֱ�Ὣ�����ĸ�����list������������ȱ������ڵ�
		if (node.lchid != null) {
			preOrder(node.lchid);
		}
		// �����ߵ���һ�㣬ֻҪ��ǰ�ڵ�������Ϊ�գ���ô�Ϳ������������ϱ�������֤�˸����ҵı���˳��
		if (node.rchild != null) {
			preOrder(node.rchild);
		}
	}

	/**
	 * �Ըö���������������� ����洢��list��
	 */
	public void inOrder(Node node) {
		if (node.lchid != null) {
			inOrder(node.lchid);
		}
		list.add(node);
		if (node.rchild != null) {
			inOrder(node.rchild);
		}
	}

	/**
	 * �Ըö��������к������ ����洢��list��
	 */
	public void postOrder(Node node) {
		if (node.lchid != null) {
			postOrder(node.lchid);
		}
		if (node.rchild != null) {
			postOrder(node.rchild);
		}
		list.add(node);

	}

	/**
	 * ���ص�ǰ������� ˵��: 
	 *  1�����һ����ֻ��һ����㣬�������Ϊ1�� 
	 *  2����������ֻ����������û������������ô���������������������ȼ�1��
	 *  3����������ֻ����������û������������ô�������Ӧ����������������ȼ�1��
	 *  4����������������������������Ǹ�������Ⱦ���������������ȵĽϴ�ֵ�ټ�1��
	 * 
	 * @return
	 */
	public int getTreeDepth(Node node) {

		if (node.lchid == null && node.rchild == null) {
			return 1;
		}
		int left = 0, right = 0;
		if (node.lchid != null) {
			left = getTreeDepth(node.lchid);
		}
		if (node.rchild != null) {
			right = getTreeDepth(node.rchild);
		}
		return left > right ? left + 1 : right + 1;
	}

	// �õ��������
	public List<Node> getResult() {
		return list;
	}

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		System.out.println("���ڵ���:" + tree.root);
		// tree.preOrder(tree.root);
//		tree.inOrder(tree.root);
		tree.postOrder(tree.root);
		for (Node node : tree.getResult()) {
			System.out.println(node.data);
		}
		System.out.println("���������" + tree.getTreeDepth(tree.root));

	}

}
