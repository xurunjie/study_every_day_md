package com.kedacom.tree.threadedbinarytree;

public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.setRoot(root);
        binaryTree.threadedNode();

        HeroNode leftNode = node5.getLeft();
        HeroNode rightNode = node5.getRight();
        System.out.println("10号节点的前驱节点\t" + leftNode);
        System.out.println("10号节点的后驱节点\t" + rightNode);

        System.out.println();
        System.out.println("使用线索化方式来遍历线索化二叉树----");
        binaryTree.threadedList();
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

    public HeroNode getLeft() {
        return left;
    }

    public HeroNode getRight() {
        return right;
    }

    // 规定leftType = 0 指向左子树，如果是1则表示指向前驱节点
    private int leftType;
    // 规定rightType = 0 指向右子树，如果是1则表示指向后驱节点
    private int rightType;

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

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }
}

// 定义 BinaryTree 二叉树
class BinaryTree {
    private HeroNode root;

    /**
     * 为了实现线索化，需要创建一个当前节点的前驱节点的指针
     */
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }


    /**
     * 为了实现线索化，需要创建一个当前节点的前驱节点的指针
     */
    public void threadedList(){
        HeroNode node = root;
        while (node != null) {
            // 循环找到leftType==1的节点,第一个找到8节点
            // 后面随着遍历而变化,因为leftType==1时,说明该节点是按照线索
            // 处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }
            // 答应当前这个节点
            System.out.println(node);
            // 如果当前节点的you指针指向的是后继节点,就一直输出
            while (node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }
            // 替换这个遍历的节点
            node = node.getRight();
        }
    }



    public void threadedNode() {
        this.threadedNode(this.root);
    }

    // 编写线索话二叉树
    // node就是当前需要线索话的节点
    public void threadedNode(HeroNode node) {
        if (node == null) {
            return;
        }

        // 先线索化左子树
        threadedNode(node.getLeft());
        // 线索话当前节点
        if (node.getLeft() == null) {
            // 让当前节点的左之争指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型，指向前驱节点
            node.setLeftType(1);
        }

        if (pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            // 修改前驱节点的右指针类型
            pre.setRightType(1);
        }

        // 每处理一个节点，让当前节点是下一个节点的前驱节点
        pre = node;

        // 在线索化右子树
        threadedNode(node.getRight());
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
            } else {
                root.delNode(no);
            }
        } else {
            System.out.println("空树,不能删除");
        }
    }
}
