import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.util.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        Map<String, Record> hashMap = new TreeMap<>();
        try {
            FastScanner sc = new FastScanner(args[0]);
            try {
                int pos = 0;
                int strings = 0;
                while (sc.hasNextLine()) {
                    if (sc.needNextLine()) {
                        strings++;
                        pos = 0;
                    }
                    String sin = sc.nextString();
                    if (sin.length() == 0) {
                        continue;
                    }
                    sin = sin.toLowerCase();
                    StringBuilder s = new StringBuilder(sin);
                    if (s.length() != 0) {
                        pos++;
                        Record y = hashMap.getOrDefault(new String(s), new Record("0", new StringBuilder()));
                        y.poses.append(" ").append(Integer.toString(strings)).append(':').append(Integer.toString(pos));
                        y.count = Integer.toString(Integer.parseInt(y.count) + 1);
                        hashMap.put(new String(s), y);

                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Change input for ParseInt");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of range");
            } catch (NullPointerException e) {
                System.out.println("Bug with hashMap");
            }
        } catch (IOException e) {
            System.out.println("Input file does`t exist");
            return;
        }
        try (BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"))) {
            for (Map.Entry<String, Record> item : hashMap.entrySet()) {
                fw.write(item.getKey() + ' ' + item.getValue().count + item.getValue().poses);
                fw.newLine();
            }
        } catch (CharacterCodingException e) {
            System.out.println("Wrong coding in output file");
        } catch (FileNotFoundException e) {
            System.out.println("Output file doesn't exist");
        } catch (IOException e) {
            System.out.println("Wrong output");
        }
    }
}
