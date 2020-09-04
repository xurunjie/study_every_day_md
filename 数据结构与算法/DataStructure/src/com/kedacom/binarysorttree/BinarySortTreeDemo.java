package com.kedacom.binarysorttree;

/**
 * 二叉排序树
 *
 * @author python
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int j : arr) {
            binarySortTree.add(new Node(j));
        }
        binarySortTree.add(new Node(0));
        binarySortTree.infixOrder();
        binarySortTree.delNode(7);
        System.out.println("删除节点后的数据------");
        binarySortTree.infixOrder();
    }
}

/**
 * 创建二叉排序树
 */
class BinarySortTree {
    private Node root;

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

/**
 * 节点
 */
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 查找要删除的节点
     *
     * @param value 要删除查找的节点
     * @return 查找到的节点
     */
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

    /**
     * 查找要删除节点的父节点
     *
     * @param value 要找到的节点的值
     *              return 父节点
     */
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

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}