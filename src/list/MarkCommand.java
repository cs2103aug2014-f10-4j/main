package list;

import java.io.IOException;

import list.ICommand.CommandExecutionException;

public class MarkCommand implements ICommand {

	private static final String MESSAGE_SUCCESS = "Task is marked as done successfully.";
    private static final String MESSAGE_TASK_UNSPECIFIED = "Please specify a valid task.";
	
	private TaskManager taskManager = TaskManager.getInstance();	
	private ITask task;
	
	MarkCommand(ITask task) {
		this.task = task;
	}
    
    public MarkCommand setTask(ITask task) {
        this.task = task;
        return this;
    }

	@Override
	public String execute() throws CommandExecutionException, IOException {
		
	    if (this.task == null) {
            throw new CommandExecutionException(MESSAGE_TASK_UNSPECIFIED);
        }
        
		ITask taskToMark = this.task;
		taskManager.markTaskAsDone(taskToMark);
		taskManager.saveTasks();
        
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}

}
