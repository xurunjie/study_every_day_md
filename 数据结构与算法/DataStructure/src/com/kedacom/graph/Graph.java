package com.kedacom.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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

    /**
     * 定义数组标注是否被访问
     */
    private boolean[] isVisited;

    public static void main(String[] args) {
        // 存在5个顶点
        int n = 8;
//        String[] vertexValue = {"A", "B", "C", "D", "E"};
        String[] vertexValue = {"1", "2", "3", "4", "5", "6", "7", "8"};
        // 创建图对象
        Graph graph = new Graph(n);
        // 循环的添加顶点
        for (String vertex : vertexValue) {
            graph.insertVertex(vertex);
        }

        // 添加边\
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        graph.insertEdge(3,7,1);
        graph.insertEdge(4,7,1);
        graph.insertEdge(2,5,1);
        graph.insertEdge(2,6,1);
        graph.insertEdge(5,6,1);
        // 显示邻接举证
        graph.showGraph();
        // 深度遍历
        System.out.println("深度遍历 -> ");
        graph.dfs();


        // 广度优先
        System.out.println("\n广度遍历 -> ");
        graph.bfs();
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
     * 得到第一个邻接点的下标 w
     *
     * @param index index
     * @return 如果存在就返回对应的下标如果不存在则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标来获取下一个邻接节点
     *
     * @param v1 v1
     * @param v2 v2
     * @return 返回下一个下标
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param i 索引
     * @return 返回类型
     */
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    /**
     * 深度优先算法
     *
     * @param i         第一次是0
     * @param isVisited 是否被访问数组
     */
    public void dfs(boolean[] isVisited, int i) {
        // 首先我们访问该节点,输出
        System.out.print(getValueByIndex(i) + "->");
        // 将节点设置为已访问
        isVisited[i] = true;
        // 查找节点i的邻接点w
        int w = getFirstNeighbor(i);

        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            // 如果w已经被访问过
            w = getNextNeighbor(i, w);
        }
    }


    /**
     * 广度遍历bfs
     * @param isVisited 访问数组
     * @param i i
     */
    private void bfs(boolean[] isVisited, int i) {
        // 表示队列的头节点对应的下标
        int u;
        // 邻接点w
        int w;
        // 队列,记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();
        // 访问节点,输出节点信息
        System.out.print(getValueByIndex(i) + "->");
        // 标记为已访问
        isVisited[i] = true;
        // 将节点加入队列
        queue.addLast(i);

        while (!queue.isEmpty()) {
            // 取出队列的头结点下标
            u = queue.removeFirst();
            // 得到第一个邻接点的下标
            w = getFirstNeighbor(u);
            // w!=-1
            while (w != -1) {
                // 是否被访问
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "->");
                    // 标记已访问
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }
                // 以u为前驱点找w后面的下一个邻接点
                // 体现出广度优先
                w = getNextNeighbor(u, w);
            }
        }
    }

    /**
     * 遍历所有节点进行广度优先搜索
     */
    private void bfs(){
        isVisited = new boolean[vertexList.size()];
        // bfs遍历
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    /**
     * 对dfs进行重载
     * 遍历所有节点进行dfs
     */
    private void dfs() {
        isVisited = new boolean[vertexList.size()];
        // 遍历所有节点进行dfs
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
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
     *
     * @param v1     表示第几个顶点的下标
     * @param v2     表示连接的点的下标
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
    public int getNumOfVertex() {
        return vertexList.size();
    }


    /**
     * @return 返回边的个数
     */
    public int getNumOfEdges() {
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
    public void showGraph() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }
}
