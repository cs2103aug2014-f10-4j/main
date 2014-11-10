//@author A0126722L
package list;

public class DisplayCategoryCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING = "Displaying...";
    private static final String MESSAGE_INVALID_CATEGORY = "No such category exists";
    
    private String categoryName;
    
    
    public DisplayCategoryCommand(String categoryName) {
        this.categoryName = categoryName;
	}
    
	@Override
	public String execute() throws CommandExecutionException {
		if (categoryName == null) {
			Controller.displayCategories();
			return MESSAGE_DISPLAYING;
		} else {
			boolean success = Controller.displayTasksBasedOnDisplayMode(categoryName);
			if (success) {
				Controller.refreshUI();
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
