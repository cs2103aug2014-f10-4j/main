package list;

import java.util.Calendar;

import list.CommandBuilder.RepeatFrequency;

/**
 * An example implementation of ICommand.
 * Other commands (Edit, Delete, etc.) should follow
 * this sample.
 * 
 * @author andhieka, michael
 */
public class AddCommand implements ICommand {
	
	AddCommand(String title,
               Calendar startTime,
               Calendar endTime,
               RepeatFrequency repeatFrequency,
               String place,
               String category,
               String notes) {
		//TODO: save this information in private variable
	    //so that it can be used during execute() command
	}
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
