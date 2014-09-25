package list;
import java.util.Calendar;

/**
 * A builder class for generating various command objects
 * which implements ICommand. This class should only be used by 
 * Parser.
 * 
 * @author andhieka, michael
 */
class CommandBuilder {
	private CommandType mCommandType;
	private String mTitle;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private RepeatFrequency mRepeatFrequency;
	private String mPlace;
	private String mCategory;
	private String mNotes;
	
	class CommandTypeNotSetException extends Exception { };
	
	static enum RepeatFrequency {
		DAILY, WEEKLY, MONTHLY, NONE
	}
	
	static enum CommandType {
		ADD, EDIT, DELETE, DISPLAY
	}
	
	CommandBuilder setCommandType(CommandType commandType) {
		mCommandType = commandType;
		return this;
	}
	
	CommandBuilder setTitle(String title) {
		mTitle = title;
		return this;
	}
	
	CommandBuilder setStartTime(Calendar startTime) {
		mStartTime = startTime;
		return this;
	}
	
	CommandBuilder setEndTime(Calendar endTime) {
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

	CommandBuilder setCategory(String category) {
		mCategory = category;
		return this;
	}

	CommandBuilder setNotes(String notes) {
		mNotes = notes;
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
		//TODO: a lot of things
		return null;
	}
	
}
