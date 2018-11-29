package com.today.kafka.monitor.bean;

import java.util.Arrays;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-21 10:07 PM
 */
public class Test {

    public static void main(String[] args) {
        String str = "aaaaa";
        char[] chars = str.toCharArray();

        perm(chars, 0, chars.length);

    }

    public static void perm(char[] arrs, int start, int length) {

        if (length == 1) {
            System.out.println(Arrays.toString(arrs));
        }

        for (int i = start; i < start + length; i++) {
            if (isSwap(arrs, start, i)) {
//            if (isSwap(arrs, i, start)) {
                swap(arrs, start, i);
                perm(arrs, start + 1, length - 1);
                swap(arrs, start, i);
            }


        }

    }

    public static void swap(char[] arrs, int start, int n) {
        char tmp = arrs[start];
        arrs[start] = arrs[n];
        arrs[n] = tmp;
    }


    public static boolean isSwap(char[] arrs, int start, int n) {

        if (start != n) {
            for (int i = start; i < n; i++) {
                if (arrs[start] == arrs[n]) {
                    return false;
                }
            }

        }
        return true;
    }


}
