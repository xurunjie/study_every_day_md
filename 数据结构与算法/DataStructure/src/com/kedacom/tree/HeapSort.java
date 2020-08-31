package com.kedacom.tree;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author python
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};
//        int[] arr = new int[8000000];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = (int) (Math.random() * 100000);
//        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date);
        System.out.println("date1Str = " + date1Str);
        headSorting(arr);
        date = new Date();
        String date2Str = simpleDateFormat.format(date);
        System.out.println("date2Str = " + date2Str);
    }

    private static void headSorting(int[] arr) {
        int temp = 0;
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
        System.out.println("arr = " + Arrays.toString(arr));

         // 将锥顶元素和末尾元素交换,将最大元素下沉的数组末端
         // 重新调整结构,使得其满足堆的定义,然后交换堆顶元素,与当前末尾元素,反复执行调整+交换步骤,直到整个序列有序
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);
        }
        System.out.println("arr = " + Arrays.toString(arr));
    }


    /**
     * 功能: 完成 将以i对应的飞叶子节点的数调整成大顶锥
     * 举例: int arr[] = {4,6,8,5,9}; => i = 1 => adjustHead => 得到 {4,9,8,5,6}
     * 如果我们再次调用 adjustHead 传入的是 i = 0 => 得到 {4,9,8,5,6} => {9,6,8,5,4}
     *
     * @param arr    待调整数组
     * @param i      表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素继续调整 length 是在逐渐减少
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        // 先取出当前元素的值,保存在临时变量
        int temp = arr[i];
        // 开始调整
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 说明坐姿及诶单得知小于有字节点值
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        // 当for循环结束,我们已经将以i为父节点的数的最大值, 放在了最顶(局部)
        // 将temp的值房子调整后的位置
        arr[i] = temp;
    }
}
