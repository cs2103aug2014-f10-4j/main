package list;

import list.CommandBuilder.RepeatFrequency;

/**
 * 
 * @author Michael
 *
 */
public class EditCommand implements ICommand {
		
	private Integer mTaskNumber;
	private String mTitle;
	private Date mStartTime;
	private Date mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private ICategory mCategory;
	private String mNotes;
	
	public EditCommand(Integer taskNumber,
					   String title,
			           Date startTime,
			           Date endTime,
			           RepeatFrequency repeatFrequency,
			           String place,
			           ICategory category,
			           String notes) {
		mTaskNumber = taskNumber;
		mTitle = title;
		mStartTime = startTime;
		mEndTime = endTime;
		mRepeatFrequency = repeatFrequency;
		mPlace = place;
		mCategory = category;
		mNotes = notes;
	}
	
	@Override
	public String execute() throws InvalidTaskNumberException {
			
		if (!TaskManager.isValidTaskNumber(mTaskNumber)) {
			throw new InvalidTaskNumberException();
		}
		
		ITask taskToEdit = TaskManager.getTask(mTaskNumber);
				
		if (mTitle != null) {
			taskToEdit.setTitle(mTitle);	
		}
		
		if (mStartTime != null) {
			taskToEdit.setStartTime(mStartTime);
		}
		
		if (mEndTime != null) {
			taskToEdit.setEndTime(mEndTime);
		}
		
		if (mRepeatFrequency != null) {
			taskToEdit.setRepeatFrequency(mRepeatFrequency);
		}
		
		if (mPlace != null) {
			taskToEdit.setPlace(mPlace);
		}
		
		if (mCategory != null) {
			taskToEdit.setCategory(mCategory);
		}
		
		if (mNotes != null) {
			taskToEdit.setNotes(mNotes);
		}
		
		TaskManager.sortTasks();
		
		Controller.updateListOfTasksInUI();
		
		return "Task is successfully edited";
	}
	
	public Integer getTaskNumber() {
	    return mTaskNumber;
	}
	
	public String getTitle() {
	    return mTitle;
	}
	
	public Date getStartTime() {
	    return mStartTime;
	}
	
	public Date getEndTime() {
	    return mEndTime;
	}
	
	public RepeatFrequency getRepeatFrequency() {
	    return mRepeatFrequency;
	}
	
	public String getPlace() {
	    return mPlace;
	}
	
	public ICategory getCategory() {
	    return mCategory;
	}
	
	public String getNotes() {
	    return mNotes;
	}

}
