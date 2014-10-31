package list;

import java.io.IOException;

import list.view.MainController;

public class NextCommand implements ICommand {

    private IUserInterface userInterface = MainController.getInstance();
    
    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        userInterface.next();
        return "Next page displayed";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

}
