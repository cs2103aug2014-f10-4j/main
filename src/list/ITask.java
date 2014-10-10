package list;

import list.CommandBuilder.RepeatFrequency;

interface ITask extends Comparable<ITask> {
	public String getTitle();

	public ITask setTitle(String mTitle);

	public Date getStartTime();

	public ITask setStartTime(Date mStartTime);

	public Date getEndTime();

	public ITask setEndTime(Date mEndTime);

	public RepeatFrequency getRepeatFrequency();

	public ITask setRepeatFrequency(RepeatFrequency mRepeatFrequency);

	public String getPlace();

	public ITask setPlace(String mPlace);

	public ICategory getCategory();

	public ITask setCategory(String mCategory);

	public String getNotes();

	public ITask setNotes(String mNotes);
}
