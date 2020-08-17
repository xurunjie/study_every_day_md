package com.kedacom.search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        // 使用二分查询的前提是, 该数组是有序的
        int[] arr = {1, 8, 10, 89, 90, 1000, 1000, 1000, 1234};
//        int resIndex = binarySearching(arr, 0, arr.length, 88);
        List<Integer> resIndexList = binaryListSearching(arr, 0, arr.length, 1000);
        System.out.println("resIndex = " + resIndexList);
    }

    private static int binarySearching(int[] arr, int left, int right, int findVal) {
        // 结束递归条件
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal) {
            // 向右递归
            return binarySearching(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归
            return binarySearching(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

    private static List<Integer> binaryListSearching(int[] arr, int left, int right, int findVal) {
        // 结束递归条件
        if (left > right) {
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal) {
            // 向右递归
            return binaryListSearching(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            // 向左递归
            return binaryListSearching(arr, left, mid - 1, findVal);
        } else {
            ArrayList<Integer> resIndexList = new ArrayList<Integer>();
            resIndexList.add(mid);
            // 创建临死下标
            int temp = mid - 1;
            while (temp >= 0 && arr[temp] == findVal) resIndexList.add(temp--);
            temp = mid + 1;
            while (temp <= arr.length - 1 && arr[temp] == findVal) resIndexList.add(temp++);

            return resIndexList;
        }
    }
}
