package list.model;

import java.util.List;

import list.CommandBuilder.RepeatFrequency;

public interface ITask extends Comparable<ITask> {
	
	public static enum TaskStatus {
		DONE, PENDING
	}
	
	public String getTitle();

	public ITask setTitle(String title);

	public Date getStartDate();

	public ITask setStartDate(Date startDate);

	public Date getEndDate();

	public ITask setEndDate(Date endTime);
	
	public Date getTimelineDate();

	public RepeatFrequency getRepeatFrequency();

	public ITask setRepeatFrequency(RepeatFrequency repeatFrequency);

	public String getPlace();

	public ITask setPlace(String place);

	public ICategory getCategory();

	public ITask setCategory(ICategory category);

	public String getNotes();

	public ITask setNotes(String notes);
	
	public TaskStatus getStatus();

	public void setStatus(TaskStatus status);
	
	public List<ITask> getList();
	
	public void setList(List<ITask> list);
	
	public boolean hasDeadline();
	
	public boolean isOverdue();
}
