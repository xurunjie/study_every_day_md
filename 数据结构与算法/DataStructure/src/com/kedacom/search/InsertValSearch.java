package com.kedacom.search;

public class InsertValSearch {
    public static void main(String[] args) {
//        int[] arr = new int[100];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = i;
//        }
        int[] arr = {1, 8, 10, 89, 90, 1000, 1000, 1000, 1100, 1234};
        int mid = insertValSearching(arr, 2, arr.length - 1, 1100);
        System.out.println("mid = " + mid);
    }

    private static int insertValSearching(int[] arr, int left, int right, int findValue) {
        if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
            return -1;
        }
        // 求出mid
        int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
        if (findValue > arr[mid]) {
            // 如果判定比较大从做到有
            return insertValSearching(arr, mid + 1, right, findValue);
        } else if (findValue < arr[mid]) {
            return insertValSearching(arr, left, mid + 1, findValue);
        } else {
            return mid;
        }

    }


}
