package list;

import list.ICommand.CommandExecutionException;

/**
 * 
 * @author Michael
 *
 */
public class DisplayCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING = "Displaying ...";
    private static final String MESSAGE_TASK_UNSPECIFIED = "Please specify a valid task.";
    
	private ITask task;

	public DisplayCommand() { };
	
	public DisplayCommand(Integer taskNumber) {
		this.task = Controller.getTaskWithNumber(taskNumber);
	}
    
    public DisplayCommand setTask(ITask task) {
        this.task = task;
        return this;
    }
	
	@Override
	public String execute() throws CommandExecutionException {
	    if (this.task == null) {
            throw new CommandExecutionException(MESSAGE_TASK_UNSPECIFIED);
        }
        
		ITask selectedTask = this.task;
		
		Controller.displayTaskDetail(selectedTask);
		
		return MESSAGE_DISPLAYING;
	}

}
