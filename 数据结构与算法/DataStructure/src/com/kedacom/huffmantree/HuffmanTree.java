package com.kedacom.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author python
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node node = createHuffmanTree(arr);
        preOrder(node);
    }

    public static void preOrder(Node node) {
        if (node != null) {
            node.preOrder();
        }else {
            System.out.println("该赫夫曼树是空树");
        }
    }

    public static Node createHuffmanTree(int[] arr) {
        // 1. 遍历arr数组
        // 2. 将arr的每个元素构成一个node
        // 3. 将node放入到ArrayList中
        List<Node> nodes = new ArrayList<Node>();
        for (int j : arr) {
            nodes.add(new Node(j));
        }
        // 排序

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 去除根节点权值最小的两颗二叉树
            Node leftNode = nodes.get(0);
            // 取出权值第二小的节点二叉树
            Node rightNode = nodes.get(1);

            // 构成一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            // 从arrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 将parents加入到nodes
            nodes.add(parent);
//            System.out.println(nodes);
        }
        // 返回赫夫曼树的root节点
        return nodes.get(0);
    }
}

class Node implements Comparable<Node> {
    int value;
    /**
     * 左子节点
     */
    Node left;
    /**
     * 右子节点
     */
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}