package list;

import java.io.IOException;

import org.json.JSONException;

import list.CommandBuilder.RepeatFrequency;

/**
 * 
 * @author Michael
 *
 */
public class EditCommand implements ICommand {
		
	private static final String MESSAGE_SUCCESS = "Task is successfully edited";
    private static final String MESSAGE_NO_TASK_NUMBER = "Please specify task number.";
    private TaskManager taskManager = TaskManager.getInstance(); 
	private Integer taskNumber;
	private String title;
	private Date startDate;
	private Date endDate;
	private RepeatFrequency repeatFrequency;
	private String place;
	private ICategory category;
	private String notes;

    public EditCommand() { };
    
	public EditCommand(Integer taskNumber,
					   String title,
			           Date startDate,
			           Date endDate,
			           RepeatFrequency repeatFrequency,
			           String place,
			           ICategory category,
			           String notes) {
		this.taskNumber = taskNumber;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeatFrequency = repeatFrequency;
		this.place = place;
		this.category = category;
		this.notes = notes;
	}
	
	public EditCommand setTaskNumber(Integer taskNumber) {
	    this.taskNumber = taskNumber;
	    return this;
	}

	public EditCommand setTitle(String title) {
	    this.title = title;
	    return this;
	}

	public EditCommand setStartDate(Date startDate) {
	    this.startDate = startDate;
	    return this;
	}

	public EditCommand setEndDate(Date endDate) {
	    this.endDate = endDate;
	    return this;
	}

	public EditCommand setRepeatFrequency(RepeatFrequency repeatFrequency) {
	    this.repeatFrequency = repeatFrequency;
	    return this;
	}

	public EditCommand setPlace(String place) {
	    this.place = place;
	    return this;
	}

	public EditCommand setCategory(ICategory category) {
	    this.category = category;
	    return this;
	}

	public EditCommand setNotes(String notes) {
	    this.notes = notes;
	    return this;
	}

	@Override
	public String execute() throws InvalidTaskNumberException,
	                               CommandExecutionException, 
	                               IOException {
		if (this.taskNumber == null) {
		    throw new CommandExecutionException(MESSAGE_NO_TASK_NUMBER);
		}
		if (!taskManager.hasTaskWithNumber(taskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		ITask taskToEdit = taskManager.getTask(taskNumber);
				
		if (this.title != null) {
			taskToEdit.setTitle(title);	
		}
		
		if (this.startDate != null) {
			taskToEdit.setStartDate(startDate);
		}
		
		if (this.endDate != null) {
			taskToEdit.setEndDate(endDate);
		}
		
		if (this.repeatFrequency != null) {
			taskToEdit.setRepeatFrequency(repeatFrequency);
		}
		
		if (this.place != null) {
			taskToEdit.setPlace(place);
		}
		
		if (this.category != null) {
			taskToEdit.setCategory(category);
		}
		
		if (this.notes != null) {
			taskToEdit.setNotes(notes);
		}
		
		taskManager.sortTasks();
		taskManager.saveTasks();
		
		Controller.updateListOfTasksInUi();
		
		return MESSAGE_SUCCESS;
	}
	
	public Integer getTaskNumber() {
	    return this.taskNumber;
	}
	
	public String getTitle() {
	    return this.title;
	}
	
	public Date getStartDate() {
	    return this.startDate;
	}
	
	public Date getEndDate() {
	    return this.endDate;
	}
	
	public RepeatFrequency getRepeatFrequency() {
	    return this.repeatFrequency;
	}
	
	public String getPlace() {
	    return this.place;
	}
	
	public ICategory getCategory() {
	    return this.category;
	}
	
	public String getNotes() {
	    return this.notes;
	}

}
