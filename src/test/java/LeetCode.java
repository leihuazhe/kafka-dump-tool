import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-21 11:40 AM
 */
public class LeetCode {

    public static void main(String[] args) {
        Solution s = new Solution();

        List<List<Integer>> list = s.permuteUnique(new int[]{1, 1, 2});
        System.out.println(list);

    }

    static class Solution {
        public List<List<Integer>> permuteUnique(int[] nums) {
            List<List<Integer>> list = new ArrayList<>();
            perm(nums, 0, nums.length, list);
            return list;

        }

        private void perm(int[] nums, int start, int length, List<List<Integer>> list) {
            if (length == 1) {
                List<Integer> subList = new ArrayList<>();
                for (int num : nums) {
                    subList.add(num);
                }
                list.add(subList);
            }
            for (int i = start; i < start + length; i++) {
                if (isSwap(nums, start, i)) {
                    swap(nums, i, start);
                    perm(nums, start + 1, length - 1, list);
                    swap(nums, i, start);
                }

            }
        }

        private void swap(int[] nums, int i, int n) {
            int tmp = nums[i];
            nums[i] = nums[n];
            nums[n] = tmp;
        }

        private boolean isSwap(int[] nums, int start, int n) {
            if (start != n) {
                for (int i = start; i < n; i++) {
                    if (nums[i] == nums[n]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
