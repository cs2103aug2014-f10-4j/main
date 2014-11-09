package list;

import java.io.IOException;

public class PrevCommand implements ICommand {
	
	private static final String MESSAGE_SUCCESS = "Previous page displayed";
	private static final String MESSAGE_ERROR = "End of list";
    
	@Override
    public String execute() {
        boolean success = Controller.back();
        if (success) {
        	return MESSAGE_SUCCESS;
        } else {
        	return MESSAGE_ERROR;
        }
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
