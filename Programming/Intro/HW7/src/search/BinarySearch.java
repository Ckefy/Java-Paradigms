package search;

import java.util.ArrayList;

public class BinarySearch {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            arr.add(Integer.parseInt(args[i]));
        }
        int n = arr.size();
        int x = Integer.parseInt(args[0]);
        int left = -1, right = n;
        while (right > left + 1) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) <= x) {
                right = mid;
            } else {
                left = mid;
            }
        }
        System.out.println(right);
    }
}
