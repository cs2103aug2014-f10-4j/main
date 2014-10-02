package list;

import java.util.Calendar;

import list.CommandBuilder.RepeatFrequency;

interface ITask extends Comparable<ITask> {
	public String getTitle();

	public ITask setTitle(String mTitle);

	public Calendar getStartTime();

	public ITask setStartTime(Calendar mStartTime);

	public Calendar getEndTime();

	public ITask setEndTime(Calendar mEndTime);

	public RepeatFrequency getRepeatFrequency();

	public ITask setRepeatFrequency(RepeatFrequency mRepeatFrequency);

	public String getPlace();

	public ITask setPlace(String mPlace);

	public String getCategory();

	public ITask setCategory(String mCategory);

	public String getNotes();

	public ITask setNotes(String mNotes);
}
