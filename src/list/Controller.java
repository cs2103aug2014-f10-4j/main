package list;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.List;
import java.util.Stack;

import org.json.JSONException;

import list.ICommand.CommandExecutionException;
import list.IParser.ParseException;

public class Controller {
    private static final String TITLE_FLOATING_TASKS = "Floating Tasks";
    private static final String TITLE_TODAY_TASKS = "Today's Tasks";
    private static final String TITLE_ALL_TASKS = "All Tasks";

    public static enum DisplayMode {
        TODAY, FLOATING, ALL, CUSTOM
    }
    
	private static final String MESSAGE_UNKNOWN_ERROR = "Unknown error!";
    private static final String MESSAGE_ERROR_PARSING_COMMAND = "Error parsing command.";
    private static final String MESSAGE_ERROR_LOADING = "Error loading data";
	private static final String MESSAGE_ERROR_INVALID_JSON_FORMAT = "Data is not in a valid JSON format ." + 
																	"Please ensure the JSON format is " + 
																	"valid and relaunch the program.";
	private static final String MESSAGE_ERROR_SAVING = "Error saving data";
	
	private static final DisplayMode DEFAULT_DISPLAY_MODE = DisplayMode.ALL;
    
	private static IUserInterface userInterface = UserInterface.getInstance();
	private static IParser parser = new FlexiCommandParser();
	private static TaskManager taskManager = TaskManager.getInstance();
	private static DisplayMode displayMode = DEFAULT_DISPLAY_MODE;
	private static List<ITask> displayedTasks = null;
	private static ITask displayedTaskDetail = null;
	
	private static Stack<ICommand> undoStack = new Stack<ICommand>();
	private static Stack<ICommand> redoStack = new Stack<ICommand>();
	private static boolean isUndoRedoOperation = false;
	
	public static void main(String[] args) {
		loadInitialData();
		userInterface.prepareForUserInput();
	}
	
	public static String processUserInput(String userInput) {
	    String reply;
        try {
            isUndoRedoOperation = false;
            ICommand commandMadeByParser = parser.parse(userInput);
            reply = commandMadeByParser.execute();
            ICommand inverseCommand = commandMadeByParser.getInverseCommand();
            if (inverseCommand != null) {
                undoStack.add(inverseCommand);
            }
            if (!isUndoRedoOperation) {
                redoStack.clear();
            }
        } catch (ParseException e) {
            reply = MESSAGE_ERROR_PARSING_COMMAND;
        } catch (CommandExecutionException e) {
            reply = e.getMessage();
        } catch (IOException e) {
        	reply = MESSAGE_ERROR_SAVING;
        } catch (Exception e) {
            reply = MESSAGE_UNKNOWN_ERROR;
            e.printStackTrace();
        }
		return reply;
	}

	public static ITask getTaskWithNumber(int taskNumber) {
	    int taskId = taskNumber - 1;
		return displayedTasks.get(taskId);
	}
	
	public static boolean hasTaskWithNumber(int taskNumber) {
		return (taskNumber > 0 && taskNumber <= displayedTasks.size());		
	}
	
	public static ITask getLastDisplayedTaskDetail() {
	    return displayedTaskDetail;
	}
	
	//UI FUNCTIONS
    public static void setDisplayMode(DisplayMode displayMode) {
        Controller.displayMode = displayMode; 
    }
    
	public static void displayTaskDetail(ITask selectedTask) {
	    Controller.displayedTaskDetail = selectedTask;
		userInterface.displayTaskDetail(selectedTask);
	}
	
	public static void refreshUi() {
		List<ITask> tasksToDisplay = null;
	    switch (Controller.displayMode) {
		    case ALL:
		    	tasksToDisplay = taskManager.getAllTasks();
		        userInterface.display(TITLE_ALL_TASKS, tasksToDisplay);
		        break;
		    case TODAY:
		    	tasksToDisplay = taskManager.getTodayTasks();
		        userInterface.display(TITLE_TODAY_TASKS, tasksToDisplay);
		        break;
		    case FLOATING:
		    	tasksToDisplay = taskManager.getFloatingTasks();
		        userInterface.display(TITLE_FLOATING_TASKS, tasksToDisplay);
		        break;
		    case CUSTOM:
		        //do something, or possibly do nothing
	    }
	    Controller.displayedTasks = tasksToDisplay;
	}

	public static void updateUiWithTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
	}
	
	static Stack<ICommand> getUndoStack() {
	    return undoStack;
	}
	
	static Stack<ICommand> getRedoStack() {
	    return redoStack;
	}
	
	static void reportUndoRedoOperation() {
	    isUndoRedoOperation = true;
	}
	
	//TODO: Error with UI when loading
	public static void loadInitialData() {
		try {
			taskManager.loadTasks();
			refreshUi();
		} catch (IOException e) {
			userInterface.displayMessageToUser(MESSAGE_ERROR_LOADING);
		} catch (JSONException e) {
			userInterface.displayMessageToUser(MESSAGE_ERROR_INVALID_JSON_FORMAT);
			System.exit(1);
		}
	}

}
