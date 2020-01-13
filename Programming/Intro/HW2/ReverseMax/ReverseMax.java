import java.util.ArrayList;
import java.util.*;
public class ReverseMax {
    public static void main(String[] args) {
        ArrayList<ArrayList <String>> massive = new ArrayList<> ();
        Scanner std = new Scanner(System.in);
        String s = new String();
        ArrayList<Integer> stroki = new ArrayList <> ();
        ArrayList<Integer> stolb = new ArrayList <> ();
        while (std.hasNextLine()) {
            ArrayList<String> massive1 = new ArrayList<> ();
            s = std.nextLine();
            if (s.length() == 0) {
                massive1.add(s);
                massive.add(massive1);
                stroki.add(-1 * Integer.MAX_VALUE);
                continue;
            }
            String[] splited = s.split(" ");
            stroki.add(Integer.parseInt(splited[0]));
            for (int i = 0; i < splited.length; i++) {
                massive1.add(splited[i]);
                stroki.set(massive.size(), Math.max(stroki.get(massive.size()), Integer.parseInt(splited[i])));
            }
            massive.add(massive1);
        }
        for (int i = 0; i < massive.size(); i++) {
            for (int j = 0; j < massive.get(i).size(); j++) {
                if (massive.get(i).get(0).length() == 0 || massive.get(i).size() <= j) {
                    continue;
                } else if (stolb.size()<=j) {
                    stolb.add(j, Integer.parseInt(massive.get(i).get(j)));
                } else {
                    stolb.set(j, Math.max(stolb.get(j), Integer.parseInt(massive.get(i).get(j))));
                }
            }
        }
        for (int i = 0; i < massive.size(); i++) {
           if (massive.get(i).get(0).length() == 0){
		System.out.println();
		continue;
	   }
	   for (int j = 0; j < massive.get(i).size(); j++) {
                System.out.print(Math.max(stroki.get(i), stolb.get(j)));
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
//String[] s=s1.split(" ");