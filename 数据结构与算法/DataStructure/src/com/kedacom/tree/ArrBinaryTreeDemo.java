package com.kedacom.tree;

public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrBinaryTree binaryTree = new ArrBinaryTree(arr);
        binaryTree.preOrder(0);
    }
}


class ArrBinaryTree {
    private final int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    protected void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
            return;
        }

        System.out.println(arr[index]);

        if ((index * 2 + 1) < arr.length) {
            preOrder(2 * index + 1);
        }

        if ((index * 2 + 2) < arr.length) {
            preOrder(2 * index + 2);
        }
    }
}