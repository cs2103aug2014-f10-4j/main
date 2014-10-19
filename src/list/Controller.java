package list;

import list.ICommand.CommandExecutionException;
import list.ICommand.InvalidTaskNumberException;
import list.IParser.ParseException;

public class Controller {
		
	private static final String MESSAGE_ERROR_PARSING_COMMAND = "Error parsing command.";
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Task number entered is invalid.";
	
	private static IUserInterface userInterface = new UserInterface();
	private static IParser parser = new Parser();
	private static TaskManager taskManager = TaskManager.getInstance();
	
	public static void main(String[] args) {
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
        } catch (Exception e) {
            reply = "Unknown error!";
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

}
