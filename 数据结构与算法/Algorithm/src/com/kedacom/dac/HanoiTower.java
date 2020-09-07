package com.kedacom.dac;

/**
 * @author python
 */
public class HanoiTower {
    public static void main(String[] args) {
        hanoiTower(2, 'A', 'B', 'C');
    }

    public static void hanoiTower(int num, char a, char b, char c) {
        // 如果只有一个盘
        if (num == 1) {
            System.out.println("第" + 1 + "个盘从 " + a + "->" + c);
        } else {
            // 如果我们有 n >= 2 情况, 我们总是可以看做两个盘 1. 最下边的一个盘 2. 剩余的盘子
            // 1.先把最上面的盘A -> B 移动过程中使用到C
            hanoiTower(num - 1, a, c, b);
            // 2.把最下面的盘子 A->C
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            // 3. 把b塔的所有的盘移动到c 从B->C ,移动过程中使用到A塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
