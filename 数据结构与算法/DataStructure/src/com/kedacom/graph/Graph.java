package com.kedacom.graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author python
 */
public class Graph {

    /**
     * 存储顶点的集合
     */
    private final ArrayList<String> vertexList;

    /**
     * 存储邻接矩阵
     */
    private final int[][] edges;

    /**
     * 表示边的数目
     */
    private int numOfEdges;

    public static void main(String[] args) {
        // 存在5个顶点
        int n = 5;
        String[] vertexValue = {"A","B","C","D","E"};
        // 创建图对象
        Graph graph = new Graph(n);
        // 循环的添加顶点
        for (String vertex : vertexValue) {
            graph.insertVertex(vertex);
        }

        // 添加边
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        // 显示邻接举证
        graph.showGraph();

    }

    /**
     * 构造器
     *
     * @param n 顶点个数
     */
    public Graph(int n) {
        // 初始化矩阵和vertexList;
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
    }

    /**
     * 插入顶点
     *
     * @param vertex 顶点
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 插入边
     * @param v1 表示第几个顶点的下标
     * @param v2 表示连接的点的下标
     * @param weight 权值
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        // 无向图需要反向赋值
        edges[v2][v1] = weight;
        // 边++
        numOfEdges++;
    }

    /**
     * @return 返回顶点的个数
     */
    public int getNumOfVertex(){
        return vertexList.size();
    }


    /**
     * @return 返回边的个数
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }

    /**
     * @param v1 顶点的下标
     * @param v2 连线
     * @return 返回权重
     */
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    /**
     * 展示图
     */
    public void showGraph(){
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }
}
