package md2html;

class ParsingTags {

    static String makeHeadings(String text, int levelHeading) {
        text = text.substring(levelHeading + ((levelHeading > 0) ? 1 : 0));
        if (levelHeading == 0) {
            return "<p>" + text + "</p>";
        } else {
            return "<h" + levelHeading + ">" + text + "</h" + levelHeading + ">";
        }
    }

    private static StringBuilder inserting(StringBuilder answer, TagMap tags, String nowTag, StringBuilder parsingText, int nowTextPos, int endTextPos) {
        answer.append(parsingText.substring(nowTextPos, endTextPos));
        if (tags.getValue(nowTag) % 2 == 0) {
            answer.append(tags.openTag(nowTag));
        } else {
            answer.append(tags.closeTag(nowTag));
        }
        return answer;
    }

    static String parseInsideTags(StringBuilder parsingText, TagMap tags) {
        StringBuilder answer = new StringBuilder();
        int nowTextPos = 0;
        int len = parsingText.length();
        for (int i = 0; i < len; i++) {
            char nowChar = parsingText.charAt(i);
            switch (nowChar) {
                case '[': {
                    int openSquare = findSquare(parsingText, i); //где квадратная
                    int openCircle = findCircle(parsingText, openSquare); //где круглая скобка
                    String link = parsingText.substring(openSquare + 1, openCircle);
                    String inBrackets = parseInsideTags(new StringBuilder(parsingText.substring(i + 1, openSquare - 1)), tags);
                    answer.append(parsingText.substring(nowTextPos, i))
                            .append("<a href='").append(link).append("'>").append(inBrackets).append("</a>");
                    i = openCircle;
                    nowTextPos = i + 1;
                    break;
                }
                case '!': {
                    int openSquare = findSquare(parsingText, i + 1);
                    int openCircle = findCircle(parsingText, openSquare);
                    String link = parsingText.substring(openSquare + 1, openCircle);
                    String inBrackets = parsingText.substring(i + 2, openSquare - 1);
                    answer.append(parsingText.substring(nowTextPos, i))
                            .append("<img alt='").append(inBrackets).append("' src='").append(link).append("'>");
                    i = openCircle;
                    nowTextPos = i + 1;
                    break;
                }
                case '`':
                case '~':
                    answer = inserting(answer, tags, "" + nowChar, parsingText, nowTextPos, i);
                    nowTextPos = i + 1;
                    break;
                case '-':
                case '+':
                    if (i < len - 1 && parsingText.charAt(i + 1) == nowChar) {
                        answer = inserting(answer, tags, "" + nowChar + nowChar, parsingText, nowTextPos, i);
                        nowTextPos = i + 2;
                    }
                    break;
                case '*':
                case '_':
                    if (tags.getValue("" + nowChar) % 2 == 0 && ((i < len - 1 && (parsingText.charAt(i + 1) == ' ' ||
                            parsingText.charAt(i + 1) == '\n' || parsingText.charAt(i + 1) == '<')) || (i == len - 1))) {
                        answer.append(parsingText.substring(nowTextPos, i + 1));
                        nowTextPos = i + 1;
                    } else if (i < len - 1 && parsingText.charAt(i + 1) == nowChar) {
                        String nowTag = new String(new char[]{nowChar, nowChar});
                        answer = inserting(answer, tags, nowTag, parsingText, nowTextPos, i);
                        nowTextPos = i + 2;
                    } else if (i > 0 && parsingText.charAt(i - 1) != nowChar) {
                        if (parsingText.charAt(i - 1) != '\\') {
                            answer = inserting(answer, tags, "" + nowChar, parsingText, nowTextPos, i);
                            nowTextPos = i + 1;
                        }
                    }
                    break;
                case '\\':
                    answer.append(parsingText.substring(nowTextPos, i));
                    nowTextPos = i + 1;
                    break;
            }
        }
        if (nowTextPos < parsingText.length())
            answer.append(parsingText.substring(nowTextPos, parsingText.length()));
        return answer.toString();
    }

    private static int findCircle(StringBuilder parsingText, int openCircle) {
        int balanceCircle = 0;
        while (openCircle < parsingText.length()) {
            if (parsingText.charAt(openCircle) == '(') {
                balanceCircle++;
            } else if (parsingText.charAt(openCircle) == ')') {
                balanceCircle--;
            }
            if (balanceCircle == 0) {
                break;
            }
            openCircle++;
        }
        return openCircle;
    }

    private static int findSquare(StringBuilder parsingText, int openSquare) {
        int balanceSquare = 0;
        while (openSquare < parsingText.length()) {
            if (parsingText.charAt(openSquare) == '[') {
                balanceSquare++;
            } else if (parsingText.charAt(openSquare) == ']') {
                balanceSquare--;
            }
            if (balanceSquare == 0) {
                break;
            }
            openSquare++;
        }
        openSquare++;
        return openSquare;
    }
}