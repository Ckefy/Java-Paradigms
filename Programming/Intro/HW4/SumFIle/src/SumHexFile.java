import java.io.*;
import java.nio.charset.CharacterCodingException;

public class SumHexFile {

    public static void main(String[] args) throws Exception {
        String sans = new String();
        try {
            args[0] = args[0];
            args[1] = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Name of file doesn`t exist");
            return;
        }
        try (BufferedReader fi = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8"))) {
            String sin = fi.readLine();
            int answer = 0;
            int j = 0;
            while (sin != null) {
                j = 0;
                StringBuilder s = new StringBuilder();
                while (j < sin.length()) {
                    if (sin.charAt(j) == 'x' || sin.charAt(j) == 'X') {
                        s.append('x');
                    } else if ((sin.charAt(j) == '-') || Character.isLetterOrDigit(sin.charAt(j))) {
                        s.append(sin.charAt(j));
                    } else {
                        answer = translate(answer, s);
                    }
                    j++;
                }
                answer = translate(answer, s);
                sin = fi.readLine();
            }
            sans = Integer.toString(answer);

        } catch (FileNotFoundException e) {
            System.out.println("Input file doesn`t exist");
            return;
        } catch (CharacterCodingException e) {
            System.out.println("Wrong coding in input file");
            return;
        } catch (IOException e) {
            System.out.println("Wrong input");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Change input for ParseInt");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of range");
            return;
        }
        try (BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"))) {
            fw.write(sans);
        } catch (CharacterCodingException e) {
            System.out.println("Wrong coding in output file");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Output file doesn`t exist");
            return;
        } catch (IOException e) {
            System.out.println("Wrong output");
            return;
        }

    }

    private static int translate(int answer, StringBuilder s) throws NumberFormatException {
        if (s.length() > 0) {
            String s1 = new String(s);
            if (s1.length() > 1 && s1.startsWith("0x")) {
                answer += Integer.parseUnsignedInt(s1.substring(2), 16);
            } else {
                answer += Integer.parseInt(s1);
            }
            s.setLength(0);
        }
        return answer;
    }
}
