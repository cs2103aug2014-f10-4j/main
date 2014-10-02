package list;

import java.util.Calendar;

import list.CommandBuilder.RepeatFrequency;

public class Task implements ITask {
	private String mTitle;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private String mCategory;
	private String mNotes;
	
	@Override
	public int compareTo(ITask o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTitle() {
		return mTitle;
	}

	public Task setTitle(String mTitle) {
		this.mTitle = mTitle;
		return this;
	}

	public Calendar getStartTime() {
		return mStartTime;
	}

	public Task setStartTime(Calendar mStartTime) {
		this.mStartTime = mStartTime;
		return this;
	}

	public Calendar getEndTime() {
		return mEndTime;
	}

	public Task setEndTime(Calendar mEndTime) {
		this.mEndTime = mEndTime;
		return this;
	}

	public RepeatFrequency getRepeatFrequency() {
		return mRepeatFrequency;
	}

	public Task setRepeatFrequency(RepeatFrequency mRepeatFrequency) {
		this.mRepeatFrequency = mRepeatFrequency;
		return this;
	}

	public String getPlace() {
		return mPlace;
	}

	public Task setPlace(String mPlace) {
		this.mPlace = mPlace;
		return this;
	}

	public String getCategory() {
		return mCategory;
	}

	public Task setCategory(String mCategory) {
		this.mCategory = mCategory;
		return this;
	}

	public String getNotes() {
		return mNotes;
	}

	public Task setNotes(String mNotes) {
		this.mNotes = mNotes;
		return this;
	}

}
