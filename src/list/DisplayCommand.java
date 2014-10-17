package list;

/**
 * 
 * @author Michael
 *
 */
public class DisplayCommand implements ICommand {
	
	private TaskManager taskManager = TaskManager.getInstance();
	private Integer mTaskNumber;

	public DisplayCommand(Integer taskNumber) {
		mTaskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
		
		if (!taskManager.isValidTaskNumber(mTaskNumber)) {
			throw new InvalidTaskNumberException();	
		} 
		
		ITask selectedTask = taskManager.getTask(mTaskNumber);
		
		
		Controller.updateUIWithTaskDetail(selectedTask);
		
		return "Displaying ...";
	}

}
