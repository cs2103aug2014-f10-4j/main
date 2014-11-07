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
    private static final String MESSAGE_INVALID_CATEGORY = "No such category exists";
    
    private String title;
    
    public DisplayCategoryCommand(String title) {
		this.title = title;
	}
    
	@Override
	public String execute() throws CommandExecutionException {
		if (title == null) {
			Controller.displayCategories();
			return MESSAGE_DISPLAYING;
		} else {
			if (Controller.changeDisplayMode(title)) {
				return MESSAGE_DISPLAYING;
			} else {
				return MESSAGE_INVALID_CATEGORY;
			}
		}		
	}

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

	

}
