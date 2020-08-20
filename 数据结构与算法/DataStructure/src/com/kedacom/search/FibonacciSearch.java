package com.kedacom.search;

import java.util.Arrays;

public class FibonacciSearch {
    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println("findIndex = " + fibSearch(arr, 1000));
    }

    // 非递归得到一个斐波那契数列
    public static int[] fib() {
        int[] f = new int[20];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    // 编写斐波那契查找算法
    public static int fibSearch(int[] arr, int key) {
        int lower = 0;
        // 使用非递归编写算法
        int high = arr.length - 1;
        int k = 0; // 表示斐波那契额数值的下标
        int mid = 0;
        int[] f = fib();

        while (high > f[k] - 1) k++;
        // 因为 f[k] 值可能大于a的长度,因此我们需要使用Arrays类,构造一个新的数组,并指向a[]
        int[] temp = Arrays.copyOf(arr, 10);

        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        while (lower <= high) {
            mid = lower + f[k - 1] - 1;
            if (key < temp[mid]) {
                high = mid - 1;
                k--;
            } else if (key > temp[mid]) {
                lower = mid + 1;
                k -= 2;
            } else {
                return Math.min(mid, high);
            }
        }
        return -1;
    }
}