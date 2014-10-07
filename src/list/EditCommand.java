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
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
