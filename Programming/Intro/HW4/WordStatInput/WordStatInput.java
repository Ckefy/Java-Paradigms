import java.io.*;
import java.util.ArrayList;

public class WordStatInput {

    public static void main(String[] args) {
        BufferedReader fi = null;
        BufferedWriter fw = null;
        try {
            ArrayList<String> words = new ArrayList<>();
            ArrayList<Integer> counter = new ArrayList<>();
            fi = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8"));
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"));
            String sin = fi.readLine();
            int j = 0;
            while (sin != null) {
                sin = sin.toLowerCase();
                j = 0;
                StringBuilder s = new StringBuilder();
                while (j < sin.length()) {
                    if (Character.isLetter(sin.charAt(j)) || Character.getType(sin.charAt(j)) == 20 || sin.charAt(j) == '\'') {
                        s.append(sin.charAt(j));
                    } else {
                        if (s.length() != 0) {
                            String check = new String(s);
                            if (words.size() == 0) {
                                words.add(check);
                                counter.add(1);
                            } else {
                                for (int i = 0; i < words.size(); i++) {
                                    if (words.get(i).equals(check)) {
                                        counter.set(i, counter.get(i) + 1);
                                        break;
                                    } else if (i == words.size() - 1) {
                                        words.add(check);
                                        counter.add(1);
                                        break;
                                    }
                                }
                            }
                            s.setLength(0);
                        }
                    }
                    j++;
                }
                if (s.length() != 0) {
                    String check = new String(s);
                    if (words.size() == 0) {
                        counter.add(1);
                        words.add(check);
                    } else {
                        for (int i = 0; i < words.size(); i++) {
                            if (words.get(i).equals(check)) {
                                counter.set(i, counter.get(i) + 1);
                                break;
                            } else if (i == words.size() - 1) {
                                counter.add(1);
                                words.add(check);
                                break;
                            }
                        }
                    }
                    s.setLength(0);
                }
                sin = fi.readLine();
            }
            for (int i = 0; i < words.size(); i++) {
                fw.write(words.get(i));
                fw.write(' ');
                String ans = Integer.toString(counter.get(i));
                fw.write(ans);
                fw.write("\r\n");
            }
        } catch (Exception e) {
            System.out.print("hi");
        } finally {
            if (fi != null) {
                fi.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
    }
}
