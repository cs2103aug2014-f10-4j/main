package list;

import java.util.Calendar;

import list.CommandBuilder.RepeatFrequency;

/**
 * 
 * @author Michael
 *
 */
public class EditCommand implements ICommand {
		
	private Integer mTaskNumber;
	private String mTitle;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private String mCategory;
	private String mNotes;
	
	public EditCommand(Integer taskNumber,
					   String title,
			           Calendar startTime,
			           Calendar endTime,
			           RepeatFrequency repeatFrequency,
			           String place,
			           String category,
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
			
		if (!isValid(mTaskNumber)) {
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
		
		Controller.displayTasksInUI();
		
		return "Task is successfully edited";
	}

	private boolean isValid(Integer taskNumber) {
		return (taskNumber >= 1) && (taskNumber <= TaskManager.getNumberOfTasks());
	}

}
