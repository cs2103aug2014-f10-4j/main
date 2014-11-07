package list;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Color;

import list.CommandBuilder.CommandType;
import list.CommandBuilder.CommandTypeNotSetException;
import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ICategory;

public class CommandParser implements IParser {
    
    private static final String ERROR_END_DATE_BEFORE_START_DATE = "End date cannot be earlier than start date.";
    private static final String ERROR_COMMAND_TYPE_NOT_SPECIFIED = "Error: command type is not specified";
    private static final String ERROR_CANNOT_PARSE_START_DATE = "Error: unable to parse start date.";
    private static final String ERROR_END_DATE_NOT_SPECIFIED = "Error: if start date is specified, end date must also be specified.";
    private static final String ERROR_CANNOT_PARSE_END_DATE = "Error: unable to parse end date.";
    private static final String ERROR_AMBIGUOUS_COMMAND_TYPE = "Error: ambiguous command type.";
    private static final String ERROR_PARAMETER_CONFLICT = "The parameter %s is specified multiple times.";
    
    private static final String MARKER_COLOR = "-c";
    private static final String MARKER_REPEAT = "-r";
    private static final String MARKER_CATEGORY = "-c";
    private static final String MARKER_PLACE = "-p";
    private static final String MARKER_NOTES = "-n";
    private static final String MARKER_END_DATE = "-d";
    private static final String MARKER_START_DATE = "-s";
    private static final String MARKER_TITLE = "-t";
    
    private static final String REGEX_SPLITTER = "\\s+";

    private static enum ParseMode {
        TASK, CATEGORY
    }
    private static final Map<String, String> EXPECTATIONS_TASK;
    static {
        EXPECTATIONS_TASK = new HashMap<String, String>();
        EXPECTATIONS_TASK.put(MARKER_TITLE, "Title");
        EXPECTATIONS_TASK.put(MARKER_CATEGORY, "Category");
        EXPECTATIONS_TASK.put(MARKER_END_DATE, "Deadline");
        EXPECTATIONS_TASK.put(MARKER_START_DATE, "Start date");
        EXPECTATIONS_TASK.put(MARKER_PLACE, "Place");
        EXPECTATIONS_TASK.put(MARKER_NOTES, "Notes");
        EXPECTATIONS_TASK.put(MARKER_REPEAT, "Repeat");
    }
    private static final Map<String, String> EXPECTATIONS_CATEGORY;
    static {
        EXPECTATIONS_CATEGORY = new HashMap<String, String>();
        EXPECTATIONS_CATEGORY.put(MARKER_COLOR, "Color");
        EXPECTATIONS_CATEGORY.put(MARKER_TITLE, "Title");
    }
    private static final List<String> KEYWORDS_CATEGORY = Arrays.asList(
        "cat", "category"
    );
    private static final Map<String, String> EXPECTATIONS_ACTION;
    static {
        EXPECTATIONS_ACTION = new HashMap<String, String>();
        EXPECTATIONS_ACTION.put("add", "Add a new task");
        EXPECTATIONS_ACTION.put("edit", "Edit an existing task");
        EXPECTATIONS_ACTION.put("delete", "Delete an existing task");
        EXPECTATIONS_ACTION.put("display", "Display the details of an existing task.");
        EXPECTATIONS_ACTION.put("display cat", "Show categories.");
        EXPECTATIONS_ACTION.put("mark", "Mark a task as done");
        EXPECTATIONS_ACTION.put("unmark", "Mark a task as not done.");
        EXPECTATIONS_ACTION.put("undo", "Undo your last command (multiple undo supported)");
        EXPECTATIONS_ACTION.put("redo", "Redo action");
        EXPECTATIONS_ACTION.put("prev", "Go to previous page");
        EXPECTATIONS_ACTION.put("next", "Go to next page");
        EXPECTATIONS_ACTION.put("cat add", "Add a new category");
        EXPECTATIONS_ACTION.put("cat edit", "Edit an existing category");
        EXPECTATIONS_ACTION.put("cat delete", "Delete an existing category");
        EXPECTATIONS_ACTION.put("close", "Exit the application");
    }
    private static final Map<String, String> EXPECTATIONS_NUMBER;
    static {
        EXPECTATIONS_NUMBER = new HashMap<String, String>();
        EXPECTATIONS_NUMBER.put("Number", "The object number you want to select.");
    }
    private static final List<String> ACTIONS_REQUIRING_NUMBER = Arrays.asList(
            "edit", "delete", "display",
            "mark", "unmark"
    );
    
