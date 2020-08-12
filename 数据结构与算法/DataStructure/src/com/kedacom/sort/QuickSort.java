package com.kedacom.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        // int[] arr = {-9, 78, 0, -567, -58, 70};
        int[] arr = new int[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }

        quickSorting(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSorting(int[] arr, int left, int right) {
        int l = left;
        int r = right;
        int pivot = arr[(l + r) / 2];
        int temp; // 临时变量  作为交换时使用
        while (l < r) { // 只要
            while (arr[l] < pivot) {
                l += 1;
            }
            while (arr[r] > pivot) {
                r -= 1;
            }
            // 如果l>=r成立  则pivot左右两边的值 已经按照左边全部是小于等于pivot的值,右边大于等于
            if (l >= r) {
                break;
            }
            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            // 如果交换完后

            if (arr[l] == pivot) {
                r--;
            }

            if (arr[r] == pivot) {
                l++;
            }
        }
        if (l == r) {
            l++;
            r--;
        }

        if (left < r) {
            quickSorting(arr, left, r);
        }
        if (right > l) {
            quickSorting(arr, l, right);
        }
    }

}
