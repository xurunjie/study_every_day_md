package com.kedacom.huffmantree.huffmancode;

import java.io.*;
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
//        String str = "i like like like java do you like a java";
//        byte[] contentBytes = str.getBytes();
//        byte[] huffmanCodeBytes = huffmanZip(contentBytes);
//        System.out.println("huffmanCodeBytes = " + Arrays.toString(huffmanCodeBytes));
//        System.out.println("huffmanCodeBytes.length = " + huffmanCodeBytes.length);
//        byte[] bytes = decode(huffmanCodes, huffmanCodeBytes);
//        System.out.println("bytes = " + new String(bytes));
//        System.out.println("contentBytes.length = " + contentBytes.length);
//        List<Node> nodes = getNodes(contentBytes);
//        System.out.println("nodes = " + nodes);
//        // 创建二叉树
//        Node node = createHuffmanTree(nodes);
//        // 前序遍历
//        preOrder(node);
//        // 获取压缩的数值
//        Map<Byte, String> huffmanCodes = getCodes(node);
//        System.out.println("生成的 huffman 编码表是\n" + huffmanCodes);
//
//        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
//        System.out.println("huffmanCodeBytes = " + Arrays.toString(huffmanCodeBytes));

//        String srcPath = "/Users/xurunjie/Desktop/sad.png";
//        String dstPath = "/Users/xurunjie/Desktop/dst.zip";
//        zipFile(srcPath,dstPath);
//        System.out.println("压缩结束");

        String dstPath = "/Users/xurunjie/Desktop/1234.png";
        String srcPath = "/Users/xurunjie/Desktop/dst.zip";
        unzipFile(srcPath, dstPath);
        System.out.println("解压成功");

    }
    public static void unzipFile(String zipFile,String dstFile) {
        // 定义文件输入流
        InputStream is = null;
        // 定义对象的输入流
        ObjectInputStream ois = null;
        // 定义文件输出流
        OutputStream os = null;

        try {
            is = new FileInputStream(zipFile);
            ois = new ObjectInputStream(is);
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte,String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            os = new FileOutputStream(dstFile);
            // 数据写出
            os.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert os != null;
                os.close();
                ois.close();
                is.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * 编写方法,将一个文件进行存储
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {
        // 创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        // 创建输入流
        FileInputStream is = null;
        try {
            is = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的 byte[]
            // available 返回文件大小
            byte[] b = new byte[is.available()];
            // 读取文件
            is.read(b);
            // 对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            // 创建输出流
            os = new FileOutputStream(dstFile);
            // 创建一个和文件输出流相关联的 ObjectOutputStream
            oos = new ObjectOutputStream(os);
            // 先把 huffmanBytes 写进去
            oos.writeObject(huffmanBytes);
            // 这里我们以对象流的方式写入赫夫曼编码,是为了以后我们恢复时使用
            oos.writeObject(huffmanCodes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                assert is != null;
                is.close();
                assert os != null;
                os.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 完成赫夫曼编码表 map
     * @param huffmanCodes 生成的赫夫曼编码 map
     * @param huffmanBytes 赫夫曼编码的到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        // 显得到 huffmanBytes 对应的二进制的字符串, 形式 1010100010111...
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < huffmanBytes.length; i++) {
            boolean flag = (i == huffmanBytes.length - 1);
            builder.append(byteToBitString(!flag, huffmanBytes[i]));
        }
        // 把字符串按照指定的赫夫曼编码进行解码
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> stringByteEntry : huffmanCodes.entrySet()) {
            map.put(stringByteEntry.getValue(), stringByteEntry.getKey());
        }

        // 创建一个集合存放 byte
        ArrayList<Byte> byteArrayList = new ArrayList<>();
        for (int i = 0; i < builder.length();) {
            int count = 1;
            boolean flag = true;
            Byte bit = null;
            while (flag) {
                // 取出一个'1' '0'
                // i 不动 让 count 移动,指定匹配到一个字符
                String key = builder.substring(i, i + count);
                bit = map.get(key);
                if (bit == null) {
                    count++;
                }else {
                    flag = false;
                }
            }
            byteArrayList.add(bit);
            i += count;
        }
        byte[] b = new byte[byteArrayList.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = byteArrayList.get(i);
        }
        return b;
    }

    /**
     * 完成数据的解压
     * 思路
     * 1. 将huffmanCodeBytes -> [ -88
     *
     * @param b 二进
     * @param flag 标志是否需要补高位,如果是 true,表示需要补高位,如果是 false 表示不补, 如果是最后一个字节,无需补高位
     * @return string 是该 b 的二进制字符串
     */
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        // 如果是证书我们还存在补高位
        if (flag){
            // 按位与
            temp |= 256;
        }
        // 返回对应的是二进制对应的补码
        String str = Integer.toBinaryString(temp);
        if (flag) {
            return str.substring(str.length() - 8);
        }else {
            return str;
        }
    }


    /**
     * @param bytes 原始的在字符串对应的字节数组
     * @return 经过赫夫曼处理后的字节数组 (压缩后的数组)
     */
    private static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        // 创建 huffman 编码
        Node huffmanTree = createHuffmanTree(nodes);
        // 生成 huffman 编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        // 根据生成的赫夫曼编码压缩,得到压缩后的赫夫曼编码字节数组

        return zip(bytes, huffmanCodes);
    }

    /**
     * 编写一个方法,将字符串对应的 byte[], 通过生成的赫夫曼编码表,返回一个赫夫曼编码 压缩后的 byte[]
     *
     * @param bytes        这是原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码 map
     * @return 返回赫夫曼编码处理后的 byte[]
     * 举例: string content = "i like like like java do you like a java";=> byte[] contentBytes = content.getBytes()
     * 返回的是 字符内穿 "1010100... ... ... ..."
     * 对应的 Byte[] huffmanCodeBytes , 即 8 位对应一个 byte,放入到 huffmanCodeBytes
     * huffmanCodeBytes[0] = 10101000(补码) => byte [推导 10101000 => 1010100 - 1 => 10100111(反码) => 11011000 ]
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 1. 利用 huffmanCodeBytes 将 bytes 转成赫夫曼编码对应的字符串
        StringBuilder builder = new StringBuilder();
        // 遍历 bytes 数组
        for (byte b : bytes) {
            builder.append(huffmanCodes.get(b));
        }
        // 获取数组长度
        int len = (builder.length() + 7) / 8;
        // 创建 存储压缩后的 byte 数组
        byte[] huffmanCodeBytes = new byte[len];

        int index = 0;
        // 每 8 位对应一个 byte 字节
        for (int i = 0; i < builder.length(); i += 8) {
            String strByte;
            if (i + 8 > builder.length()) {
                // 条件成立 不够 8 位
                strByte = builder.substring(i);
            } else {
                strByte = builder.substring(i, i + 8);
            }
            // 将 strByte 转成一个 byte 数,放入到
            huffmanCodeBytes[index++] = (byte) Integer.parseInt(strByte, 2);

        }
        return huffmanCodeBytes;
    }

    /**
     * 从赫夫曼树生成赫夫曼编码
     * 思路
     * 1. 将赫夫曼编码表存放在Map<Byte,String> 形式
     * 32 -> 01 97 -> 100 100-> 11000 等等[形式]
     */
    static Map<Byte, String> huffmanCodes = new HashMap<>();

    /**
     * 在生成赫夫曼编码表示,需要去
     */
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * @param node 赫夫曼树的根节点
     * @return huffman-code 赫夫曼编码表
     */
    private static Map<Byte, String> getCodes(Node node) {
        if (node == null) {
            return null;
        }
        // 处理左子树
        getCodes(node.left, "0", stringBuilder);
        // 处理右子树
        getCodes(node.right, "1", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 功能：将传入的node节点的所有节点哈夫曼编码的到，并放入huffmanCodes集合中
     *
     * @param node          传入节点
     * @param code          路径： 左子节点是0 右子节点是 1
     * @param stringBuilder 是用于拼接路径的
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        // 将 code 加入 builder
        builder.append(code);
        if (node != null) {
            if (node.data == null) {
                getCodes(node.left, "0", builder);
                getCodes(node.right, "1", builder);
            } else {
                // 说明找到叶子节点的最后
                huffmanCodes.put(node.data, builder.toString());
            }
        }

    }


    /**
     * 前序遍历
     */
    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("根节点为空~~~");
        }
    }

    /**
     * @param bytes 接收一个字节数组
     * @return 返回的是List形式的 [Node ...]
     */
    private static List<Node> getNodes(byte[] bytes) {
        // 1. 先创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
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