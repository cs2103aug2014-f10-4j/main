package list;

import list.model.ICategory;
import list.model.ITask;

/**
 * 
 * @author Shotaro
 *
 */
public class DisplayCategoryCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING = "Displaying...";
    private static final String MESSAGE_INVALID_CATEGORY = "No such category exists";
    
    private ICategory category;
    
    public DisplayCategoryCommand(ICategory category) {
		this.category = category;
	}
    
	@Override
	public String execute() throws CommandExecutionException {
		if (category == null) {
			Controller.displayCategories();
			return MESSAGE_DISPLAYING;
		} else {
			if (Controller.changeDisplayMode(category.getName())) {
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
