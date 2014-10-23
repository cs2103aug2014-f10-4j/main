package list;

import java.io.IOException;

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
	private static final String MESSAGE_ERROR_SAVING_DATA = null;
	
	private static final DisplayMode DEFAULT_DISPLAY_MODE = DisplayMode.ALL;
    
	private static IUserInterface userInterface = UserInterface.getInstance();
	private static IParser parser = new FlexiCommandParser();
	private static TaskManager taskManager = TaskManager.getInstance();
	private static DisplayMode displayMode = DisplayMode.ALL;
	
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
        	reply = MESSAGE_ERROR_SAVING_DATA;
        } catch (Exception e) {
            reply = MESSAGE_UNKNOWN_ERROR;
            e.printStackTrace();
        }
		return reply;
	}

	//UI FUNCTIONS
    public static void setDisplayMode(DisplayMode displayMode) {
        Controller.displayMode = displayMode; 
    }
    
	public static void displayTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
	}
	
	public static void refreshUi() {
	    switch (Controller.displayMode) {
	    case ALL:
	        userInterface.display(TITLE_ALL_TASKS, taskManager.getAllTasks());
	        break;
	    case TODAY:
	        userInterface.display(TITLE_TODAY_TASKS, taskManager.getTodayTasks());
	        break;
	    case FLOATING:
	        userInterface.display(TITLE_FLOATING_TASKS, taskManager.getFloatingTasks());
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
