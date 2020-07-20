package com.kedacom.stack;

import jdk.nashorn.internal.ir.IfNode;

import java.util.Scanner;

public class ArrayStackArray {
    public static void main(String[] args) {
        // 初始化数据
        ArrayStack arrayStack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("show: 显示栈数据~~");
            System.out.println("push: 插(入栈)数据~~");
            System.out.println("pop: 取(出栈)数据~~");
            System.out.println("exit: 程序结束~~");
            key = scanner.next();
            switch (key) {
                case "exit":
                    loop = false;
                    break;
                case "show":
                    arrayStack.list();
                    break;
                case "push":
                    System.out.println("请输入入栈的数~~");
                    int value = scanner.nextInt();
                    arrayStack.push(value);
                    break;
                case "pop":
                    try {
                        int res = arrayStack.pop();
                        System.out.printf("出栈的数据是%d\n",res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }
}


class ArrayStack{
    private int maxSize;
    private int[] stack;
    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }
    // 栈满
    public boolean isFull(){
        return maxSize - 1 == top;
    }
    // 栈空
    public boolean isEmpty(){
        return top == -1;
    }

    // 入栈
    public void push(int value) {
        // 判断栈是否满了
        if (isFull()) {
            System.out.println("栈满~~");
            return;
        }
        stack[++top] = value;
    }

    // 出栈
    public int pop(){
        if (isEmpty()) {
            throw new RuntimeException("栈空没有数据~~");
        }
        //  先取出栈顶的值取出
        int value = stack[top];
        top--;
        return value;
    }

    public void list(){
        if (isEmpty()) {
            throw new RuntimeException("栈空没有数据~~");
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }

}