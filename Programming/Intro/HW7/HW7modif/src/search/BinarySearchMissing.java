package search;

import java.util.ArrayList;

public class BinarySearchMissing {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            arr.add(Integer.parseInt(args[i]));
        }
        //Pre - array of ints reverse - sorted
        int n = arr.size();
        int x = Integer.parseInt(args[0]);
        int left = -1, right = n;
        //inv:
        //right` == size || right < size && arr.get(right) <= key
        //left == -1 || left > -1 && arr.get(left) >= key
        //right`` - left`` <= (right` - left`)/2 && left` + 1 <= right
        while (right > left + 1) {
            //inv saved && right` > left` + 1
            int mid = left + (right - left) / 2;
            //inv saved && right` > left` + 1 && left` <= mid <= right` && arr.get(left`) >= arr.get(mid) >= arr.get(right`)
            if (arr.get(mid) <= x) {
                right = mid;
                //inv saved && right` > left` + 1 && arr.get(mid) <= x;
                //right`` = mid, left`` = left`;
                //right`` < right`;
                //arr.get(right) <= x;
                //inv saved
            } else {
                left = mid;
                //inv saved && right > left + 1 && arr.get(mid) > x;
                //left`` = mid, right`` = right`;
                //left`` > left
                //arr.get(left) > x;
                //inv saved
            }
        }
        //inv saved && left +1 = right
        //right!= arr.size() && arr.get(right) = x && answer = right` || right = arr.size && arr.get(arr.size()-1) && answer = -right - 1 ||
        //right < arr.size() && arr.get(right)!=x && arr.get(right) < x && answer = -r - 1 || right < arr.size && arr.get(right) = x && answer = right;
        if (right != arr.size() && arr.get(right) == x) {
            //right < arr.size() && arr.get(right) = x && answer = right;
            System.out.println(right);
        } else {
            //(right = arr.size() || arr.get(right) != x) && answer = -right - 1;
            System.out.println(-right - 1);
        }
        //Post answer < arr.size() || answer >= 0 && arr.get (answer - 1) < x || answer < 0 ** arr.get(-ans - 1) < x
    }
}
