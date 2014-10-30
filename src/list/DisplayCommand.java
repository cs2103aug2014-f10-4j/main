package list;

/**
 * 
 * @author Michael
 *
 */
public class DisplayCommand implements ICommand {
    private static final String MESSAGE_INVALID_TASK_NUMBER = "Invalid task number.";
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
	public String execute() throws CommandExecutionException {
	    if (this.taskNumber == null) {
            throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
        }
		if (!Controller.hasTaskWithNumber(this.taskNumber)) {
			throw new CommandExecutionException(MESSAGE_INVALID_TASK_NUMBER);	
		} 
		
		ITask selectedTask = Controller.getTaskWithNumber(this.taskNumber);
		
		Controller.displayTaskDetail(selectedTask);
		
		return MESSAGE_DISPLAYING;
	}

}
