package com.kedacom.sort;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SelectSort {
    public static void main(String[] args) {
//        int[] arr = {101, 34, 119, 1};
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        selectSort(arr);
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);
//        System.out.println(Arrays.toString(arr));
    }

    public static void selectSort(int[] arr) {
        // 原始数组: 101, 34, 119 1
        // 第一轮排序: 1, 34, 119, 101
        // 算法,先简单 ---> 做复杂,就是把一个复杂的算法,拆分成简单的问题 --> 逐步解决
        int temp = 0;
        int count = 0;
        int reCount = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            // 获取最小值的索引赋值
            int minIndex = i;
            int min = arr[i];
            // 这里遍历如果当前下标索引不是最小值
            for (int j = i; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                    count++;
                }
//                if (arr[i]>arr[j]){
//                    temp = arr[j];
//                    arr[j] = arr[i];
//                    arr[i] = temp;
//                }
            }
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
//                reCount++;
            }
//            System.out.printf("第 %d 轮循环 \n", i + 1);
//            System.out.println(Arrays.toString(arr));
        }

        System.out.println("count = " + count);
        System.out.println("reCount = " + reCount);

    }
}
