package list;

import list.model.ITask;

/**
 * 
 * @author Shotaro
 *
 */
public class DisplayCategoryCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING = "Displaying ...";
    private static final String MESSAGE_INVALID_COMMAND = "Please make valid input.";
    
	@Override
	public String execute() throws CommandExecutionException {
	    Controller.displayCategories();
		
		return MESSAGE_DISPLAYING;
	}

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

}
