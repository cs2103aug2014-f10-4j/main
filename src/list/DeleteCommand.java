package list;

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
	                               CommandExecutionException {
		if (this.taskNumber == null) {
		    throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
		}
	    if (!taskManager.hasTaskWithNumber(taskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		taskManager.deleteTask(this.taskNumber);
		
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}
	
	

}
