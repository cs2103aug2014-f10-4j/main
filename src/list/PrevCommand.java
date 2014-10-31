package list;

import java.io.IOException;

import list.view.MainController;

public class PrevCommand implements ICommand {

    private IUserInterface userInterface = MainController.getInstance();
    
    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        boolean success = userInterface.back();
        return success ? "Previous page displayed" : "End of list";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
