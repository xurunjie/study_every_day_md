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
        map[3][1] = 1;
        map[3][2] = 1;
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                System.out.printf("%d\t", anAMap);
            }
            System.out.println();
        }
        setWay(map, 1, 1);
        // 输出新的地图,小球走过,并标识过的地图
        System.out.println("后面的位置------------");
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                System.out.printf("%d\t", anAMap);
            }
            System.out.println();
        }
    }

    // 使用迷宫回溯问题给小球招路
    // 1. map代表地图
    // 2. i,j代表起始出发位置
    // 3. 如果小球带6-5位置既结束
    // 4. 约定当map[i][j] = 0表示未走过; 2代表通路可以走;3表示该店已经走过走不通
    // 走迷宫的策略 下->右->上->左问题,如果该店走不通,再回溯
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) { // 向下走
                    return true;
                } else if (setWay(map, i, j + 1)) { // 向右走
                    return true;
                } else if (setWay(map, i - 1, j)) { //向上走
                    return true;
                } else if (setWay(map, i, j - 1)) { //向左走
                    return true;
                } else {
                    // 该点走不通
                    System.out.println("--------------------------------");
                    for (int[] aMap : map) {
                        for (int anAMap : aMap) {
                            System.out.printf("%d\t", anAMap);
                        }
                        System.out.println();
                    }
                    System.out.println("--------------------------------");
                    map[i][j] = 3;
                    return false;
                }
            } else { // 如果map[i][j]!=0,可能是1,2,3
                System.out.println("--------------------------------");
                for (int[] aMap : map) {
                    for (int anAMap : aMap) {
                        System.out.printf("%d\t", anAMap);
                    }
                    System.out.println();
                }
                System.out.println("--------------------------------");
                return false;
            }
        }
    }

}
