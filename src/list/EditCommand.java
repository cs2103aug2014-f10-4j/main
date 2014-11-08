package list;

import java.io.IOException;

import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.util.Constants;

/**
 * 
 * @author Michael
 *
 */
public class EditCommand implements ICommand {
		
    private static final String MESSAGE_SUCCESS = "Task is successfully edited";
    private static final String MESSAGE_TASK_UNSPECIFIED = "Please specify a valid task.";
    private static final String MESSAGE_ERROR_NO_END_TIME = "Unable to edit into task with " + 
    												  		"start date but no end date";
    private static final String MESSAGE_ERROR_INVALID_START_DATE = "Unable to edit because end date " + 
    												  		       "cannot be later than start date";
    
    private TaskManager taskManager = TaskManager.getInstance(); 
    private ITask task;
	private String title;
	private Date startDate;
	private Date endDate;
	private RepeatFrequency repeatFrequency;
	private String place;
	private ICategory category;
	private String notes;
	
	private ICommand inverseCommand;
	private boolean isExecuted = false;

    public EditCommand() { };
    
	public EditCommand(ITask task,
					   String title,
			           Date startDate,
			           Date endDate,
			           RepeatFrequency repeatFrequency,
			           String place,
			           ICategory category,
			           String notes) {
		this.task = task;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeatFrequency = repeatFrequency;
		this.place = place;
		this.category = category;
		this.notes = notes;
	}
	
	public EditCommand setTask(ITask task) {
	    this.task = task;
	    return this;
	}
	public EditCommand setTitle(String title) {
	    this.title = title;
	    return this;
	}

	public EditCommand setStartDate(Date startDate) {
	    this.startDate = startDate;
	    return this;
	}

	public EditCommand setEndDate(Date endDate) {
	    this.endDate = endDate;
	    return this;
	}

	public EditCommand setRepeatFrequency(RepeatFrequency repeatFrequency) {
	    this.repeatFrequency = repeatFrequency;
	    return this;
	}

	public EditCommand setPlace(String place) {
	    this.place = place;
	    return this;
	}

	public EditCommand setCategory(ICategory category) {
	    this.category = category;
	    return this;
	}

	public EditCommand setNotes(String notes) {
	    this.notes = notes;
	    return this;
	}

	@Override
	public String execute() throws CommandExecutionException, IOException {
	    if (this.task == null) {
            throw new CommandExecutionException(MESSAGE_TASK_UNSPECIFIED);
        } else if (!task.getStartDate().equals(Date.getFloatingDate()) &&
        		   this.endDate != null && this.endDate.equals(Date.getFloatingDate())) {
        	throw new CommandExecutionException(MESSAGE_ERROR_NO_END_TIME);
        } else if (this.endDate != null && this.startDate != null &&
        		   this.startDate.compareTo(endDate) > 0) {
        	throw new CommandExecutionException(MESSAGE_ERROR_INVALID_START_DATE);
        }
	    
	    if (isExecuted) {
	        assert(false);
	    }
	    
	    this.inverseCommand = createInverseCommand();
        
	    ITask taskToEdit = this.task;
	    
	    taskToEdit.getList().remove(taskToEdit);	    
	    
		if (this.title != null) {
			taskToEdit.setTitle(title);	
		}
		
		if (this.startDate != null) {
			taskToEdit.setStartDate(startDate);
		}
		
		if (this.endDate != null) {
			taskToEdit.setEndDate(endDate);
		}
		
		if (this.repeatFrequency != null) {
			taskToEdit.setRepeatFrequency(repeatFrequency);
		}
		
		if (this.place != null) {
			taskToEdit.setPlace(place);
		}
		
		if (this.category != null) {
			taskManager.removeFromCategoryList(taskToEdit);
			taskToEdit.setCategory(category);
			taskManager.addToCategoryList(taskToEdit);
		}
		
		if (this.notes != null) {
			taskToEdit.setNotes(notes);
		}
		
		if (taskToEdit.hasDeadline()) {
			if (taskToEdit.isOverdue()) {
				taskManager.addToOverdueTasks(taskToEdit);
				taskToEdit.setList(taskManager.getOverdueTasks());
			} else {
				taskManager.addToCurrentTasks(taskToEdit);
				taskToEdit.setList(taskManager.getCurrentTasks());
			}
		} else {
			taskManager.addToFloatingTasks(taskToEdit);
			taskToEdit.setList(taskManager.getFloatingTasks());
		}
				
		if (Controller.hasTask(taskToEdit)) {
			Controller.highlightTask(taskToEdit);
		} else {
			if (!taskToEdit.hasDeadline()) {
				Controller.displayTasks(Constants.FLOATING_TASKS, taskManager.getFloatingTasks());
			} else if (taskToEdit.isOverdue()) {
				Controller.displayTasks(Constants.OVERDUE_TASKS, taskManager.getOverdueTasks());
			} else {
				Controller.displayTasks(Constants.CURRENT_TASKS, taskManager.getCurrentTasks());
			}
			
			Controller.highlightTask(taskToEdit);
		}
		
		taskManager.saveData();
		
		return MESSAGE_SUCCESS;
	}
	
	
	public String getTitle() {
	    return this.title;
	}
	
	public Date getStartDate() {
	    return this.startDate;
	}
	
	public Date getEndDate() {
	    return this.endDate;
	}
	
	public RepeatFrequency getRepeatFrequency() {
	    return this.repeatFrequency;
	}
	
	public String getPlace() {
	    return this.place;
	}
	
	public ICategory getCategory() {
	    return this.category;
	}
	
	public String getNotes() {
	    return this.notes;
	}

    @Override
    public ICommand getInverseCommand() {
        if (this.inverseCommand == null) {
            this.inverseCommand = createInverseCommand();
        }
        return this.inverseCommand;
    }
    
    private ICommand createInverseCommand() {
        EditCommand inverseCommand = new EditCommand();
        inverseCommand.setTask(this.task);
        
        if (this.title != null) {
            inverseCommand.setTitle(this.task.getTitle());
        }
        if (this.category != null) {
            inverseCommand.setCategory(this.task.getCategory());
        }
        if (this.startDate != null) {
            inverseCommand.setStartDate(this.task.getStartDate());
        }
        if (this.endDate != null) {
            inverseCommand.setEndDate(this.task.getEndDate());
        }
        if (this.notes != null) {
            inverseCommand.setNotes(this.task.getNotes());
        }
        if (this.place != null) {
            inverseCommand.setPlace(this.task.getPlace());
        }
        if (this.repeatFrequency != null) {
            inverseCommand.setRepeatFrequency(this.task.getRepeatFrequency());
        }
        
        return inverseCommand;
    }

}
