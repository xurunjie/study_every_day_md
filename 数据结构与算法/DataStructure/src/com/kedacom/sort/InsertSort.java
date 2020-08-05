package com.kedacom.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InsertSort {
    public static void main(String[] args) {
//        int[] arr = {34, 69, 119, 30,80,90,18,27};
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);

    }

    private static void insertSort(int[] arr) {
        // 插入排序算法  小->大
        // 1.我们要分两个数组一个有序数组,一个无序数组
        // 2. 第一次拿到 [1] 成有序数组 [20,-1,30]成无序数组,然后取出无序数组第一个下标中的元素和有序数组进行对比
        // 2.1 当然我们这里说分组只是概念上的拆分, 保留原有数组, 这边只需要通过临时变量记住下边即可,同时记住下标对应的值
        // 遍历次数应为第一次可以省略所以遍历n-1次

        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];
            int insertIndex = i-1;
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            if (insertIndex+1!=i){
                arr[insertIndex + 1] = insertValue;
//                System.out.println("第 " + i + "次循环:");
//                System.out.println(Arrays.toString(arr));
            }


        }

    }
}
