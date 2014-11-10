//@author A0113672L
package list;

import java.io.IOException;

import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.model.ITask.TaskStatus;
import list.model.Task;
import list.util.Constants;

/**
 * This class handles the execution of 'add' command, which adds a new task into the
 * application.
 * 
 * @author A0094022R
 */
public class AddCommand implements ICommand {
	private static final String MESSAGE_TASK_ADDED_SUCCESFULLY = "Task added succesfully";
    private static final String MESSAGE_NO_TITLE = "Please specify title for the task.";
    private TaskManager taskManager = TaskManager.getInstance();
	private String title = null;
	private Date startDate = null;
	private Date endDate = null;
	private RepeatFrequency repeatFrequency = RepeatFrequency.NONE;
	private String place = null;
	private ICategory category = null;
	private String notes = null;
	
	private ITask task;
	
	public AddCommand() { };
	
	public AddCommand(String title, 
	                  Date startDate, 
	                  Date endDate,
	                  RepeatFrequency repeatFrequency, 
	                  String place,
	                  ICategory category, 
	                  String notes) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeatFrequency = repeatFrequency;
        this.place = place;
        this.category = category;
        this.notes = notes;
    }

    public AddCommand setTitle(String title) {
	    this.title = title;
        return this;
	}
	
	public AddCommand setStartDate(Date startDate) {
	    this.startDate = startDate;
        return this;
	}
	
	public AddCommand setEndDate(Date endDate) {
	    this.endDate = endDate;
        return this;
	}
	
	public AddCommand setRepeatFrequency(RepeatFrequency repeatFrequency) {
	    this.repeatFrequency = repeatFrequency;
        return this;
	}
	
	public AddCommand setPlace(String place) {
	    this.place = place;
        return this;
	}
	
	public AddCommand setCategory(ICategory category) {
	    this.category = category;
        return this;
	}
	
	public AddCommand setNotes(String notes) {
	    this.notes = notes;
	    return this;
	}
	
	/**
	 * This method will create a new Task object, complete with its attributes
	 * (e.g. title, startDate, endDate, etc.). A task object should always have title. 
	 * Upon addition of the new task, it will give feedback through controller to UI 
	 * accordingly.
	 */
	@Override
	public String execute() throws CommandExecutionException, IOException {
	    //enforce required conditions
	    if (this.title == null) {
	        throw new CommandExecutionException(MESSAGE_NO_TITLE);
	    }
	    
		ITask task = new Task();
		task.setTitle(this.title)
			.setStartDate(this.startDate)
			.setEndDate(this.endDate)
			.setRepeatFrequency(this.repeatFrequency)
			.setPlace(this.place)
			.setCategory(this.category)
			.setNotes(this.notes)
			.setStatus(TaskStatus.PENDING);
		
		this.task = task;
		
		taskManager.addTask(task);
		
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
		
		return MESSAGE_TASK_ADDED_SUCCESFULLY;
	}

	/** 
	 * @author A0113672L
	 */
    @Override
    public ICommand getInverseCommand() {
        assert(this.task != null);
        DeleteCommand deleteCommand = new DeleteCommand(this.task);
        return deleteCommand;
    }
}
