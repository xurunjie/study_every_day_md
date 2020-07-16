package com.kedacom.linkedlist;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        SingleLinkedList linkedList = new SingleLinkedList();
//        linkedList.addHero(hero1);
//        linkedList.addHero(hero2);
//        linkedList.addHero(hero3);
//        linkedList.addHero(hero4);
//        linkedList.show();

        linkedList.addByOrder(hero1);
        linkedList.addByOrder(hero4);
        linkedList.addByOrder(hero2);
        linkedList.addByOrder(hero3);
        linkedList.show();

        HeroNode heroNode = new HeroNode(2, "王月", "小傻瓜");
        linkedList.update(heroNode);
        linkedList.show();

        linkedList.delete(heroNode);
        linkedList.show();

        int length = getLength(linkedList.getHead());
        System.out.println(length);

        heroNode = findLastNode(linkedList.getHead(), 1);
        System.out.println(heroNode);

    }


    private static int getLength(HeroNode head) {
        /**
         * 1.判断两边是否为空
         * 2.
         */
        if (head.getNext() == null) {
            return 0;
        }
        int length = 0;
        HeroNode heroNode = head.getNext();
        while (heroNode != null) {
            length++;
            heroNode = heroNode.getNext();
        }
        return length;
    }

    private static HeroNode findLastNode(HeroNode heroNode, int i) {
        /**
         * 1. 判断链表是否为空
         * 2. 获取链表长度
         * 3. 判断索要获取的是否在链表范围内
         * 4. 反转获取链表
         */
        if (heroNode.getNext() == null) {
            return null;
        }
        int size = getLength(heroNode);
        if (i <= 0 || i > size) {
            return null;
        }
        // 定义辅助变量
        HeroNode cur = heroNode.getNext();
        for (int j = 0; j < size - i; j++) {
            cur = cur.getNext();
        }
        return cur;
    }
}


class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    void delete(HeroNode heroNode) {
        if (head.getNext() == null) {
            System.out.println("链表为空~~");
            return;
        }
        HeroNode temp = head;
        Boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getId() == heroNode.getId()) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (flag) {
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.println("删除节点不存在~~");
        }

    }

    void update(HeroNode hero) {
        /**
         * 1. 先判断链表是否为空
         * 2. 找到该链表
         * 3. 修改链表
         */
        if (head.getNext() == null) {
            System.out.println("链表为空~~");
            return;
        }
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getId() == hero.getId()) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (flag) {
            temp.setName(hero.getName());
            temp.setNickName(hero.getNickName());
        } else {
            System.out.println("未找到要修改的数据~~");
        }
    }

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
    void show() {
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

    void addByOrder(HeroNode hero) {
        // 复制头结点
        HeroNode temp = head;
        // 是否存在 临时变量
        boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getId() > hero.getId()) {
                break;
            } else if (temp.getNext().getId() == hero.getId()) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (flag) {
            System.out.println("已存在,不能添加~~");
        } else {
            hero.setNext(temp.getNext());
            temp.setNext(hero);
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
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}