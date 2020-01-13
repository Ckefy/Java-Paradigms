import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SumFile {

    public static void main(String[] args) throws Exception {
        Scanner file = null;
        PrintWriter fw = null;
        try {
            String s1 = args[0];
            String s2 = args[1];
            file = new Scanner(new File(s1), "utf-8");
            fw = new PrintWriter(new File(s2), "utf-8");
            ArrayList<String> array = new ArrayList<>();
            while (file.hasNextLine()) {
                array.add(file.next());
            }
            int answer = 0;
            StringBuilder sans = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                int j = 0;
                while (j < array.get(i).length()) {
                    char c = array.get(i).charAt(j);
                    if (c == '+' || c == '-' || c == '.' || (c >= '0' && c <= '9')) {
                        sans.append(array.get(i).charAt(j));
                        j++;
                    } else {
                        if (sans.length() != 0) {
                            String s = new String(sans);
                            answer += Integer.parseInt(s);
                            sans.setLength(0);
                        }
                        j++;
                    }
                    if (j >= array.get(i).length()) {
                        if (sans.length() != 0) {
                            String s = new String(sans);
                            answer += Integer.parseInt(s);
                            sans.setLength(0);

                        }
                    }
                }
                if (sans.length() != 0) {
                    String s = new String(sans);
                    answer += Integer.parseInt(s);
                    sans.setLength(0);

                }
            }
            fw.write(answer);
        } finally {
            if (file!=null){
                file.close();
            }
            if (fw!=null){
                fw.close();
            }
        }
    }
}
