package list;

import list.CommandBuilder.RepeatFrequency;

public class Task implements ITask {
	private String title;
	private Date startTime;
	private Date endTime;
	private RepeatFrequency repeatFrequency;
	private String place;
	private ICategory category;
	private String notes;
	
	@Override
	public int compareTo(ITask o) {
	    return this.getEndTime().compareTo(o.getEndTime());
	}

	public String getTitle() {
		return title;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Task setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Task setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public RepeatFrequency getRepeatFrequency() {
		return repeatFrequency;
	}

	public Task setRepeatFrequency(RepeatFrequency repeatFrequency) {
		this.repeatFrequency = repeatFrequency;
		return this;
	}

	public String getPlace() {
		return place;
	}

	public Task setPlace(String place) {
		this.place = place;
		return this;
	}

	public ICategory getCategory() {
		return category;
	}

	public Task setCategory(ICategory category) {
		this.category = category;
		return this;
	}

	public String getNotes() {
		return notes;
	}

	public Task setNotes(String notes) {
		this.notes = notes;
		return this;
	}
}
