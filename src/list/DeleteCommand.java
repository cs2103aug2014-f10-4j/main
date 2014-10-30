package list;

import java.io.IOException;

import list.ICommand.CommandExecutionException;

import org.json.JSONException;

/**
 * 
 * @author Michael
 *
 */
public class DeleteCommand implements ICommand {
    private static final String MESSAGE_SUCCESS = "Task is deleted successfully";
    private static final String MESSAGE_TASK_UNSPECIFIED = "Please specify a valid task.";
	
    private TaskManager taskManager = TaskManager.getInstance();
	private ITask task;

	public DeleteCommand() { };
	
	public DeleteCommand(ITask task) {
		this.task = task;
	}
    
    public DeleteCommand setTask(ITask task) {
        this.task = task;
        return this;
    }
	
	@Override
	public String execute() throws CommandExecutionException,  
	                               IOException {
	    if (this.task == null) {
            throw new CommandExecutionException(MESSAGE_TASK_UNSPECIFIED);
        }
        
        taskManager.deleteTask(this.task);
		taskManager.saveTasks();
		
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}
	
	

}
