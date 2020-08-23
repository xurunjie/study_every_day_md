package com.kedacom.tree;

import javax.xml.transform.Source;

/**
 *
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关圣");
        // 说明
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);
//        binaryTree.preOrder();
//        System.out.println("--------------------");
//        binaryTree.infixOrder();
//        System.out.println("--------------------");
//        binaryTree.postOrder();
//        System.out.println("前序遍历------");
////        HeroNode resNode = binaryTree.preOrderSearch(5);
////        HeroNode resNode = binaryTree.infixOrderSearch(5);
//        HeroNode resNode = binaryTree.postOrderSearch(2);
//        if (resNode != null) {
//            System.out.printf("找到了 信息为 no=%d name=%s\n", resNode.getNo(), resNode.getName());
//        } else {
//            System.out.printf("没有找到 no=%d 的英雄\n", 5);
//        }
        // 删除前
        System.out.println("删除前 -> 前序");
        binaryTree.preOrder();
        binaryTree.delNode(3);
        System.out.println("删除后 -> 后序");
        binaryTree.postOrder();
    }
}

// 定义 BinaryTree 二叉树
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("当前二叉树为空无法遍历");
        }
    }

    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("当前二叉树为空无法遍历");
        }
    }

    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("当前二叉树为空无法遍历");
        }
    }

    public HeroNode preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }


    public HeroNode infixOrderSearch(int no) {
        if (this.root != null) {
            return this.root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    public HeroNode postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        } else {
            return null;
        }
    }


    public void delNode(int no) {
        if (this.root != null) {
            if (this.root.getNo() == no) {
                root = null;
            }else {
                root.delNode(no);
            }
        }else {
            System.out.println("空树,不能删除");
        }
    }
}

/**
 * hero node 节点
 */
class HeroNode {
    private final int no;
    private final String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);
        // 递归左子树
        if (this.left != null) {
            this.left.preOrder();
        }
        // 递归向右子树谦虚遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    // 中序遍历
    public void infixOrder() {
        // 递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    // 后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }


    // 前序遍历
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序遍历");
        if (this.no == no) {
            return this;
        }

        HeroNode result = null;
        if (this.left != null) {
            // 说明存在子节点树
            result = this.left.preOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.right != null) {
            result = this.right.preOrderSearch(no);
        }
        return result;
    }


    // 中序遍历
    public HeroNode infixOrderSearch(int no) {

        HeroNode result = null;

        if (this.left != null) {
            // 说明存在子节点树
            result = this.left.infixOrderSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.no == no) {
            return this;
        }


        if (this.right != null) {
            result = this.right.infixOrderSearch(no);
        }
        return result;
    }


    public HeroNode postOrderSearch(int no) {
        HeroNode result = null;

        if (this.left != null) {
            // 说明存在子节点树
            result = this.left.postOrderSearch(no);
        }

        if (result != null) {
            return result;
        }
        if (this.right != null) {
            result = this.right.postOrderSearch(no);
        }
        if (result != null) {
            return result;
        }
        System.out.println("进入后续遍历");
        if (this.no == no) {
            return this;
        }
        return result;
    }


    // 删除节点
    public void delNode(int no) {
        // 如果当前节点的左子节点不为空,并且左子节点就是要删除节点,就将 this.left = null, 并且就返回
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }
        // 如果当前结点的右子节点不为空,并且有右子节点, 就是要删除节点 九江 this.right = null; 并且就返回
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }

        if (this.left != null) {
            this.left.delNode(no);
        }

        if (this.right != null) {
            this.right.delNode(no);
        }
    }
}
