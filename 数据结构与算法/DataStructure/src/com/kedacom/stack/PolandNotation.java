package com.kedacom.stack;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
//        String expression = "3 4 + 5 * 6 -";
//        int res = calculate(getStringList(expression));
//        System.out.println("res = " + res);

        String expression = "1+((2+3)*   4)-5";
        List<String> stringList = toInfixExpressionList(expression);
        System.out.println("stringList = " + stringList);
        List<String> parseExpression = getParseExpression(stringList);
        System.out.println(parseExpression);


    }

    public static List<String> toInfixExpressionList(String s) {
        // 定义一个数组存取内容
        ArrayList<String> list = new ArrayList<>();
        // 初始指正
        int i = 0;
        StringBuilder str;//存取多位数的拼接
        char c; // 遍历到没一个字符就放入 c
        do {
            if ((c = s.charAt(i)) == 32) {
                i++;
                continue;
            }
            if ((c = s.charAt(i)) < 48||(c = s.charAt(i)) > 57) {
                list.add("" + c);
                i++;
            }else {
                str = new StringBuilder();
                while (i<s.length()&&(c=s.charAt(i))>=48&&(c = s.charAt(i)) < 57){
                    str.append(c);
                    i++;
                }
                list.add(str.toString());
            }

        } while (i < s.length());
        return list;
    }


    public static List<String> getParseExpression(List<String> list) {
        Stack<String> s1 = new Stack<>();
        ArrayList<String> s2 = new ArrayList<>();
        for (String item : list) {
            if (item.matches("\\d+")){
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();
            }else {
                while (s1.size() != 0 && Operator.getValue(s1.peek()) >= Operator.getValue(item)) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }
        }
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    public static List<String> getStringList(String expression) {
        String[] split = expression.split(" ");
        // S数组接受方便遍历
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, split);
        return arrayList;
    }

    public static int calculate(List<String> ls) {
        Stack<String> stack = new Stack<>();
        for (String l : ls) {
            if (l.matches("\\d")) stack.push(l);
            else {
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                switch (l) {
                    case "+":
                        res = num2 + num1;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num1 / num2;
                        break;
                    default:
                        throw new RuntimeException("运算符有误");
                }
                stack.push(res+"");
            }
        }
        return Integer.parseInt(stack.pop());
    }
}


class Operator {
    private static final int ADD = 1;
    private static final int SUB = 1;
    private static final int MUL = 1;
    private static final int DIV = 1;

    public static int getValue(String operation) {
        int res = 0;
        switch (operation) {
            case "+":
                res = ADD;
                break;
            case "-":
                res = SUB;
                break;
            case "*":
                res = MUL;
                break;
            case "/":
                res = DIV;
                break;
            default:
                break;
        }
        return res;
    }
}
