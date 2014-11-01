package list;

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
	private CommandType mCommandType = null;
	private String mTitle = null;
	private Date mStartDate = null;
	private Date mEndTime = null;
	private RepeatFrequency mRepeatFrequency = null;
	private String mPlace = null;
	private ICategory mCategory = null;
	private String mNotes = null;
	private ITask task = null;
	
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
		UNDO, REDO, PREV, NEXT
	}
	
	CommandBuilder setCommandType(CommandType commandType) {
		mCommandType = commandType;
		return this;
	}
	
	CommandBuilder setTitle(String title) {
		mTitle = title;
		return this;
	}
	
	CommandBuilder setStartDate(Date startDate) {
		mStartDate = startDate;
		return this;
	}
	
	CommandBuilder setEndDate(Date endTime) {
		mEndTime = endTime;
		return this;
	}

	CommandBuilder setRepeatFrequency(RepeatFrequency repeatFrequency) {
		mRepeatFrequency = repeatFrequency;
		return this;
	}

	CommandBuilder setPlace(String place) {
		mPlace = place;
		return this;
	}

	CommandBuilder setCategory(ICategory category) {
		mCategory = category;
		return this;
	}

	CommandBuilder setNotes(String notes) {
		mNotes = notes;
		return this;
	}
	
	CommandBuilder setTaskNumber(Integer taskNumber) {
	    if (taskNumber != null) {
	        this.task = Controller.getTaskWithNumber(taskNumber);
	    }
	    return this;
	}

	/**
	 * This method uses the given information to construct
	 * the corresponding command object.
	 * 
	 * @return a command object implementing ICommand.
	 */
	ICommand getCommandObject() throws CommandTypeNotSetException {
		if (mCommandType == null) {
			throw new CommandTypeNotSetException();
		}
		ICommand command;
		switch (mCommandType) {
			case ADD:
			    command = new AddCommand(mTitle, mStartDate, mEndTime,
			            mRepeatFrequency, mPlace, mCategory, mNotes);
			    break;
			case EDIT:
			    command = new EditCommand(task, mTitle, mStartDate, mEndTime,
			            mRepeatFrequency, mPlace, mCategory, mNotes);
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
