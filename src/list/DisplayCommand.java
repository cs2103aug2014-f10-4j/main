package list;

/**
 * 
 * @author Michael
 *
 */
public class DisplayCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING = "Displaying ...";
    private static final String MESSAGE_NO_TASK_NUMBER = "Please specify task number.";
    
	private TaskManager taskManager = TaskManager.getInstance();
	private Integer taskNumber;

	public DisplayCommand() { };
	
	public DisplayCommand(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	public DisplayCommand setTaskNumber(Integer taskNumber) {
	    this.taskNumber = taskNumber;
	    return this;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException,
	                               CommandExecutionException {
	    if (this.taskNumber == null) {
            throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
        }
		if (!taskManager.hasTaskWithNumber(this.taskNumber)) {
			throw new InvalidTaskNumberException();	
		} 
		
		ITask selectedTask = taskManager.getTask(this.taskNumber);
		
		Controller.updateUiWithTaskDetail(selectedTask);
		
		return MESSAGE_DISPLAYING;
	}

}
