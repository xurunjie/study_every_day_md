package com.kedacom.linkedlist;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        DoubleLinkedList linkedList = new DoubleLinkedList();
        linkedList.addByOrder(hero1);
        linkedList.addByOrder(hero4);
        linkedList.addByOrder(hero3);
        linkedList.addByOrder(hero2);
        linkedList.show();
//        linkedList.del(3);
//        linkedList.show();
    }
}

class DoubleLinkedList {
    private HeroNode2 head = new HeroNode2(0, "", "");

    public void add(HeroNode2 node2) {
        HeroNode2 temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(node2);
        node2.setPre(temp);
    }

    public void addByOrder(HeroNode2 node2) {
        /**
         * 1. 获取头结点
         */
        HeroNode2 temp = head;
        while (true){
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getId()>node2.getId()){
                break;
            }
            temp = temp.getNext();
        }
        if (temp.getNext() != null) {
            node2.setNext(temp.getNext());
            temp.getNext().setPre(node2);
        }
        node2.setPre(temp);
        temp.setNext(node2);
    }

    public void del(int id) {
        HeroNode2 temp = head.getNext();
        if (temp == null) {
            System.out.println("链表为空,不能删除~~");
            return;
        }
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.getId() == id) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (flag) {
            temp.getPre().setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPre(temp.getPre());
            }
        } else {
            System.out.println("未找到删除的节点~~");
        }
    }

    public void show() {
        if (head.getNext() == null) {
            System.out.println("节点为空~~");
            return;
        }
        HeroNode2 temp = head.getNext();
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

}

// 创建英雄节点
class HeroNode2 {
    private int id;
    private String name;
    private String nickName;
    private HeroNode2 next;
    private HeroNode2 pre;

    HeroNode2(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    HeroNode2 getPre() {
        return pre;
    }

    void setPre(HeroNode2 pre) {
        this.pre = pre;
    }

    HeroNode2 getNext() {
        return next;
    }

    void setNext(HeroNode2 next) {
        this.next = next;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getNickName() {
        return nickName;
    }

    void setNickName(String nickName) {
        this.nickName = nickName;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", next=" + next +
                '}';
    }
}