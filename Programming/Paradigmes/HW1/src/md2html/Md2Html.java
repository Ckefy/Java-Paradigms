package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static md2html.ParsingTags.makeHeadings;
import static md2html.ParsingTags.parseInsideTags;

public class Md2Html {

    public static void main(String[] args) {
        StringBuilder answer = new StringBuilder();
        try (BufferedReader inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), StandardCharsets.UTF_8));) {
            boolean isNewRubric = true;
            String readedLine = inputFile.readLine();
            StringBuilder text = new StringBuilder();
            TagMap tags = new TagMap();
            int levelHeading = 0;
            while (readedLine != null) {
                if (readedLine.equals("")) {
                    isNewRubric = true;
                    if (text.length() > 0) {
                        if (text.charAt(text.length() - 1) == '\n') {
                            text.deleteCharAt(text.length() - 1);
                        }
                        answer.append(parseInsideTags(new StringBuilder(makeHeadings(text.toString(), levelHeading)), tags));
                        answer.append('\n');
                    }
                    levelHeading = 0;
                    text = new StringBuilder();
                    tags = new TagMap();
                } else {
                    if (isNewRubric) {
                        int i = 0;
                        while (readedLine.charAt(i) == '#')
                            i++;
                        levelHeading = (readedLine.charAt(i) == ' ') ? i : 0;
                        isNewRubric = false;
                    }
                    readedLine = readedLine.replace("&", "&amp;");
                    readedLine = readedLine.replace("<", "&lt;");
                    readedLine = readedLine.replace(">", "&gt;");
                    text.append(readedLine);
                    text.append('\n');
                }
                readedLine = inputFile.readLine();
            }
            if (text.length() > 0) {
                if (text.charAt(text.length() - 1) == '\n') {
                    text.deleteCharAt(text.length() - 1);
                }
                answer.append(parseInsideTags(new StringBuilder(makeHeadings(text.toString(), levelHeading)), tags));
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println(e.toString());
        }
        try (PrintWriter out = new PrintWriter(new File(args[1]), StandardCharsets.UTF_8)) {
            out.print(answer);
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.err.println(e.toString());
            System.out.println(answer);
        }
    }
}
