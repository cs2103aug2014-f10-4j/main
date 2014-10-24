package list;

import java.io.IOException;

import org.json.JSONException;

import list.CommandBuilder.RepeatFrequency;
import list.ITask.TaskStatus;

/**
 * An example implementation of ICommand.
 * Other commands (Edit, Delete, etc.) should follow
 * this sample.
 * 
 * @author Michael
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
	 */
	@Override
	public String execute() throws CommandExecutionException, IOException {
	    //enforce required conditions
	    if (this.title == null) {
	        throw new CommandExecutionException(MESSAGE_NO_TITLE);
	    }
	    
		Task task = new Task();
		task.setTitle(this.title)
			.setStartDate(this.startDate)
			.setEndDate(this.endDate)
			.setRepeatFrequency(this.repeatFrequency)
			.setPlace(this.place)
			.setCategory(this.category)
			.setNotes(this.notes)
			.setStatus(TaskStatus.PENDING);
		
		taskManager.addTask(task);
		taskManager.saveTasks();
		
		Controller.refreshUi();
		
		return MESSAGE_TASK_ADDED_SUCCESFULLY;
	}

}
