import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-21 10:54 AM
 */
@Slf4j
public class FullSort {

    public static void main(String[] args) {
        FullSort sort = new FullSort();
        String str = "baab";
        sort.sort(str.toCharArray(), 0, str.length());

    }

    // a b c d e f g
    public void sort(char[] arr, int start, int length) {
        if (length == 1) {
            log.info("arr:[{}]", Arrays.toString(arr));
        }
        //最开始 start 为 0 从 a 开始 ,第一次自己和自己交换
        for (int i = start; i < start + length; i++) {
            if (isSwap2(arr, start, i)) {
                swap(arr, start, i);
                sort(arr, start + 1, length - 1);
                swap(arr, start, i);
            }
        }

    }

    private void swap(char[] arr, int start, int end) {
        char temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    private boolean isSwap(char[] arr, int start, int n) {
        //当i= N时，是自身与自身进行交换，此时必须交换
        if (start != n) {
            //当除自身与自身相等还有重复的值时，就不进行交换
            if (arr[start] == arr[n]) {
                return false;
            }
        }
        return true;

    }

    /*
    去重的全排列
    基本思想：去重的全排列就是从第一个数字起每个数分别与它后面 非重复出现 的数字交换；
            增加了判断条件，在两个值交换之前首先判断者两个数值是否相等，若相等则不交换，不想等则进行交换
     */
    private boolean isSwap2(char[] arr, int start, int n) {
        //当除自身与自身相等还有重复的值时，就不进行交换
        if (start != n) {
            for (int i = start; i < n; i++) {
                if (arr[i] == arr[n]) {
                    return false;
                }
            }
        }

        return true;

    }
}
