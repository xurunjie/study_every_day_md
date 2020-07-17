package com.kedacom.linkedlist;

/**
 * 约瑟夫问题的求解
 *
 */
public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList linkedList = new CircleSingleLinkedList();
        linkedList.addBoy(25);
        linkedList.showBoy();
    }
}

// 创建环形单向链表
class CircleSingleLinkedList{
    private Boy first;

    // 添加小孩节点 构建环形链表
    public void addBoy(int num) {
        if (num <= 1) {
            System.out.println("num值不正确");
            return;
        }

        // 辅助指针
        Boy curBoy = null;

        for (int i = 1; i <= num; i++) {
            Boy boy = new Boy(i);

            if (i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            }else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void showBoy(){
        if (first.getNext()==null) {
            System.out.println("链表为空~~");
            return;
        }
        // 辅助指正
        Boy temp = first;
        while (true){
            System.out.printf("小孩编号~~~ -> %d\n", temp.getId());
            if (temp.getNext()==first){
                break;
            }
            temp = temp.getNext();
        }
    }

}

class Boy {
    private int id;
    private Boy next;

    public Boy(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "id=" + id +
                ", next=" + next +
                '}';
    }
}