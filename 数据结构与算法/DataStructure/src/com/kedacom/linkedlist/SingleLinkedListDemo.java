package com.kedacom.linkedlist;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.addHero(hero1);
        linkedList.addHero(hero2);
        linkedList.addHero(hero3);
        linkedList.addHero(hero4);
        linkedList.show();
    }
}


class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    // 添加
    void addHero(HeroNode heroNode) {
        // 因为head节点不能动，我们要创建一个辅助节点
        HeroNode temp = head;
        while (temp.getNext() != null) {
            // 判断是否为空
            // 不为空则替换temp
            temp = temp.getNext();
        }
        // 末尾添加设置hero node
        temp.setNext(heroNode);
    }

    //展示
    void show(){
        if (head.getNext() == null) {
            System.out.println("链表为空～～");
            return;
        }
        HeroNode temp = head.getNext();
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }
}

// 创建英雄节点
class HeroNode {
    private int id;
    private String name;
    private String nickName;
    private HeroNode next;

    HeroNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    HeroNode getNext() {
        return next;
    }

    void setNext(HeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}