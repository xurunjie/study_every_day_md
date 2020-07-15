package com.kedacom.queue;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {

        ArrayQueue queue = new ArrayQueue(3);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.showQueue();;
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int val = scanner.nextInt();
                    queue.addQueue(val);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出来的数据是%d\n",res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头数据数据是%d\n",res);
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

class ArrayQueue {
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public ArrayQueue(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1;// 指向对象
        rear = -1;
    }

    // 队列已满
    private boolean isFull() {
        return rear==maxSize-1;
    }

    // 队列已空
    private boolean isEmpty() {
        return front==rear;
    }
    /**
     * 逻辑:
     *  添加队列
     *  获取队列
     *  展示队列
     *  获取队列头信息
     */
    public void addQueue(int i){
        // 队列是否满状态,不可添加
        if (isFull()){
            throw new RuntimeException("队列已满不可添加~~");
        }
        arr[++rear]=i;
    }

    // 获取队列
    public int getQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列空的,没有数据~~");
        }
        return arr[++front];
    }

    // 展示队列
    public void showQueue(){
        if (isEmpty()){
            System.out.println("队列空,无数据~~");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]\t=%d\n",i,arr[i]);
        }
    }

    // 头部队列
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列空的,没有数据~~");
        }
        return arr[front+1];
    }

}