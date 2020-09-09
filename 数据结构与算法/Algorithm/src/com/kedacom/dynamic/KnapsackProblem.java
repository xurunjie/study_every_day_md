package com.kedacom.dynamic;

import java.util.Arrays;

/**
 * @author python
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        // 物品重量
        int[] w = {1, 4, 3};
        // 物品价格
        int[] val = {1500, 3000, 2000};
        // 背包的容量 m
        int m = 4;
        // 物品的个数
        int n = val.length;
        // 创建二维数组
        int[][] v = new int[n + 1][m + 1];

        int[][] path = new int[n + 1][m + 1];
        // 初始化 第一行和第一列 是0 号位不处理
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        Arrays.fill(v[0], 0);

        // 行下标
        for (int i = 1; i < v.length; i++) {
            // 列下标
            for (int j = 1; j < v[0].length; j++) {
                if (w[i - 1] > j) {
                    // 重量大于剩余重量
                    v[i][j] = v[i - 1][j];
                } else {
                    if (v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }

        for (int[] ints : v) {
            System.out.println(Arrays.toString(ints));
        }

        System.out.println("=============================");
        // 打印被存入列表的数量
        for (int[] ints : path) {
            System.out.println(Arrays.toString(ints));
        }
        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入背包\n",i);
                j -= w[i - 1];
            }
            i--;
        }
    }
}
