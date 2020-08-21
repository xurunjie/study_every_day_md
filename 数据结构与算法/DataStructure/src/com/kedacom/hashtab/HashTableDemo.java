package com.kedacom.hashtab;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author xurunjie
 */
public class HashTableDemo {
    public static void main(String[] args) {

        // 创建哈希表
        HashTab hashTable = new HashTab(7);

        String key;
        Scanner scan = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("**************************");
            System.out.println("add : 添加雇员");
            System.out.println("list : 显示雇员");
            System.out.println("find : 查找雇员");
            System.out.println("exit : 退出系统");
            System.out.println("**************************");
            key = scan.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scan.nextInt();
                    System.out.println("输入名字");
                    String name = scan.next();
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "exit":
                    scan.close();
                    loop = false;
                    break;
                case "find":
                    System.out.println("请输入你要找的雇员");
                    id = scan.nextInt();
                    hashTable.findEmpById(id);
                    break;
                default:
                    break;
            }
        }
    }
}

/**
 * 表示一个雇员
 */
class Emp {
    public int id;
    public String name;
    public Emp next;

    /**
     * @param id   id
     * @param name 姓名
     */
    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


/**
 * 雇员链表
 */
class EmpLinkedList {
    /**
     * 头指正
     */
    private Emp head;

    /**
     * 假定,当天价雇员时,id是自增长的,既id的分配总是从小到大
     *
     * @param emp 雇员
     */
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        // 临时指正
        Emp curEmp = head;
        while (curEmp.next != null) {
            curEmp = curEmp.next;
        }
        curEmp.next = emp;
    }


    /**
     * 获取链表清单
     *
     * @param no number of id
     */
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + (no + 1) + "链表为空");
            return;
        }
        System.out.println("第" + (no + 1) + "链表的信息为");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=> id = %d name = %s\t\n", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
    }


    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                break;
            }
            if (curEmp.next == null) {
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;
        }
        return curEmp;
    }
}

/**
 * 实现hash表
 */
class HashTab {
    private final EmpLinkedList[] empLinkedLists;

    private final int size;

    public HashTab(int size) {
        // size
        this.size = size;

        // 存在空指针
        empLinkedLists = new EmpLinkedList[size];

        // 初始化每个两边
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }

    public void add(Emp emp) {
        // 更具员工的id添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        // 将emp添加到对应的链表
        empLinkedLists[empLinkedListNo].add(emp);
    }

    public void list() {
        IntStream.range(0, size).forEachOrdered(i -> empLinkedLists[i].list(i));
    }

    public int hashFun(int id) {
        return id % size;
    }

    public void findEmpById(int id) {
        int empLinkedListNo = hashFun(id);
        Emp emp = empLinkedLists[empLinkedListNo].findEmpById(id);
        if (emp != null) {
            System.out.printf("在第%d条链表中找到雇员 id = %d name = %s\n", (empLinkedListNo + 1), emp.id, emp.name);
        } else {
            System.out.println("链表中未找到该雇员");
        }
    }
}