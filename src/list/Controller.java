package list;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.List;

import org.json.JSONException;

import list.ICommand.CommandExecutionException;
import list.ICommand.InvalidTaskNumberException;
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
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Task number entered is invalid.";
    private static final String MESSAGE_ERROR_LOADING ="Error loading data";
	private static final String MESSAGE_ERROR_INVALID_JSON_FORMAT = "Data is not in a valid JSON format ." + 
																	"Please ensure the JSON format is " + 
																	"valid and relaunch the program.";
	private static final String MESSAGE_ERROR_SAVING = "Error saving data";
	
	private static final DisplayMode DEFAULT_DISPLAY_MODE = DisplayMode.ALL;
    
	private static IUserInterface userInterface = UserInterface.getInstance();
	private static IParser parser = new FlexiCommandParser();
	private static TaskManager taskManager = TaskManager.getInstance();
	private static DisplayMode displayMode = DisplayMode.ALL;
	private static List<ITask> displayedTasks = null;
	
	public static void main(String[] args) {
		loadInitialData();
		userInterface.prepareForUserInput();
	}
	
	public static String processUserInput(String userInput) {
	    ICommand commandMadeByParser;
	    String reply;
        try {
            commandMadeByParser = parser.parse(userInput);
            reply = commandMadeByParser.execute();
        } catch (ParseException e) {
            reply = MESSAGE_ERROR_PARSING_COMMAND;
        } catch (InvalidTaskNumberException e) {
            reply = MESSAGE_INVALID_TASK_NUMBER;
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

	public static ITask getTask(int taskNumber) {
		return displayedTasks.get(taskNumber);
	}
	
	public static boolean hasTaskWithTaskNumber(int taskNumber) {
		return (taskNumber >= 0 && taskNumber < displayedTasks.size());		
	}
	
	//UI FUNCTIONS
    public static void setDisplayMode(DisplayMode displayMode) {
        Controller.displayMode = displayMode; 
    }
    
	public static void displayTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
	}
	
	public static void refreshUi() {
		List<ITask> tasksToDisplay = null;
	    switch (Controller.displayMode) {
		    case ALL:
		    	tasksToDisplay = taskManager.getAllTasks();
		        userInterface.display(TITLE_ALL_TASKS, tasksToDisplay);
		        displayedTasks = tasksToDisplay;
		        break;
		    case TODAY:
		    	tasksToDisplay = taskManager.getTodayTasks();
		        userInterface.display(TITLE_TODAY_TASKS, tasksToDisplay);
		        displayedTasks = tasksToDisplay;
		        break;
		    case FLOATING:
		    	tasksToDisplay = taskManager.getFloatingTasks();
		        userInterface.display(TITLE_FLOATING_TASKS, tasksToDisplay);
		        displayedTasks = tasksToDisplay;
		        break;
		    case CUSTOM:
		        //do something, or possibly do nothing
	    }
	}

	public static void updateUiWithTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
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
