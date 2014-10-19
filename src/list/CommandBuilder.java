package list;

import junit.extensions.RepeatedTest;

/**
 * A builder class for generating various command objects
 * which implements ICommand. This class should only be used by 
 * Parser.
 * 
 * @author andhieka, michael
 */
class CommandBuilder {
	private CommandType mCommandType = null;
	private String mTitle = null;
	private Date mStartDate = null;
	private Date mEndTime = null;
	private RepeatFrequency mRepeatFrequency = null;
	private String mPlace = null;
	private ICategory mCategory = null;
	private String mNotes = null;
	private Integer mTaskNumber = null;
	
	class CommandTypeNotSetException extends Exception { };
			
	static enum RepeatFrequency {
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
		ADD, EDIT, DELETE, DISPLAY, CLOSE
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
	    mTaskNumber = taskNumber;
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
		    command = new EditCommand(mTaskNumber, mTitle, mStartDate, mEndTime,
		            mRepeatFrequency, mPlace, mCategory, mNotes);
		    break;
		case DISPLAY:
			command = new DisplayCommand(mTaskNumber);
			break;
		case DELETE:
			command = new DeleteCommand(mTaskNumber);
			break;
		case CLOSE:
			command = new CloseCommand();
			break;
	    default:
		    throw new CommandTypeNotSetException();    
		}
		return command;
	}
	
}
