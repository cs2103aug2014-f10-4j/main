package list;

import list.CommandBuilder.RepeatFrequency;

interface ITask extends Comparable<ITask> {
	public String getTitle();

	public ITask setTitle(String title);

	public Date getStartTime();

	public ITask setStartTime(Date startTime);

	public Date getEndTime();

	public ITask setEndTime(Date endTime);

	public RepeatFrequency getRepeatFrequency();

	public ITask setRepeatFrequency(RepeatFrequency repeatFrequency);

	public String getPlace();

	public ITask setPlace(String place);

	public ICategory getCategory();

	public ITask setCategory(ICategory category);

	public String getNotes();

	public ITask setNotes(String notes);
}
