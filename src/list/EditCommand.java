package list;

import list.CommandBuilder.RepeatFrequency;

/**
 * 
 * @author Michael
 *
 */
public class EditCommand implements ICommand {
		
	private TaskManager taskManager = TaskManager.getInstance(); 
	private Integer taskNumber;
	private String title;
	private Date startDate;
	private Date endDate;
	private RepeatFrequency repeatFrequency;
	private String place;
	private ICategory category;
	private String notes;
	
	public EditCommand(Integer taskNumber,
					   String title,
			           Date startDate,
			           Date endTime,
			           RepeatFrequency repeatFrequency,
			           String place,
			           ICategory category,
			           String notes) {
		this.taskNumber = taskNumber;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endTime;
		this.repeatFrequency = repeatFrequency;
		this.place = place;
		this.category = category;
		this.notes = notes;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
			
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
		
		Controller.updateListOfTasksInUi();
		
		return "Task is successfully edited";
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
