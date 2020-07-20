package com.kedacom.stack;

import java.util.TreeMap;

public class Calculator {
    public static void main(String[] args) {
        String expression = "3+2*6-2-8";

        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        // 定义相关变量
        int index=0;
        int num1=0;
        int num2=0;
        int oper=0;
        int res = 0;
        char ch;
        do {
            // 依次得到每次到的一个字符
            ch = expression.substring(index, index + 1).charAt(0);
            if (operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {

                    if (operStack.priority(ch) <= operStack.priority((char) operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        numStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);
                }
            } else {
                numStack.push(ch - 48);
            }
            // index+1
            index++;
        } while (index != expression.length());

        while (!operStack.isEmpty()) {
            // 如果符号1栈为空,则计算到最后的结果结束了
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }

        int res2 = numStack.pop();
        System.out.printf("表达式%s = %d",expression,res2);

    }
}

// 先创建一个栈
class ArrayStack2{
    private int maxSize;
    private int[] stack;
    private int top = -1;

    public ArrayStack2(int maxSize) {
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

    public int priority(char oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        }else {
            return -1;
        }
    }

    public boolean isOper(char oper) {
        return oper == '+' || oper == '-' || oper == '*' || oper == '/';
    }

    // 增加一个方法可以返回  当前栈顶的一个值, 大怒时真正的pop
    public int peek(){
        return stack[top];
    }

    public int cal(int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}