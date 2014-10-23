package list;

public class UnmarkCommand implements ICommand {
	
	private static final String MESSAGE_SUCCESS = "Task is unmarked successfully.";
	private static final String MESSAGE_NO_TASK_NUMBER = "Please specify task number.";
	private static final String MESSAGE_INVALID_TASK_NUMBER = "Invalid task number.";
	
	private Integer taskNumber;
	private TaskManager taskManager = TaskManager.getInstance();
	
	public UnmarkCommand(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws CommandExecutionException {
		
		if (taskNumber == null) {
			throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
		}
		
		if (!Controller.hasTaskWithTaskNumber(taskNumber)) {
			throw new CommandExecutionException(MESSAGE_INVALID_TASK_NUMBER);
		}
		
		ITask taskToUnmark = Controller.getTask(taskNumber);
		taskManager.unmarkTask(taskToUnmark);
		
		Controller.refreshUi();
		
		return MESSAGE_SUCCESS;
	}

}
