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
		
		if (!TaskManager.getInstance().isValidTaskNumber(mTaskNumber)) {
			throw new InvalidTaskNumberException();	
		} 
		
		ITask selectedTask = TaskManager.getInstance().getTask(mTaskNumber);
		
		
		Controller.updateUIWithTaskDetail(selectedTask);
		
		return "Displaying ...";
	}

}
