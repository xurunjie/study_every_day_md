package com.kedacom.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RadixSort {
    public static void main(String[] args) {
//        int[] arr = {53, 3, 542, 748, 14, 214};

        int[] arr = new int[100000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        radixSorting(arr);
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);
    }

    private static void radixSorting(int[] arr) {
        // 获取值最大的数,我们默认第一个数最大,依次对比
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 获取最大的分桶次数
        int maxLength = (max + "").length();
        // 使用空间换时间的经典算法
        // 创建一个二维数组保存基数排序的桶
        int[][] bucket = new int[10][arr.length];
        // 为了方便统计我们每个桶装了多少个数据我们使用一个数组统计每个桶的数据
        int[] bucketElementCount = new int[10];

        for (int j = 0, n = 1; j < maxLength; j++, n *= 10) {
            // 遍历记录元素和桶中插入同种arr的数据
            for (int i = 0; i < arr.length; i++) {
                int digitOfElement = arr[i] / n % 10;
                bucket[digitOfElement][bucketElementCount[digitOfElement]++] = arr[i];
            }
            // 开始排序
            int index = 0;
            for (int k = 0; k < bucketElementCount.length; k++) {
                if (bucketElementCount[k] != 0) {
                    for (int l = 0; l < bucketElementCount[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                    // 取出后吧桶暂时清空
                    bucketElementCount[k] = 0;
                }
            }
        }
    }
}
