package list;

import list.CommandBuilder.RepeatFrequency;

public class Task implements ITask {
	private String title;
	private Date startDate;
	private Date endDate;
	private RepeatFrequency repeatFrequency;
	private String place;
	private ICategory category;
	private String notes;
	private TaskStatus status;
	
	@Override
	public int compareTo(ITask o) {
	    if (this.getEndDate() != null && o.getEndDate() != null) {
	        int result = this.getEndDate().compareTo(o.getEndDate());
	        if (result != 0) {
	            return result;
	        } else {
	            return this.getTitle().compareTo(o.getTitle());
	        }
	    } else {
	        return this.getTitle().compareTo(o.getTitle());
	    }
	}

	public String getTitle() {
		return title;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Task setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Task setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
