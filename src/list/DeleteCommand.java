package list;

/**
 * 
 * @author Michael
 *
 */
public class DeleteCommand implements ICommand {
	private TaskManager taskManager = TaskManager.getInstance();
	private Integer mTaskNumber;

	public DeleteCommand(Integer taskNumber) {
		mTaskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
		if (!TaskManager.getInstance().isValidTaskNumber(mTaskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		TaskManager.getInstance().deleteTask(mTaskNumber);
		
		Controller.updateListOfTasksInUI();
		
		return "Task is deleted successfully";
	}
	
	

}
