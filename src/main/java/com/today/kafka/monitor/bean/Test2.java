package com.today.kafka.monitor.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-21 11:54 PM
 */
public class Test2 {

    public static int binarySearch(int[] arr, int n, int target) {
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (arr[mid] == target)
                return target;
            else if (target > arr[mid])
                l = mid + 1;
            else
                r = mid - 1;

        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = i;
        }

        int i = binarySearch(arr, arr.length, 567);
        System.out.println(i);
    }
}
