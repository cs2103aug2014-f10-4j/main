package list;

/**
 * 
 * @author Michael
 *
 */
public class DisplayCommand implements ICommand {
	private Integer mTaskNumber;

	public DisplayCommand(Integer taskNumber) {
		mTaskNumber = taskNumber;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
		
		if (!TaskManager.isValid(mTaskNumber)) {
			throw new InvalidTaskNumberException();	
		} 
		
		ITask selectedTask = TaskManager.getTask(mTaskNumber);
		
		
		//Controller.updateUIWithTaskDetail(selectedTask);
		
		return "Displaying ...";
	}

}
