import java.io.IOException;
import java.util.ArrayList;

public class ReverseSum {

    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner();
            ArrayList<ArrayList<String>> massive = new ArrayList<>();
            ArrayList<String> massive1 = new ArrayList<>();
            ArrayList<Integer> lines = new ArrayList<>();
            ArrayList<Integer> rows = new ArrayList<>();
            while (sc.hasNextLine()) {
                if (sc.needChangeLine()) {
                    massive.add(massive1);
                    massive1 = new ArrayList<>();
                    continue;
                }
                massive1.add(sc.nextElement());
            }

            for (int i = 0; i < massive.size(); i++) {
                for (int j = 0; j < massive.get(i).size(); j++) {
                    if (massive.get(i).get(0).length() == 0) {
                        lines.add(0);
                    } else if (lines.size() <= i) {
                        lines.add(Integer.parseInt(massive.get(i).get(j)));
                    } else {
                        lines.set(i, lines.get(i) + Integer.parseInt(massive.get(i).get(j)));
                    }
                }
            }
            for (int i = 0; i < massive.size(); i++) {
                for (int j = 0; j < massive.get(i).size(); j++) {
                    if (massive.get(i).get(0).length() == 0 || massive.get(i).size() <= j) {
                        continue;
                    } else if (rows.size() <= j) {
                        rows.add(j, Integer.parseInt(massive.get(i).get(j)));
                    } else {
                        rows.set(j, rows.get(j) + Integer.parseInt(massive.get(i).get(j)));
                    }
                }
            }
            for (int i = 0; i < massive.size(); i++) {
                if (massive.get(i).get(0).length() == 0) {
                    System.out.println();
                    continue;
                }
                for (int j = 0; j < massive.get(i).size(); j++) {
                    System.out.print(lines.get(i) + rows.get(j) - Integer.parseInt(massive.get(i).get(j)));
                    System.out.print(' ');
                }
                System.out.println();
            }
        } catch (NumberFormatException e) {
            System.out.println("Change input for ParseInt");
        } catch (IOException e) {
            System.out.println("Wrong input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Out of range");
        }
    }
}
