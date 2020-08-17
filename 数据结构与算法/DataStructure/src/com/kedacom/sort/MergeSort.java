package com.kedacom.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {34, 69, 119, 30, 80, 90, 18, 27};
        int[] temp = new int[arr.length];
        mergeSorting(arr, 0, arr.length - 1 , temp);
        System.out.println(Arrays.toString(arr));

    }

    private static void mergeSorting(int[] arr, int left, int right, int[] temp) {
        // 分解
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSorting(arr, left, mid, temp);

            mergeSorting(arr, mid + 1, right, temp);

            merge(arr, left, mid, right, temp);
        }
    }

    /**
     * @param arr   数组
     * @param left  初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  中转数组
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // 合并
        int i = left;
        int j = mid + 1;
        int t = 0; // 指向temp数组的当前索引

        // (一)
        // 先把左右两边(有序)的数据按照规则填充到temp数组
        // 直到左右两边的有序序列有一边处理完毕为止
        while (i <= mid && j <= right) temp[t++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];


        // (二)
        // 把有剩余数据一边的数据一次全部填充到temp去
        while (i <= mid) temp[t++] = arr[i++];
        while (j <= right) temp[t++] = arr[j++];

        // (三)
        // 将temp数组的元素拷贝到arr
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) arr[tempLeft++] = temp[t++];
    }
}
