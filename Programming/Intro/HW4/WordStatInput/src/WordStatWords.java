import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.util.*;

public class WordStatWords {

    public static void main(String[] args) throws Exception {
        Map<String, String> sorted = new TreeMap<String, String>();
        try {
            args[0] = args[0];
            args[1] = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Name of file doesn`t exist");
            return;
        }
        boolean ok = false;
        try (BufferedReader fi = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8"))) {
            ArrayList<String> words = new ArrayList<>();
            ArrayList<Integer> counter = new ArrayList<>();
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
                        CheckingString(words, counter, s);
                    }
                    j++;
                }
                CheckingString(words, counter, s);
                sin = fi.readLine();
            }
            for (int i = 0; i < words.size(); i++) {
                sorted.put(words.get(i), Integer.toString(counter.get(i)));
            }
            ok = true;
        } catch (FileNotFoundException e) {
            System.out.println("Input file doesn`t exist");
        } catch (CharacterCodingException e) {
            System.out.println("Wrong coding in input file");
        } catch (IOException e) {
            System.out.println("Wrong input");
        } catch (NumberFormatException e) {
            System.out.println("Change input for ParseInt");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of range");
        }
        if (!ok) {
            return;
        }
        try (BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"))) {
            for (Map.Entry e : sorted.entrySet()) {
                fw.write((String) e.getKey());
                fw.write(' ');
                fw.write((String) e.getValue());
                fw.newLine();
            }
        } catch (CharacterCodingException e) {
            System.out.println("Wrong coding in output file");
        } catch (FileNotFoundException e) {
            System.out.println("Output file doesn`t exist");
        } catch (IOException e) {
            System.out.println("Wrong output");
        }
    }

    private static void CheckingString(ArrayList<String> words, ArrayList<Integer> counter, StringBuilder s) throws ArrayIndexOutOfBoundsException {
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
}
