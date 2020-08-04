package com.kedacom.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class BubbleSort {
    public static void main(String[] args) {
        // int[] arr = {3, 9, -1, 10, 20};

        // 测试冒泡排序的速度O(n^2), 创建100000个数据进行测试
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        bubbleSort(arr);
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);

    }

    public static void bubbleSort(int[] arr) {
        // 冒泡排序
        // 1. 首先我们拿到排序的数组
        // 2. 冒泡排序其实是从小到大排列既  小->大
        // 3. 每次拿到当前下标值我们需要和当前下标+1所对应的值进行比较,如果当前下标得值>下标+1对应的值则调换顺序继续进行,一直执行到arr.size-1次
        // 4. 当我们排序好第一次后,则确定了当前数组的最大值,并下标移动至数组的最后一位
        // 5. 由于最大值确定,第二次循环3重新进行,这次我们只需要重复排序值arr.size-1-1次确立第二大的数即可,重次反复执行执行arr.size-1次循环则的到冒泡排序后的值
        // 6. 涉及优化当当前一次3循环没有下标交换的过程,则可以提前结束下面的排序
        // 7. 最坏的O(n)时间复杂度是n^2
        int temp = 0;
        boolean flag = false;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!flag) break;
            else flag = false;
//
//            System.out.println("第" + i + "趟排序后的数组");
//            System.out.println(Arrays.toString(arr));
        }

    }
}
