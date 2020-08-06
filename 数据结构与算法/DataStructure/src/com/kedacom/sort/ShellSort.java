package com.kedacom.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
//        shellSort(arr);
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        shellSort2(arr);
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);
    }

    private static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j]<arr[j-gap]){
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        arr[j] = arr[j-gap];
                        j -= gap;
                    }
                    // 退出循环后,temp后就找到插入的位置
                    if (j!=i){
                        arr[j] = temp;
                    }
                }
            }
        }
    }

    // 逐步推倒
    private static void shellSort(int[] arr) {
        // 第一轮排序是将9个数据分成4组
//        int temp = 0;
//        for (int i = 4; i < arr.length; i++) {
//            // 步长为4
//            for (int j = i - 4; j >= 0; j -= 4) {
//                // 加上步长的那个元素需要交换
//                if (arr[i] < arr[j]) {
//                    temp = arr[j];
//                    arr[j] = arr[j + 4];
//                    arr[j + 4] = temp;
//                }
//
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//
//        for (int i = 4 / 2; i < arr.length; i++) {
//            // 步长为4
//            for (int j = i - 4 / 2; j >= 0; j -= 4 / 2) {
//                // 加上步长的那个元素需要交换
//                if (arr[i] < arr[j]) {
//                    temp = arr[i];
//                    arr[i] = arr[j];
//                    arr[j] = temp;
//                }
//
//            }
//        }
//
//        System.out.println(Arrays.toString(arr));
//
//
//
//        for (int i = 4 / 2 / 2; i < arr.length; i++) {
//            // 步长为4
//            for (int j = i - 4 / 2 / 2; j >= 0; j -= 4 / 2 / 2) {
//                // 加上步长的那个元素需要交换
//                if (arr[i] < arr[j]) {
//                    temp = arr[i];
//                    arr[i] = arr[j];
//                    arr[j] = temp;
//                }
//
//            }
//        }
//
//        System.out.println(Arrays.toString(arr));
        int temp = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[i]<arr[j]){
                        temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
        }
//        System.out.println(Arrays.toString(arr));
    }
}
