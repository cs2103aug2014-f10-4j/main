package list;

import java.io.IOException;

import org.json.JSONException;

/**
 * 
 * @author Michael
 *
 */
public class DeleteCommand implements ICommand {
	private static final String MESSAGE_INVALID_TASK_NUMBER = "Invalid task number.";
    private static final String MESSAGE_NO_TASK_NUMBER = "Please specify task number.";
    private static final String MESSAGE_SUCCESS = "Task is deleted successfully";
	
    private TaskManager taskManager = TaskManager.getInstance();
	private Integer taskNumber = null;

	public DeleteCommand() { };
	
	public DeleteCommand(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException,
	                               CommandExecutionException,  
	                               IOException {
		if (this.taskNumber == null) {
		    throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
		}
	    if (!Controller.hasTaskWithNumber(taskNumber)) {
			throw new CommandExecutionException(MESSAGE_INVALID_TASK_NUMBER);
		}
	    
		ITask task = Controller.getTask(taskNumber);
		taskManager.deleteTask(task);
		taskManager.saveTasks();
		
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}
	
	

}
