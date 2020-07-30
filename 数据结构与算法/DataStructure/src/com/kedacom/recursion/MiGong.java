package com.kedacom.recursion;

public class MiGong {
    public static void main(String[] args) {
        // 创建二维迷宫
        // 地图
        int[][] map = new int[8][7];
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[3][1]=1;
        map[3][2]=1;
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                System.out.printf("%d\t", anAMap);
            }
            System.out.println();
        }
    }
}
