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
	public String execute() {
		ITask selectedTask = TaskManager.getTask(mTaskNumber);
		
		Controller.updateUIWithTaskDetail(selectedTask);
		
		return "Displaying ...";
	}

}
