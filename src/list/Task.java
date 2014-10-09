package list;

import list.CommandBuilder.RepeatFrequency;

public class Task implements ITask {
	private String mTitle;
	private Date mStartTime;
	private Date mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private String mCategory;
	private String mNotes;
	
	@Override
	public int compareTo(ITask o) {
	    return this.getEndTime().compareTo(o.getEndTime());
	}

	public String getTitle() {
		return mTitle;
	}

	public Task setTitle(String mTitle) {
		this.mTitle = mTitle;
		return this;
	}

	public Date getStartTime() {
		return mStartTime;
	}

	public Task setStartTime(Date mStartTime) {
		this.mStartTime = mStartTime;
		return this;
	}

	public Date getEndTime() {
		return mEndTime;
	}

	public Task setEndTime(Date mEndTime) {
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
