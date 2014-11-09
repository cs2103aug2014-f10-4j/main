package list;

public class NextCommand implements ICommand {
    
	private static final String MESSAGE_SUCCESS = "Next page displayed";
	private static final String MESSAGE_ERROR = "End of list";
	
    @Override
    public String execute() {
        boolean success = Controller.next();
        if (success) {
        	return MESSAGE_SUCCESS;
        } else {
        	return MESSAGE_ERROR;
        }
    }

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

}
