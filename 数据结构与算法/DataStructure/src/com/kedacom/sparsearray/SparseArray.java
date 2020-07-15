package com.kedacom.sparsearray;

public class SparseArray {
    public static void main(String[] args) {
        // 创建一个原始的二维数组 11 * 11
        // 0 : 标识没有旗子, 1 : 表示黑子, 2 : 表示蓝子
        int[][] ints = new int[11][11];
        ints[1][2] = 1;
        ints[2][3] = 2;
        ints[3][5] = 2;
        ints[4][6] = 2;
        ints[5][7] = 2;
        ints[6][8] = 2;
        // 原始的二维数组
        System.out.println("原始的二维数组~~");
        for (int[] anInt : ints) {
            for (int i : anInt) {
                System.out.printf("%d\t",i);
            }
            System.out.println();
        }
        // 将二维数组转稀疏数组
        // 统计获取二维数组中的所有棋盘子
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                if (ints[i][j] > 0){
                    sum++;
                }
            }
        }

        // 初始化稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        sparseArr[0][0]=11;
        sparseArr[0][1]=11;
        sparseArr[0][2]=sum;
        // 插入相对位置
        // 插入限定为
        int count = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                if (ints[i][j] > 0){
                    count++;
                    sparseArr[count][0]=i;
                    sparseArr[count][1]=j;
                    sparseArr[count][2]=ints[i][j];
                }
            }
        }

        // 打印稀疏数组
        System.out.println("稀疏数组~~");
        for (int[] ints1 : sparseArr) {
            System.out.printf("%d\t%d\t%d\t\n",ints1[0],ints1[1],ints1[2]);
        }

        // 转化数组为 二维数组
        // 初始化创建数组
        int[][] orgArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        // 写入数据
        for (int i = 1; i < sparseArr.length; i++) {
            orgArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        System.out.println("还原二维数据~~");
        for (int[] ints1 : orgArr) {
            for (int i : ints1) {
                System.out.printf("%d\t",i);
            }
            System.out.println();
        }
    }
}
