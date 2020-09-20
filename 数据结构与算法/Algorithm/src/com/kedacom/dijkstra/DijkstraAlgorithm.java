package com.kedacom.dijkstra;

import java.io.File;
import java.util.Arrays;

/**
 * @author python
 */
public class DijkstraAlgorithm {
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {INF, 5, 7, INF, INF, INF, 2},
                {5, INF, INF, 9, INF, INF, 3},
                {7, INF, INF, INF, 8, INF, INF},
                {INF, 9, INF, INF, INF, 4, INF},
                {INF, INF, 8, INF, INF, 5, 4},
                {INF, INF, INF, 4, 5, INF, 6},
                {2, 3, INF, INF, 4, 6, INF},
        };
        // 创建graph对象
        Graph graph = new Graph(vertex, matrix);
        // 测试
        graph.showGraph();
        // 迪杰斯特拉算法
        int index = 6;
        graph.dsj(index);

    }
}


class Graph {
    /**
     * 顶点数组
     */
    char[] vertex;
    /**
     * 邻接矩阵
     */
    int[][] matrix;

    /**
     * 已经访问的顶点的集合
     */
    VisitedVertex vi;

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    void showGraph() {
        for (int[] link : this.matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    void dsj(int index) {
        vi = new VisitedVertex(vertex.length, index);
        // 更新index顶点到周围顶点的距离和前驱顶点
        this.update(index);
    }

    private void update(int index) {
        int len = 0;
        // 根据遍历我们的邻接矩阵的 matrix[index]
        for (int i = 0; i < matrix[index].length; i++) {
            // 出发顶点到index顶点的距离 + index 顶点到j顶点的距离的和
            len = vi.getDis(index) + matrix[index][i];
            // 如果j的顶点没有被访问过,并且len小于出发顶点到j顶点的距离
            if (!vi.in(i) && len < vi.getDis(i)) {
                // 更新j顶点的前驱为 index顶点
                vi.updatePre(i, index);
                // 更新出发顶点到j顶点的距离
                vi.updateDis(i, len);
            }
        }
    }
}

/**
 * 已访问顶点集合
 */
class VisitedVertex {
    /**
     * 记录顶点是否访问过 1, 代表访问过 0 代表没访问过
     */
    public int[] alreadyArr;
    /**
     * 每个下标对应的值为前一个顶点下标,会动态更新
     */
    public int[] preVisited;
    /**
     * 记录出发顶点到其他所有顶点的距离,比如G为触发顶点, 就会记录G到其它顶点的距离, 会动态更新,求得最短距离就会存放到dis
     */
    public int[] dis;

    /**
     * @param length 表示我们顶点的个数
     * @param index  出发顶点
     */
    public VisitedVertex(int length, int index) {
        this.alreadyArr = new int[length];
        this.preVisited = new int[length];
        this.dis = new int[length];
        // 初始化 dis 数组
        Arrays.fill(dis, Integer.MAX_VALUE);
        // 设置出发顶点被访问过
        this.alreadyArr[index] = 1;
        // 设置出发顶点的访问距离为 0 就可以了
        this.dis[index] = 0;
    }

    /**
     * 判断index顶点是否被访问过
     *
     * @param index index
     * @return 如果访问过, 就访问true, 否则返回false
     */
    public boolean in(int index) {
        return alreadyArr[index] == 1;
    }

    /**
     * 更新出发顶点到index顶点的距离
     *
     * @param index  index
     * @param length 长度
     */
    public void updateDis(int index, int length) {
        dis[index] = length;
    }

    /**
     * 更新pre前驱为index节点
     *
     * @param pre   前驱节点下标
     * @param index i当前下标
     */
    public void updatePre(int pre, int index) {
        preVisited[pre] = index;
    }

    /**
     * 返回出发顶点到index顶点的巨鹿
     *
     * @param index index
     */
    public int getDis(int index) {
        return dis[index];
    }
}