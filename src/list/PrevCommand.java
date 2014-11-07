package list;

import java.io.IOException;

import list.view.IUserInterface;
import list.view.MainController;

public class PrevCommand implements ICommand {
    
    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        boolean success = Controller.back();
        return success ? "Previous page displayed" : "End of list";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
