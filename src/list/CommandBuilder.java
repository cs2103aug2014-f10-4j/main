package list;

import java.awt.Color;

import list.model.Date;
import list.model.ICategory;
import list.model.ITask;

/**
 * A builder class for generating various command objects
 * which implements ICommand. This class should only be used by 
 * Parser.
 * 
 * @author andhieka, michael
 */
public class CommandBuilder {
	private CommandType commandType = null;
	private String title = null;
	private Date startDate = null;
	private Date endTime = null;
	private RepeatFrequency repeatFrequency = null;
	private String place = null;
	private ICategory category = null;
	private String notes = null;
	private ITask task = null;
	private Color color = null;
	
	@SuppressWarnings("serial")
    class CommandTypeNotSetException extends Exception { };
			
	public static enum RepeatFrequency {
		DAILY, WEEKLY, MONTHLY, NONE;
	
		static boolean isValidRepeatFrequencyType(String repeatFrequency) {
			try {
				valueOf(repeatFrequency.trim().toUpperCase());
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
	}
	
	static enum CommandType {
		ADD, EDIT, DELETE, DISPLAY, MARK, CLOSE, UNMARK,
		UNDO, REDO, PREV, NEXT,
		CATEGORY_ADD, CATEGORY_EDIT, CATEGORY_DELETE
	}
	
	CommandBuilder setCommandType(CommandType commandType) {
		this.commandType = commandType;
		return this;
	}
	
	CommandBuilder setTitle(String title) {
	    this.title = title;
		return this;
	}
	
	CommandBuilder setStartDate(Date startDate) {
	    this.startDate = startDate;
		return this;
	}
	
	CommandBuilder setEndDate(Date endTime) {
	    this.endTime = endTime;
		return this;
	}

	CommandBuilder setRepeatFrequency(RepeatFrequency repeatFrequency) {
	    this.repeatFrequency = repeatFrequency;
		return this;
	}

	CommandBuilder setPlace(String place) {
	    this.place = place;
		return this;
	}

	CommandBuilder setCategory(ICategory category) {
	    this.category = category;
		return this;
	}

	CommandBuilder setNotes(String notes) {
	    this.notes = notes;
		return this;
	}
	
	CommandBuilder setObjectNumber(Integer taskNumber) {
	    if (taskNumber != null) {
	        this.task = Controller.getTaskWithNumber(taskNumber);
	    }
	    return this;
	}
	
	CommandBuilder setColor(Color color) {
	    this.color = color;
	    return this;
	}

	/**
	 * This method uses the given information to construct
	 * the corresponding command object.
	 * 
	 * @return a command object implementing ICommand.
	 */
	ICommand getCommandObject() throws CommandTypeNotSetException {
		if (commandType == null) {
			throw new CommandTypeNotSetException();
		}
		ICommand command;
		switch (commandType) {
			case ADD:
			    command = new AddCommand(title, startDate, endTime,
			            repeatFrequency, place, category, notes);
			    break;
			case EDIT:
			    command = new EditCommand(task, title, startDate, endTime,
			            repeatFrequency, place, category, notes);
			    break;
			case DISPLAY:
				command = new DisplayCommand(task);
				break;
			case DELETE:
				command = new DeleteCommand(task);
				break;
			case CLOSE:
				command = new CloseCommand();
				break;
			case MARK:
				command = new MarkCommand(task);
				break;
			case UNMARK:
			    command = new UnmarkCommand(task);
			    break;
			case UNDO:
			    command = new UndoCommand();
			    break;
			case REDO:
			    command = new RedoCommand();
			    break;
			case PREV:
			    command = new PrevCommand();
			    break;
			case NEXT:
			    command = new NextCommand();
		        break;
		    default:
			    throw new CommandTypeNotSetException();    
		}
		return command;
	}
	
}
