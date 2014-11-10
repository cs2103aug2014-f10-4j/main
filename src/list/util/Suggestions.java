//@author A0113672L
package list.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Suggestions {
    public static final HashMap<String, List<String>> PARSER_SUGGESTIONS;
    static {
        PARSER_SUGGESTIONS = new HashMap<String, List<String>>();
        
        PARSER_SUGGESTIONS.put("", Arrays.asList("help", "cat", "add, edit, display, delete, search, mark, unmark, close"));
        
        PARSER_SUGGESTIONS.put("cat", Arrays.asList("add", "edit", "display", "delete"));
        PARSER_SUGGESTIONS.put("cat add", Arrays.asList("catargs()"));
        PARSER_SUGGESTIONS.put("cat edit", Arrays.asList("category name"));
        PARSER_SUGGESTIONS.put("cat display", Arrays.asList("category name"));
        PARSER_SUGGESTIONS.put("cat delete", Arrays.asList("category name"));
        PARSER_SUGGESTIONS.put("cat edit ga()", Arrays.asList("catargs()"));
        
        PARSER_SUGGESTIONS.put("search", Arrays.asList("keyword"));
        
        PARSER_SUGGESTIONS.put("mark", Arrays.asList("task number"));
        PARSER_SUGGESTIONS.put("unmark", Arrays.asList("task number"));
        
        PARSER_SUGGESTIONS.put("delete", Arrays.asList("task number", "cat"));
        PARSER_SUGGESTIONS.put("delete cat", Arrays.asList("category name"));
        
        PARSER_SUGGESTIONS.put("display", Arrays.asList("task number", "cat"));
        PARSER_SUGGESTIONS.put("display cat", Arrays.asList("category name"));
        
        PARSER_SUGGESTIONS.put("add", Arrays.asList("cat", "taskargs()"));
        PARSER_SUGGESTIONS.put("add cat", Arrays.asList("catargs()"));
        PARSER_SUGGESTIONS.put("add ga()", Arrays.asList("taskargs()"));
        
        PARSER_SUGGESTIONS.put("edit", Arrays.asList("task number", "cat"));
        PARSER_SUGGESTIONS.put("edit num()", Arrays.asList("taskargs()"));
        PARSER_SUGGESTIONS.put("edit cat", Arrays.asList("category name"));
        PARSER_SUGGESTIONS.put("edit cat ga()", Arrays.asList("catargs()"));
         
        
    }
    
}
