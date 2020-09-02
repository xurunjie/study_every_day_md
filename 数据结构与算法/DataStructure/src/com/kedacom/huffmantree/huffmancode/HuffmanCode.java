package com.kedacom.huffmantree.huffmancode;

import java.util.*;

/**
 * 功能: 根据赫夫曼编码压缩数据的原理,需要创建" I like like like java do you like a java " 对应的赫夫曼树
 * 思路:
 * 1. Node { data -> 存放数据, weight -> 权值, left 和 right }
 * 2. 得到"I like like like java do you like a java"对应的byte[]数组
 * 3. 编写一个方法,将准备构建的赫夫曼树的Node节点放到List, 形式[Node{data='7', weight=5 }] ...
 * 4. 可以通过List创建对应的赫夫曼树
 *
 * @author python
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String str = "i like like like java do you like a java";
        byte[] contentBytes = str.getBytes();
        System.out.println("contentBytes.length = " + contentBytes.length);
        List<Node> nodes = getNodes(contentBytes);
        System.out.println("nodes = " + nodes);
        // 创建二叉树
        Node node = createHuffmanTree(nodes);

        node.preOrder();
    }

    /**
     * 从赫夫曼树生成赫夫曼编码
     * 思路
     * 1. 将赫夫曼编码表存放在Map<Byte,String> 形式
     * 32 -> 01 97 -> 100 100-> 11000 等等[形式]
     */
    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();

    /**
     * 在生成赫夫曼编码表示,需要去
     */
    static StringBuilder stringBuilder = new StringBuilder();


    /**
     * 前序遍历
     */
    private static void preOrder(Node root){
        if (root != null) {
            root.preOrder();
        }else {
            System.out.println("根节点为空~~~");
        }
    }

    /**
     * @param bytes 接收一个字节数组
     * @return 返回的是List形式的 [Node ...]
     */
    private static List<Node> getNodes(byte[] bytes) {
        // 1. 先创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<Node>();
        // 2. 遍历bytes,统计每一个byte出现的次数 -> map[key,value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            counts.merge(b, 1, Integer::sum);
        }
        // 把每个键值对转成一个Node对象并加入到nodes集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 从小到大排序
            Collections.sort(nodes);
            // 取出第一颗最小的二叉树
            Node left = nodes.get(0);
            // 去除第二颗最小二叉树
            Node right = nodes.get(1);
            // 创建一颗新的二叉树,他的根节点没有data,没有权值
            Node parent = new Node(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            nodes.remove(left);
            nodes.remove(right);
            // 将新的二叉树加入到nodes
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}


class Node implements Comparable<Node> {
    Byte data;
    int weight;
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排列
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
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