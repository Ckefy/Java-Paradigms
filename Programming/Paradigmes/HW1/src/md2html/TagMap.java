package md2html;

import java.util.HashMap;

class TagMap {
    private HashMap<String, Integer> tags;

    TagMap() {
        tags = new HashMap<>();
        tags.put("*", 0); //выделение
        tags.put("**", 0); //сильное
        tags.put("_", 0);  //выделение
        tags.put("__", 0); //сильное
        tags.put("--", 0); //удаление
        tags.put("++", 0);
        tags.put("~", 0);
        tags.put("`", 0);
    }

    private void addValue(String tag) {
        int value = tags.get(tag);
        tags.replace(tag, ++value);
    }

    int getValue(String tag) {
        return tags.get(tag);
    }

    String openTag(String tag) {
        addValue(tag);
        if (tag.equals("--")) {
            return "<s>";
        }
        if (tag.equals("`")) {
            return "<code>";
        }
        if (tag.equals("*") || tag.equals("_")) {
            return "<em>";
        }
        if (tag.equals("~")){
            return "<mark>";
        }
        if (tag.equals("++")){
            return "<u>";
        }
        return "<strong>";
    }

    String closeTag(String tag) {
        addValue(tag);
        if (tag.equals("--")) {
            return "</s>";
        }
        if (tag.equals("`")) {
            return "</code>";
        }
        if (tag.equals("*") || tag.equals("_")) {
            return "</em>";
        }
        if (tag.equals("~")){
            return "</mark>";
        }
        if (tag.equals("++")){
            return "</u>";
        }
        return "</strong>";
    }
}
