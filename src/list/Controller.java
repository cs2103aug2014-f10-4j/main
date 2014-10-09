package list;

import list.ICommand.InvalidTaskNumberException;
import list.IParser.ParseException;

public class Controller {
		
	private static IUserInterface mUserInterface = null;
	private static IParser mParser = new Parser();
	
	public static void main(String[] args) {
		
		while (true) {
			try {
				String userInput = receiveUserInputFromUI(mUserInterface.getUserInput());
				ICommand commandMadeByParser = mParser.parse(userInput);
				commandMadeByParser.execute();
				
			} catch (ParseException e) {
				
			} catch (InvalidTaskNumberException e) {
				
			}
		}
	}
	
	public static String receiveUserInputFromUI(String userInput) {
		return userInput;
	}
	
	public static void updateListOfTasksInUI() {
		int taskNumberToDisplay = 1;
		
		//Naming of boolean condition OK?
		while (UICanDisplayMoreTask() && 
			   TaskManager.isValid(taskNumberToDisplay)) {
			ITask taskToDisplay = TaskManager.getTask(taskNumberToDisplay);			
			mUserInterface.displayMoreTask(taskToDisplay);
		}
		
	}

	public static void updateUIWithTaskDetail(ITask selectedTask) {
		mUserInterface.displayTaskDetail(selectedTask);
	}
	
	private static boolean UICanDisplayMoreTask() {
		return !mUserInterface.isFull();
	}
}
