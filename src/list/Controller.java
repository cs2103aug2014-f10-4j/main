package list;

import java.io.IOException;

import org.json.JSONException;

import list.ICommand.CommandExecutionException;
import list.ICommand.InvalidTaskNumberException;
import list.IParser.ParseException;

public class Controller {
		
	private static final String MESSAGE_UNKNOWN_ERROR = "Unknown error!";
    private static final String MESSAGE_ERROR_PARSING_COMMAND = "Error parsing command.";
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Task number entered is invalid.";
    private static final String MESSAGE_ERROR_LOADING ="Error loading data";
	private static final String MESSAGE_ERROR_INVALID_JSON_FORMAT = "Data is not in a valid JSON format ." + 
																	"Please ensure the JSON format is " + 
																	"valid and relaunch the program.";
	private static final String MESSAGE_ERROR_SAVING_DATA = null;
	
	private static IUserInterface userInterface = UserInterface.getInstance();
	private static IParser parser = new Parser();
	private static TaskManager taskManager = TaskManager.getInstance();
	
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
	
	public static void updateListOfTasksInUi() {
	    int taskNumberToDisplay = 1;
	    userInterface.clearAll();
	    while (!userInterface.isFull() && 
	            taskManager.hasTaskWithNumber(taskNumberToDisplay)) {
	        ITask taskToDisplay = taskManager.getTask(taskNumberToDisplay);         
	        userInterface.displayNewTaskOrDate(taskToDisplay);
	        taskNumberToDisplay++;
	    }
	}

	public static void updateUiWithTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
	}
	
	//TODO: Error with UI when loading
	public static void loadInitialData() {
		try {
			taskManager.loadTasks();
			updateListOfTasksInUi();
		} catch (IOException e) {
			userInterface.displayMessageToUser(MESSAGE_ERROR_LOADING);
		} catch (JSONException e) {
			userInterface.displayMessageToUser(MESSAGE_ERROR_INVALID_JSON_FORMAT);
			System.exit(1);
		}
	}

}
