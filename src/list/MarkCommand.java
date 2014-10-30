package list;

import java.io.IOException;

public class MarkCommand implements ICommand {

	private static final String MESSAGE_SUCCESS = "Task is marked as done successfully.";
	private static final String MESSAGE_NO_TASK_NUMBER = "Please specify task number.";
	private static final String MESSAGE_INVALID_TASK_NUMBER = "Invalid task number.";
	
	private Integer taskNumber;
	private TaskManager taskManager = TaskManager.getInstance();	
	
	MarkCommand(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	@Override
	public String execute() throws CommandExecutionException, IOException {
		
		if (taskNumber == null) {
			throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
		}
		
		if (!Controller.hasTaskWithNumber(taskNumber)) {
			throw new CommandExecutionException(MESSAGE_INVALID_TASK_NUMBER);
		}
		
		ITask taskToMark = Controller.getTaskWithNumber(taskNumber);
		taskManager.markTaskAsDone(taskToMark);
		taskManager.saveTasks();
        
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}

}
