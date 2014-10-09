package list;

/**
 * 
 * @author Michael
 *
 */
public class DeleteCommand implements ICommand {
	private Integer mTaskNumber;

	public DeleteCommand(Integer taskNumber) {
		mTaskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
		if (!isValid(mTaskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		TaskManager.deleteTask(mTaskNumber);
		
		Controller.displayTasksInUI();
		
		return "Task is deleted successfully";
	}
	
	private boolean isValid(Integer taskNumber) {
		return (taskNumber >= 1) && (taskNumber <= TaskManager.getNumberOfTasks());
	}

}
