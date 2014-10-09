package list;

import java.util.Scanner;

import list.ICommand.InvalidTaskNumberException;
import list.IParser.ParseException;

public class Controller {
		
	private static final String MESSAGE_INVALID_TASK_NUMBER = "Task number entered is invalid.";
	
	private static IUserInterface mUserInterface = null;
	private static IParser mParser = new Parser();
	
	public static void main(String[] args) {
		
		
		while (true) {
			try {
				Scanner sc = new Scanner(System.in);
				String userInput = sc.nextLine();
				
				//String userInput = receiveUserInputFromUI(mUserInterface.getUserInput());
				ICommand commandMadeByParser = mParser.parse(userInput);
				commandMadeByParser.execute();
				
				TaskManager.printTasks();
				
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			} catch (InvalidTaskNumberException e) {
				System.out.println(MESSAGE_INVALID_TASK_NUMBER);
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
