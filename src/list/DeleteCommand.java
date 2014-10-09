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
		if (!TaskManager.isValid(mTaskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		TaskManager.deleteTask(mTaskNumber);
		
		Controller.updateListOfTasksInUI();
		
		return "Task is deleted successfully";
	}
	
	

}
