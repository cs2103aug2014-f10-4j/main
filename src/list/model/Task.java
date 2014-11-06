package list.model;

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
	
	public Task() {
	    title = "";
	    startDate = Date.getFloatingDate();
	    endDate = Date.getFloatingDate();
	    repeatFrequency = RepeatFrequency.NONE;
	    place = "";
	    category = Category.getDefaultCategory();
	    notes = "";
	    status = TaskStatus.PENDING;
	}
	
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
		if (title != null) {
		    this.title = title;
		}
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Task setStartDate(Date startDate) {
	    if (startDate != null) {
	        this.startDate = startDate;
	    }
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Task setEndDate(Date endDate) {
	    if (endDate != null) {
	        this.endDate = endDate;
	    }
		return this;
	}

	public RepeatFrequency getRepeatFrequency() {
		return repeatFrequency;
	}

	public Task setRepeatFrequency(RepeatFrequency repeatFrequency) {
	    if (repeatFrequency != null) {
	        this.repeatFrequency = repeatFrequency;
	    }
		return this;
	}

	public String getPlace() {
		return place;
	}

	public Task setPlace(String place) {
	    if (place != null) {
	        this.place = place;
	    }
		return this;
	}

	public ICategory getCategory() {
		return category;
	}

	public Task setCategory(ICategory category) {
	    if (category != null) {
	        this.category = category;
	    }
	    
		return this;
	}

	public String getNotes() {
		return notes;
	}

	public Task setNotes(String notes) {
	    if (notes != null) {
	        this.notes = notes;
	    }
		return this;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
	    if (status != null) {
	        this.status = status;
	    }
	}
}
