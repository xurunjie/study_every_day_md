package com.kedacom.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {

        CircleArray queue = new CircleArray(4);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.showQueue();
                    ;
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int val = scanner.nextInt();
                    queue.addQueue(val);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出来的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头数据数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~~");
    }
}


class CircleArray {
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[arrMaxSize];
    }

    private boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    private boolean isEmpty() {
        return rear == front;
    }


    public void addQueue(int n) {
        /**
         * 1. 判断队列是否满了
         * 2. 更新rear
         */
        if (isFull()) {
            System.out.println("队列已满~~");
            return;
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空的,没有数据~~");
        }
        /**
         * 1. 先取出队列中头数据这个使我们要返回的
         * 2. 取模front  -> 防止front 越界
         */
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }


    public void showQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            System.out.println("队列已空~~");
            return;
        }

        // 准确获取有效数据
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i] % maxSize);
        }


    }

    private int size() {
        return (rear + maxSize - front) % maxSize;
    }

    // 显示头元素
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列已空~~");

        }
        return arr[front];
    }
}