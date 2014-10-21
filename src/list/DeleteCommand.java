package list;

import java.io.IOException;

import org.json.JSONException;

/**
 * 
 * @author Michael
 *
 */
public class DeleteCommand implements ICommand {
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
	    if (!taskManager.hasTaskWithNumber(taskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		taskManager.deleteTask(this.taskNumber);
		taskManager.saveTasks();
		
		Controller.updateListOfTasksInUi();
		
		return MESSAGE_SUCCESS;
	}
	
	

}
