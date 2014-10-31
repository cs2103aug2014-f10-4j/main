package list;

import java.io.IOException;

import list.view.MainController;

public class PrevCommand implements ICommand {

    private IUserInterface userInterface = MainController.getInstance();
    
    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        userInterface.back();
        return "Displayed previous page";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
