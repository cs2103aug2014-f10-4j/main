package list;

import java.io.IOException;

import list.model.ITask;
import list.util.Constants;

public class UnmarkCommand implements ICommand {
	
	private static final String MESSAGE_SUCCESS = "Task is unmarked successfully.";
	private static final String MESSAGE_TASK_UNSPECIFIED = "Please specify a valid task.";
	
	private TaskManager taskManager = TaskManager.getInstance();
	private ITask task;
	
	public UnmarkCommand(ITask task) {
		this.task = task;
	}
    
    public UnmarkCommand setTask(ITask task) {
        this.task = task;
        return this;
    }
	
	@Override
	public String execute() throws CommandExecutionException, IOException {
		
		if (this.task == null) {
			throw new CommandExecutionException(MESSAGE_TASK_UNSPECIFIED);
		}
		
		ITask taskToUnmark = this.task;
		taskManager.unmarkTask(taskToUnmark);
		
		if (Controller.hasTask(task)) {
			Controller.highlightTask(task);
		} else {
			if (!task.hasDeadline()) {
				Controller.displayTasks(Constants.FLOATING_TASKS, taskManager.getFloatingTasks());
			} else if (task.isOverdue()) {
				Controller.displayTasks(Constants.OVERDUE_TASKS, taskManager.getOverdueTasks());
			} else {
				Controller.displayTasks(Constants.CURRENT_TASKS, taskManager.getCurrentTasks());
			}
			
			Controller.highlightTask(task);
		}
		
		taskManager.saveData();
		
		return MESSAGE_SUCCESS;
	}

    @Override
    public ICommand getInverseCommand() {
        ICommand inverseCommand = new MarkCommand(this.task);
        return inverseCommand;
    }

}