    //remember to reset these variables at clear()
    private ParseMode parseMode;
    private String currentMarker;
    private StringBuilder currentParameterValue;
    private Map<String, StringBuilder> parameters;
    private Integer taskNumber;
    private String action;
    private Date startDate;
    private Date endDate;
    private String selectedCategory;
    
    private TaskManager taskManager = TaskManager.getInstance();
    
    public CommandParser() {
        this.clear();
    }
    
    @Override
    public ICommand parse(String input) throws ParseException {
        this.clear();
        this.append(input);
        return this.finish();        
    }
    
    public void append(String input) throws ParseException {
        String[] words = input.split(REGEX_SPLITTER);
        for (String word: words) {
            processWord(word);
        }
    }
    
    public Map<String, String> getExpectedInputs() {
        if (this.action.isEmpty()) {
            return EXPECTATIONS_ACTION;
        }
        if (this.requiresObjectNumber() && this.taskNumber == 0) {
            return EXPECTATIONS_NUMBER;
        }
        if (this.parseMode == ParseMode.TASK) {
            return unspecifiedTaskParameters();
        } else {
            return unspecifiedCategoryParameters();
        }
    }

    private Map<String, String> unspecifiedCategoryParameters() {
        HashMap<String, String> expectations = new HashMap<String, String>();
        for(Entry<String, String> entry: EXPECTATIONS_CATEGORY.entrySet()) {
            if (parameterNotSpecified(entry.getKey())) {
                expectations.put(entry.getKey(), entry.getValue());
            }
        }
        return expectations;
    }

    private boolean parameterNotSpecified(String parameterName) {
        return !this.parameters.containsKey(parameterName);
    }

    private Map<String, String> unspecifiedTaskParameters() {
        HashMap<String, String> expectations = new HashMap<String, String>();
        for(Entry<String, String> entry: EXPECTATIONS_TASK.entrySet()) {
            if (parameterNotSpecified(entry.getKey())) {
                expectations.put(entry.getKey(), entry.getValue());
            }
        }
        return expectations;
    }

    private boolean requiresObjectNumber() {
        return ACTIONS_REQUIRING_NUMBER.contains(this.action);
    }
    
    public void clear() {
        this.parseMode = ParseMode.TASK;
        this.parameters = new HashMap<String, StringBuilder>();
        this.currentMarker = "";
        this.currentParameterValue = null;
        this.taskNumber = 0;
        this.action = "";
        this.startDate = null;
        this.endDate = null;
        this.selectedCategory = null;
    }
 
    public ICommand finish() throws ParseException {
        CommandBuilder commandBuilder = new CommandBuilder();
        
        throwErrorIfActionIsEmpty();
        updateActionName();
        setCommandType(commandBuilder);
        setTaskNumber(commandBuilder);
        setDetails(commandBuilder);
        
        ICommand result = getCommandObject(commandBuilder);
        //if there is no exception until now, parsing is successful
        this.clear(); //and we should clear the parser
        
        return result;
    }

    private void setDetails(CommandBuilder commandBuilder)
            throws ParseException {
        switch (this.parseMode) {
        case TASK:
            fillTaskParameters(commandBuilder);
            break;
        case CATEGORY:
            fillCategoryParameters(commandBuilder);
        }
    }

    private void setTaskNumber(CommandBuilder commandBuilder) {
        //set task number
        if (this.taskNumber != 0) {
            commandBuilder.setObjectNumber(this.taskNumber);
        }
    }

