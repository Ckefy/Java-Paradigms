import java.util.ArrayList;
import java.util.*;
public class ReverseTranspose {
    public static void main(String[] args) {
        ArrayList < ArrayList < String >> massive = new ArrayList < > ();
        Scanner std = new Scanner(System.in);
        String s = new String();
        int maxlength = 0;
        while (std.hasNextLine()) {
            ArrayList < String > massive1 = new ArrayList < > ();
            s = std.nextLine();
            if (s == "") {
                massive1.add(s);
                massive.add(massive1);
                continue;
            }
            String[] splited = s.split(" ");
            maxlength = Math.max(maxlength, splited.length);
            for (int i = 0; i < splited.length; i++)
                massive1.add(splited[i]);
            massive.add(massive1);
        }
        for (int i = 0; i < maxlength; i++) {
            for (int j = 0; j < massive.size(); j++) {
                if (i >= massive.get(j).size() || massive.get(j).get(0).length() == 0) {
                    continue;
                } else {
                    System.out.print(massive.get(j).get(i));
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }
}
//String[] s=s1.split(" ");