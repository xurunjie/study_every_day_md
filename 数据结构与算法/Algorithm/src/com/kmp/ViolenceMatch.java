package com.kmp;

/**
 * 暴力破解法
 *
 * @author python
 */
public class ViolenceMatch {
    public static void main(String[] args) {

        // 测试暴力破解

        String str1 = "assaasssssssssddddd";
        String str2 = "dd";
        int res = violenceMatch(str1, str2);
        System.out.println("res = " + res);

    }

    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1len = s1.length;
        int s2len = s2.length;

        int i = 0;
        int j = 0;

        while (i < s1len && j < s2len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            }else{
                i = i - (j - 1);
                j = 0;
            }
        }

        if (j == s2len) {
            return i - j;
        }else {
            return -1;
        }
    }
}
