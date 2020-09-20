package com.kedacom.kruskal;


import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author python
 */
public class KruskalCase {

    /**
     * 边的个数
     */
    private int edgeNum;

    /**
     * 顶点个数
     */
    private final char[] vertex;

    /**
     * 邻接矩阵
     */
    private final int[][] matrix;

    /**
     * 最大值
     */
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 12, INF, INF, INF, 16, 24},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {16, INF, INF, INF, 8, 9, 0},
        };

        // 创建克鲁斯卡尔对象实例
        KruskalCase kruskalCase = new KruskalCase(vertex, matrix);
        // 克鲁斯卡尔算法
        kruskalCase.kruskal();
    }

    public KruskalCase(char[] vertex, int[][] matrix) {
        // 初始化定点数和边的个数
        int vLen = vertex.length;
        // 初始化顶点
        this.vertex = new char[vLen];

        // 复制拷贝的方式
        System.arraycopy(vertex, 0, this.vertex, 0, vertex.length);

        // 初始化边 使用的是复制copy的方式
        this.matrix = new int[vLen][vLen];
        IntStream.range(0, vLen).forEach(i -> System.arraycopy(matrix[i], 0, this.matrix[i], 0, vLen));

        // 统计边
        for (int i = 0; i < vLen; i++) {
            for (int j = i + 1; j < vLen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    /**
     * 打印
     */
    public void print() {
        System.out.println("邻接矩阵为: \n");
        IntStream.range(0, this.matrix.length).forEach(i -> System.out.println(Arrays.toString(matrix[i])));
    }

    /**
     * 对边进行排序 冒泡来排
     *
     * @param edges 边
     */
    private void sortEdges(EdgeData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            boolean flag = false;
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EdgeData tmp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tmp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * @param ch 顶点的值, 比如'A','B'
     * @return 返回ch顶点对应的下标, 如果找不到, 返回-1
     */
    private int getPosition(char ch) {
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == ch) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 获取下标为i的顶点的终点, 用于后面判断两个顶点的终点是否相同
     *
     * @param ends 数组时记录哥哥顶点对应的终点事哪个
     * @param i    顶点
     * @return 下标为i的顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }


    /**
     * 获取图中的边,放到EdgeData数组中
     *
     * @return EdgeData[]
     */
    private EdgeData[] getEdges() {
        int index = 0;
        EdgeData[] edgeData = new EdgeData[edgeNum];
        // 遍历邻接矩阵
        for (int i = 0; i < vertex.length; i++) {
            for (int j = i + 1; j < vertex.length; j++) {
                if (matrix[i][j] != INF) {
                    edgeData[index++] = new EdgeData(vertex[i], vertex[j], matrix[i][j]);
                }
            }
        }
        return edgeData;
    }

    /**
     * 克鲁斯卡尔算法
     */
    public void kruskal() {
        // 表示最后结果数组索引
        int index = 0;
        // 用于保存已有最小生成树中的每个顶点在最小生成数中的终点
        int[] ends = new int[edgeNum];

        // 创建结果数组,保存最后的最小生成树
        EdgeData[] results = new EdgeData[edgeNum];

        // 获取途中所有的边的合集, 一共有12边
        EdgeData[] edges = getEdges();

        // 按照边的权值大小进行排序
        sortEdges(edges);

        // 遍历edges数组, 将边添加到最小生成树中,判断是准备加入的边是否形成了回路, 如果没有,就加入results,否则不能加入
        for (int i = 0; i < edgeNum; i++) {
            // 获取到第i条边的第一个顶点(起点)
            int p1 = getPosition(edges[i].start);
            // 获取到第i天边的第二个顶点
            int p2 = getPosition(edges[i].end);
            // 获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends, p1);
            // 获取p2这个顶点在已有最小生成树的终点
            int n = getEnd(ends, p2);
            // 是否回路
            if (m != n) {
                // 不构成回路
                // 设置m在已有最小生成树的终点
                ends[m] = n;
                // 有一条边加入到result去
                results[index++] = edges[i];
            }
        }
        // 答应生成最小生成树
        this.print();
        // 打一个 results
        for (int i = 0; i < index; i++) {
            System.out.println(results[i]);
        }
    }
}


/**
 * 边的数据
 */
class EdgeData {

    /**
     * 起始位置
     */
    char start;

    /**
     * 终止位置
     */
    char end;

    /**
     * 权值
     */
    int weight;

    public EdgeData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}