    private void setCommandType(CommandBuilder commandBuilder) throws ParseException {
        try {
            commandBuilder.setCommandType(CommandType.valueOf(this.action.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ParseException(ERROR_AMBIGUOUS_COMMAND_TYPE);
        }
    }

    private void updateActionName() {
        if (this.parseMode == ParseMode.CATEGORY) {
            this.action = "CATEGORY_" + this.action.trim();
        }
    }

    private void throwErrorIfActionIsEmpty() throws ParseException {
        if (this.action.isEmpty()) {
            throw new ParseException("Error: action is not specified.");
        }
    }
    
    /**
     * Get a command object from the given commandBuilder object.
     * @param commandBuilder a command builder
     * @return an instance of class implementing ICommand 
     */
    private ICommand getCommandObject(CommandBuilder commandBuilder) throws ParseException {
        try {
            ICommand command = commandBuilder.getCommandObject();
            return command;
        } catch (CommandTypeNotSetException e) {
            throw new ParseException(ERROR_COMMAND_TYPE_NOT_SPECIFIED);
        }
    } 
    
    private void fillCategoryParameters(CommandBuilder commandBuilder) throws ParseException {
        setSelectedCategory(commandBuilder);
        setTitle(commandBuilder);
        setColor(commandBuilder);
    }

    private void setSelectedCategory(CommandBuilder commandBuilder) {
        if (this.selectedCategory != null) {
            commandBuilder.setSelectedCategory(selectedCategory);
        }
    }

    private void setColor(CommandBuilder commandBuilder) throws ParseException {
        if (this.parameters.containsKey(MARKER_COLOR)) {
            String str = this.parameters.get(MARKER_COLOR).toString().trim();
            if (str.indexOf('#') == 0) {
                setColorFromHex(commandBuilder, str);
            } else {
                setColorFromName(commandBuilder, str);
            }
        }
    }

    private void setColorFromName(CommandBuilder commandBuilder, String str)
            throws ParseException {
        try {
            final Field field = Color.class.getField(str.toUpperCase());
            Color color = (Color) field.get(null);
            commandBuilder.setColor(color);
        } catch (Exception e) {
            throw new ParseException("Error: invalid color name.");
        }
    }

    private void setColorFromHex(CommandBuilder commandBuilder, String hexcode)
            throws ParseException {
        try {
            Integer hex = Integer.parseInt(hexcode.substring(1), 16);
            Color color = new Color(hex);
            commandBuilder.setColor(color);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid hex color format");
        }
    }
    
    private void fillTaskParameters(CommandBuilder commandBuilder) throws ParseException {
        setTitle(commandBuilder);
        setStartDate(commandBuilder);
        setEndDate(commandBuilder);
        setNotes(commandBuilder);
        setPlace(commandBuilder);
        setCategory(commandBuilder);
        setRepeatFrequency(commandBuilder);
        
        ensureEndDateIsNotEarlierThanStartDate();
    }

    private void ensureEndDateIsNotEarlierThanStartDate() throws ParseException {
        if (this.startDate == null || this.endDate == null) {
            return;
        }
        if (this.startDate.compareTo(this.endDate) > 0) {
            throw new ParseException(ERROR_END_DATE_BEFORE_START_DATE);
        }
    }

    private void setRepeatFrequency(CommandBuilder commandBuilder)
            throws ParseException {
        //repeat frequency
        if (this.parameters.containsKey(MARKER_REPEAT)) {
            String str = this.parameters.get(MARKER_REPEAT).toString();
            try {
                RepeatFrequency repeatFrequency = RepeatFrequency.valueOf(str.toUpperCase());
                commandBuilder.setRepeatFrequency(repeatFrequency);
            } catch (IllegalArgumentException e) {
                throw new ParseException("Error: invalid repeat frequency.");
            }
        }
    }

    private void setCategory(CommandBuilder commandBuilder) {
        //category
        if (this.parameters.containsKey(MARKER_CATEGORY)) {
            String categoryName = this.parameters.get(MARKER_CATEGORY).toString();
            ICategory category = taskManager.getCategory(categoryName);
            commandBuilder.setCategory(category);
        }
    }

    private void setPlace(CommandBuilder commandBuilder) {
        //place
        if (this.parameters.containsKey(MARKER_PLACE)) {
            String place = this.parameters.get(MARKER_PLACE).toString();
            commandBuilder.setPlace(place);
        }
    }

    private void setNotes(CommandBuilder commandBuilder) {
        //notes
        if (this.parameters.containsKey(MARKER_NOTES)) {
            String notes = this.parameters.get(MARKER_NOTES).toString();
            commandBuilder.setNotes(notes);
        }
    }

    private void setEndDate(CommandBuilder commandBuilder)
            throws ParseException {
        //end date
        if (this.parameters.containsKey(MARKER_END_DATE)) {
            String str = this.parameters.get(MARKER_END_DATE).toString();
            Date endDate;
            if (str.isEmpty()) {
                endDate = Date.getFloatingDate();
            } else {
                endDate = Date.tryParse(str);
            }
            if (endDate == null) {
                throw new ParseException(ERROR_CANNOT_PARSE_END_DATE);
            } else {
                commandBuilder.setEndDate(endDate);
                this.endDate = endDate;
                System.out.println(endDate);
            }
        }
    }

    private void setStartDate(CommandBuilder commandBuilder)
            throws ParseException {
        //start date
        if (this.parameters.containsKey(MARKER_START_DATE)) {
            String str = this.parameters.get(MARKER_START_DATE).toString();
            Date startDate;
            if (str.isEmpty()) {
                startDate = Date.getFloatingDate();
            } else {
                startDate = Date.tryParse(str);
            }
            if (startDate == null) {
                throw new ParseException(ERROR_CANNOT_PARSE_START_DATE);
            } else {
                commandBuilder.setStartDate(startDate);
                this.startDate = startDate;
            }
            //if there is start time, there must be end time
            if (!this.parameters.containsKey(MARKER_END_DATE) && this.action.equalsIgnoreCase("ADD")) {
                throw new ParseException(ERROR_END_DATE_NOT_SPECIFIED);
            }
        }
    }

    private void setTitle(CommandBuilder commandBuilder) {
        //set title
        if (this.parameters.containsKey(MARKER_TITLE)) {
            String title = this.parameters.get(MARKER_TITLE).toString();
            commandBuilder.setTitle(title);
        }
    }
    
    private void processWord(String word) throws ParseException {
        if (isParameterMarker(word)) {
            if (parameters.containsKey(word)) {
                throw new ParseException(String.format(ERROR_PARAMETER_CONFLICT, word));
            } else {
                this.currentMarker = word;
                this.currentParameterValue = new StringBuilder();
                this.parameters.put(word, this.currentParameterValue);
            }
        } else {
            if (this.currentMarker.isEmpty()) {
                processNonParameterWord(word);
            } else {
                if (!(this.currentParameterValue.length() == 0)) {
                    this.currentParameterValue.append(' ');
                }
                this.currentParameterValue.append(word);
            }
        }
    }
    
    private boolean isParameterMarker(String word) {
        return EXPECTATIONS_TASK.containsKey(word) ||
               EXPECTATIONS_CATEGORY.containsKey(word);
    }
    
    private void processNonParameterWord(String word) throws ParseException {
        if (KEYWORDS_CATEGORY.contains(word)) {
            this.parseMode = ParseMode.CATEGORY;
        } else if (isInteger(word)) {
            this.taskNumber = Integer.parseInt(word);
        } else if (parseMode == ParseMode.CATEGORY && !action.isEmpty()) {
            this.selectedCategory = word;
        } else {
            if (this.action.isEmpty()) {
                this.action = word;
            } else {
                throw new ParseException(ERROR_AMBIGUOUS_COMMAND_TYPE);
            }
        }
        
    }
    
    private boolean isInteger(String word) {
        try {
            Integer.parseInt(word);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
