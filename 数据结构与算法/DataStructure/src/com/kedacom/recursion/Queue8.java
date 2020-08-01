package com.kedacom.recursion;



public class Queue8 {

    private int max = 8;
    private int[] array = new int[max];
    static int count = 0;

    public static void main(String[] args) {
        // 测试
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.println(count);
    }
    // 判断是否冲突
    private boolean judge(int n) {
        for (int i=0; i < n; i++) {
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;

    }

    // 编写一个方法,放置第n个方法
    private void check(int n) {
        if (n == max) {
            // 放到最后一个皇后了
            count++;
            print();
            return;
        }
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后 n ,放到该行的第1列
            array[n] = i;
            // 判断当放置第n个皇后到i列是,是否冲突
            if (judge(n)) {
                // 不冲突 ,接着放n+1个皇后
                check(n + 1);
            }
        }
    }

    private void print() {
        for (int anArray : array) {
            System.out.print(anArray + " ");
        }
        System.out.println();
    }
}
