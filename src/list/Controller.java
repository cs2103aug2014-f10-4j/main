package list;

import list.ICommand.InvalidTaskNumberException;
import list.IParser.ParseException;
import list.IUserInterface.DisplayFullException;

public class Controller {
		
	private static final String MESSAGE_INVALID_TASK_NUMBER = "Task number entered is invalid.";
	
	private static IUserInterface mUserInterface = new UserInterface();
	private static IParser mParser = new Parser();
	
	public static void main(String[] args) {
		mUserInterface = new UserInterface();
	}
	
	public static String processUserInput(String userInput) {
	    ICommand commandMadeByParser;
	    String reply;
        try {
            commandMadeByParser = mParser.parse(userInput);
            reply = commandMadeByParser.execute();
        } catch (ParseException e) {
            reply = "Error parsing command.";
        } catch (InvalidTaskNumberException e) {
            reply = "Invalid task number!";
        } catch (Exception e) {
            reply = "Error!";
            e.printStackTrace();
        }
		return reply;
	}
	
	public static void updateListOfTasksInUI() {
		int taskNumberToDisplay = 1;
		mUserInterface.clearAll();
		
		//Naming of boolean condition OK?
		try {
		    while (UICanDisplayMoreTask() && 
		               TaskManager.isValidTaskNumber(taskNumberToDisplay)) {
	            ITask taskToDisplay = TaskManager.getTask(taskNumberToDisplay);         
	            mUserInterface.displayNewTaskOrDate(taskToDisplay);
	            taskNumberToDisplay++;
		    }
		} catch (DisplayFullException e) {
		    e.printStackTrace();
		    //do nothing
		}
		
	}

	public static void updateUIWithTaskDetail(ITask selectedTask) {
		mUserInterface.displayTaskDetail(selectedTask);
	}
	
	private static boolean UICanDisplayMoreTask() {
		return !mUserInterface.isFull();
	}
}
