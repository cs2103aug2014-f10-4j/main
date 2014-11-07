//@author A0113672L unused
package list;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import list.CommandBuilder.CommandType;
import list.CommandBuilder.CommandTypeNotSetException;
import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.Date.InvalidDateException;
import list.model.ICategory;

/**
 * A simple implementation of IParser using Regex. 
 * Supports LIST Syntax as specified in the README - Syntax and user guide.
 * No longer used because it is substituted by the new CommandParser.
 * @author andhieka
 *
 */
public class Parser implements IParser {
    private static final int MINIMUM_TASK_NUMBER = 1;

    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private final TaskManager taskManager = TaskManager.getInstance();
    
    //Regular Expression Patterns
    //Note: backslash must be escaped in Java
    private final Pattern REGEX_COMMAND_TYPE = Pattern.compile("^\\w+");
    private final Pattern REGEX_TITLE = Pattern.compile("(?<=\\B-t\\s|\\B--title\\s)[^-]+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_START_TIME = Pattern.compile("(?<=\\B-s\\s|\\B--start\\s)\\d{2}-\\d{2}-\\d{4}(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_END_TIME = Pattern.compile("(?<=\\B-d\\s|\\B--deadline\\s)\\d{2}-\\d{2}-\\d{4}(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_NOTES = Pattern.compile("(?<=\\B-n\\s|\\B--notes\\s)[^-]+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_PLACE = Pattern.compile("(?<=\\B-p\\s|\\B--place\\s)[^-]+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_CATEGORY = Pattern.compile("(?<=\\B-c\\s|\\B--category\\s)[^-]+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_REPEAT = Pattern.compile("(?<=\\B-r\\s|\\B--repeat\\s)[^-]+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_TASK_NUMBER_EDIT = Pattern.compile("(?<=^edit\\s)\\d+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_TASK_NUMBER_DELETE = Pattern.compile("(?<=^delete\\s)\\d+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_TASK_NUMBER_DISPLAY = Pattern.compile("(?<=^display\\s)\\d+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_TASK_NUMBER_MARK = Pattern.compile("(?<=^mark\\s)\\d+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    private final Pattern REGEX_TASK_NUMBER_UNMARK = Pattern.compile("(?<=^unmark\\s)\\d+(?=$|[\\s-])", Pattern.CASE_INSENSITIVE);
    
    @Override
    public ICommand parse(String input) throws ParseException {
        try {
            CommandBuilder commandBuilder = new CommandBuilder();
            commandBuilder.setCommandType(getCommandType(input));
            commandBuilder.setTitle(getTitle(input));
            commandBuilder.setStartDate(getStartDate(input));
            commandBuilder.setEndDate(getEndDate(input));
            commandBuilder.setNotes(getNotes(input));
            commandBuilder.setPlace(getPlace(input));
            commandBuilder.setCategory(getCategory(input));
            commandBuilder.setRepeatFrequency(getRepeatFrequency(input));
            commandBuilder.setObjectNumber(getTaskNumber(input));
            
            ICommand command = getCommandObject(commandBuilder);
            return command;
        } catch (InvalidDateException e) {
            e.printStackTrace();
            throw new ParseException("Error parsing dates!");
        }
    }
    
    /**
     * Get a command object from the given commandBuilder object.
     * @param commandBuilder a command builder
     * @return an instance of class implementing ICommand 
     */
    private ICommand getCommandObject(CommandBuilder commandBuilder) {
        ICommand command = null;
        try {
            command = commandBuilder.getCommandObject();
        } catch (CommandTypeNotSetException e) {
            assert(false); //should not be called at all
            e.printStackTrace();
        }
        return command;
    }

    private Integer getTaskNumber(String input) throws ParseException {
        CommandType commandType = getCommandType(input);
        String match = null;
        switch (commandType) {
        case EDIT:
            match = findFirstMatch(REGEX_TASK_NUMBER_EDIT, input);
            break;
        case DELETE:
            match = findFirstMatch(REGEX_TASK_NUMBER_DELETE, input);
            break;
        case DISPLAY:
            match = findFirstMatch(REGEX_TASK_NUMBER_DISPLAY, input);
            break;
        case MARK:
            match = findFirstMatch(REGEX_TASK_NUMBER_MARK, input);
            break;
        case UNMARK:
            match = findFirstMatch(REGEX_TASK_NUMBER_UNMARK, input);
            break;
        default:
            match = null;    
        }
        if (match == null) {
            return null;
        } else {
            Integer taskNumber = Integer.parseInt(match);
            if (taskNumber < MINIMUM_TASK_NUMBER) {
                throw new ParseException("Task number cannot be negative.");
            }
            return taskNumber;
        }
    }
    
    /**
     * Checks for a category argument (-c) inside a string.
     * 
     * Warning: returns null if no category argument is found.
     * @param input the input typed by the user into the console.
     * @return the category argument
     */
    private ICategory getCategory(String input) {
        String categoryName = findFirstMatch(REGEX_CATEGORY, input);
        
        ICategory category = taskManager.getCategory(categoryName);
        return category;
    }
    
    /**
     * Checks for a place argument (-p) inside a string.
     * 
     * Warning: returns null if no place argument is found.
     * @param input the input typed by the user into the console.
     * @return the place argument
     */
    private String getPlace(String input) {
        String place = findFirstMatch(REGEX_PLACE, input);
        return place;
    }
    
    /**
     * Checks for a notes argument (-n) inside a string.
     * 
     * Warning: returns null if no notes argument is found.
     * @param input the input typed by the user into the console.
     * @return the notes argument
     */
    private String getNotes(String input) {
        String notes = findFirstMatch(REGEX_NOTES, input);
        return notes;
    }

    /**
     * Checks the command type of a given input string.
     * This function will not return null, but will throw a ParseException
     * if no valid command type is found in the string.
     * @param input the input typed by the user
     * @return the command type of the input
     * @throws ParseException when no valid command type can be inferred from the input
     */
    private CommandType getCommandType(String input) throws ParseException {
        String match = findFirstMatch(REGEX_COMMAND_TYPE, input);
        if (match != null) {
            try {
                CommandType commandType = CommandType.valueOf(match.toUpperCase());
                return commandType; 
            } catch (IllegalArgumentException e) {
                throw new ParseException("Invalid command type.");
            }
        } else {
            throw new ParseException("No command type in specified input.");
        }
    }
    
    private RepeatFrequency getRepeatFrequency(String input) throws ParseException {
        String match = findFirstMatch(REGEX_REPEAT, input);
        if (match != null) {
            try {
                RepeatFrequency repeatFrequency = RepeatFrequency.valueOf(match.toUpperCase());
                return repeatFrequency; 
            } catch (IllegalArgumentException e) {
                throw new ParseException("Invalid repeat frequency.");
            }
        } else {
            return null;
        }
    }
    
    /**
     * Checks for a title argument (-t) inside a string.
     * This function allows spaces and word characters (A-Za-z_) in the title.
     * 
     * Warning: returns null if no title argument is found.
     * @param input the input typed by the user into the console.
     * @return the title argument
     */
    private String getTitle(String input) {
        String title = findFirstMatch(REGEX_TITLE, input);
        return title;
    }
    
    /**
     * Checks for a start time argument (-s) inside a string.
     * This function requires the date to be formatted in "dd-mm-yyyy" format.
     * 
     * Warning: returns null if no start time argument is found.
     * @param input the input typed by the user into the console.
     * @return the start time argument
     */
    private Date getStartDate(String input) throws InvalidDateException {
        String startDate = findFirstMatch(REGEX_START_TIME, input);
        if (startDate == null) {
            return null;
        }
        String[] startDateSplit = startDate.split("-");
        if (startDateSplit.length == 3) {
            return getDateInstance(Integer.parseInt(startDateSplit[0]),
                    Integer.parseInt(startDateSplit[1]),
                    Integer.parseInt(startDateSplit[2]));
        } else {
            assert(false); //should never be called. format has been enforced by Regex
            return null;
        }
    }
    
    /**
     * Checks for an end time argument (-d) inside a string.
     * This function requires the date to be formatted in "dd-mm-yyyy" format.
     * 
     * Warning: returns null if no end time argument is found.
     * @param input the input typed by the user into the console.
     * @return the end time argument
     */
    private Date getEndDate(String input) throws InvalidDateException {
        String startDate = findFirstMatch(REGEX_END_TIME, input);
        if (startDate == null) {
            return null;
        }
        String[] startDateSplit = startDate.split("-");
        if (startDateSplit.length == 3) {
            return getDateInstance(Integer.parseInt(startDateSplit[0]),
                    Integer.parseInt(startDateSplit[1]),
                    Integer.parseInt(startDateSplit[2]));
        } else {
            assert(false); //should never be called. format has been enforced by Regex
            return null;
        }
    }
    
    /**
     * Returns the first match of a given Regex pattern in the specified input string.
     * Warning: returns null if no match is found.
     * @param pattern the Regex pattern object
     * @param input the input from the user
     * @return the first match of Regex <code>pattern</code> in the given input
     */
    private String findFirstMatch(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String match = matcher.group();
            return match.trim();
        } else {
            return null;
        }
    }
    
    /**
     * Utility function to create a list.Date object with only date, month and year specified.
     * @param date int
     * @param month int
     * @param year int
     * @return an instance of Date (LIST's own Date class)
     */
    private Date getDateInstance(int date, int month, int year) throws InvalidDateException {
        return new Date(date, month, year);
    }
    
}
