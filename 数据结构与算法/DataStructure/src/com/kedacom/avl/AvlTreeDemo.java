package com.kedacom.avl;

/**
 * @author python
 */
public class AvlTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4, 3, 6, 5, 7, 8};
        // 创建一个AVL tree对象
        AvlTree avlTree = new AvlTree();
        //添加节点
        for (int j : arr) {
            avlTree.add(new Node(j));
        }
        System.out.println("中序遍历");
        avlTree.infixOrder();
        System.out.println("avlTree.getRoot().height() = " + avlTree.getRoot().height());
        System.out.println("avlTree.getRoot().leftHeight() = " + avlTree.getRoot().leftHeight());
        System.out.println("avlTree.getRoot().rightHeight() = " + avlTree.getRoot().rightHeight());
    }
}

class AvlTree {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public void add(Node node) {
        if (root != null) {
            root.add(node);
        } else {
            root = node;
        }
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("root 节点为空");
        }
    }


    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return this.root.searchParent(value);
        }
    }

    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return this.root.search(value);
        }
    }

    /**
     * 功能: 二叉排序树的删除
     * 1. 删除叶子节点
     * 2. 删除只有一颗子树的节点
     * 3. 删除有两颗子树的节点
     *
     * @param value 删除的节点对应
     */
    public void delNode(int value) {
        if (root == null) {
            return;
        }
        // 查找节点
        Node targetNode = search(value);
        // 如果节点不存在
        if (targetNode == null) {
            return;
        }
        // 如果发现当前这颗二叉排序树只有一个节点
        if (root.left == null && root.right == null) {
            root = null;
            return;
        }

        // 找到targetNode的父节点
        Node parent = searchParent(value);
        // 如果删除的节点是叶子节点
        if (targetNode.left == null && targetNode.right == null) {
            // 说明targetNode属于叶子节点
            // 判断是左子节点还是右子节点
            if (parent.left != null && parent.left.value == value) {
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {
                parent.right = null;
            }
        } else if (targetNode.left != null && targetNode.right != null) {
            // 删除非叶子节点
            targetNode.value = delRightTreeMin(targetNode.right);
        } else {
            // 删除只有一个子节点
            // 如果删除的节点只有左子节点
            if (targetNode.left != null) {
                if (parent != null) {
                    if (parent.left.value == value) {
                        // 如果是左子节点
                        parent.left = targetNode.left;
                    } else {
                        // 如果是右子节点
                        parent.right = targetNode.left;
                    }
                } else {
                    root = targetNode.left;
                }
            } else {
                if (parent != null) {
                    // 如果targetNode是parent的左子节点
                    if (parent.left.value == value) {
                        parent.left = targetNode.right;
                    } else {
                        parent.right = targetNode.right;
                    }
                } else {
                    root = targetNode.left;
                }
            }
        }
    }

    /**
     * @param node 传入的节点
     * @return 返回的以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        // 循环查找左节点,就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        // 这时target指向最小节点
        delNode(target.value);
        return target.value;
    }

}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }


    private void leftRotate() {
        // 创建新的节点,以当前根节点的值
        Node newNode = new Node(value);
        // 把新的节点的左子树设置成当前节点的左子树
        newNode.left = left;
        // 把新的节点的右子树设置成带你过去节点的柚子树的左子树
        newNode.right = right.left;
        // 把当前节点的值替换成右子节点的值
        value = right.value;
        // 把当前节点的右子树设置成当前节点右子树的右子树
        right = right.right;
        // 把当前节点的左子树(左子节点)设置成新的节点
        left = newNode;

    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    /**
     * 添加节点
     *
     * @param node 新增节点
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
        // 当天价玩一个节点后, 如果:(右子树的高度 - 左子树的高度) > 1, 左旋转
        if (rightHeight() - leftHeight() > 1) {
            leftRotate();
        }
    }

    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    public Node searchParent(int value) {
        if (this.left != null && this.left.value == value) {
            return this;
        } else if (this.right != null && this.right.value == value) {
            return this;
        } else {
            // 如果查找的值小于节点的值,并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);
            } else {
                // 没有父节点
                return null;
            }
        }
    }

    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            if (this.left != null) {
                return this.left.search(value);
            } else {
                return null;
            }
        } else {
            if (this.right != null) {
                return this.right.search(value);
            } else {
                return null;
            }
        }
    }
}