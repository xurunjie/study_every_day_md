package com.kedacom.prim;

import java.util.Arrays;

/**
 * @author python
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        // 测试看看图是否创建ok
        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertex = data.length;
        // 邻接举证的关系使用二维数组表示
        int[][] weight = {
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 2},
                {2, 3, 10000, 10000, 4, 6, 10000},
        };
        // 创建图
        Graph graph = new Graph(vertex);

        // 最小生成树
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, vertex, data, weight);
        minTree.showGraph(graph);

        // 测试 普里姆算法
        minTree.prim(graph,0);
    }
}

/**
 * 创建最小生成树 -> 村庄的图
 */
class MinTree {

    /**
     * 创建图的邻接举证
     *
     * @param graph  图对象
     * @param vertex 图对应的顶点的个数
     * @param data   图各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(Graph graph, int vertex, char[] data, int[][] weight) {
        int i, j;
        for (i = 0; i < vertex; i++) {
            // 顶点
            graph.data[i] = data[i];
            for (j = 0; j < vertex; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }


    /**
     * 邻接对象
     *
     * @param graph 图对象
     */
    public void showGraph(Graph graph) {
        for (int[] link : graph.weight) {
            System.out.println("link = " + Arrays.toString(link));
        }
    }


    /**
     * 编写prim 算法 得到最小生成树
     *
     * @param graph 图
     * @param v     表示他从图的第几个顶点开始生成 'A' -> 0 'B' -> 1 ...
     */
    public void prim(Graph graph, int v) {
        // 标记节点是否被访问过
        int[] visited = new int[graph.vertex];

        // 当前节点标记位已访问
        visited[v] = 1;
        // h1 h2 标记两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        // 将minWeight 初始化为一个大数,后面在遍历过程中,会被替换
        int minWeight = 10000;

        for (int k = 1; k < graph.vertex; k++) {
            // 这个是确定每一次生成的子图,和那个节点的距离最近
            for (int i = 0; i < graph.vertex; i++) {
                // i 表示访问过的节点
                for (int j = 0; j < graph.vertex; j++) {
                    // j 表示未访问过的节点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        // 替换minWeight(寻找已经访问过的节点和为访问过的节点间的权值,最小的边)
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            System.out.println("边 < " + graph.data[h1] + "," + graph.data[h2] + " > 权值: " + minWeight);
            // 将当前这个节点标记已经访问
            visited[h2] = 1;
            // minWeight 重新设置为最大值 10000
            minWeight = 10000;
        }

    }
}


/**
 * 图
 */
class Graph {

    /**
     * 表示图节点的个数
     */
    int vertex;

    /**
     * 存放节点的数据
     */
    char[] data;

    /**
     * 存放边, 就是我们的邻接矩阵
     */
    int[][] weight;

    public Graph(int vertex) {
        this.vertex = vertex;
        this.data = new char[vertex];
        this.weight = new int[vertex][vertex];
    }
}