package search;

import java.util.ArrayList;

public class BinarySearch {
    static ArrayList<Integer> arr;

    public static int binSearch(int left, int right, int x) {
        if (right > left + 1) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) <= x) {
                return binSearch(left, mid, x);
            } else {
                return binSearch(mid, right, x);
            }
        }
        return right;
    }

    public static void main(String[] args) {
        arr = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            arr.add(Integer.parseInt(args[i]));
        }
        int n = arr.size();
        int x = Integer.parseInt(args[0]);
        System.out.println(binSearch(-1, n, x));
    }
}
