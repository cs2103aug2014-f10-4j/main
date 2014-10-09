package list;

import java.util.Calendar;

import list.CommandBuilder.RepeatFrequency;

/**
 * An example implementation of ICommand.
 * Other commands (Edit, Delete, etc.) should follow
 * this sample.
 * 
 * @author Michael
 */
public class AddCommand implements ICommand {
	private String mTitle;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private String mCategory;
	private String mNotes;
	
	/**
	 * Save the provided information in private variable so that it can
	 * be used during execute() command
	 * 
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param repeatFrequency
	 * @param place
	 * @param category
	 * @param notes
	 */
	AddCommand(String title,
               Calendar startTime,
               Calendar endTime,
               RepeatFrequency repeatFrequency,
               String place,
               String category,
               String notes) {
		
		mTitle = title;
		mStartTime = startTime;
		mEndTime = endTime;
		mRepeatFrequency = repeatFrequency;
		mPlace = place;
		mCategory = category;
		mNotes = notes;
	}
	
	@Override
	public String execute() {
		Task task = new Task();
		task.setTitle(mTitle)
			.setStartTime(mStartTime)
			.setEndTime(mEndTime)
			.setRepeatFrequency(mRepeatFrequency)
			.setPlace(mPlace)
			.setCategory(mCategory)
			.setNotes(mNotes);
		
		TaskManager.addTask(task);
		
		Controller.displayTasksInUI();
		
		return "Task added succesfully";
	}

}
