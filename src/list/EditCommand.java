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
	private String mCategory;
	private String mNotes;
	
	public EditCommand(Integer taskNumber,
					   String title,
			           Date startTime,
			           Date endTime,
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
	
	public String getCategory() {
	    return mCategory;
	}
	
	public String getNotes() {
	    return mNotes;
	}

}
