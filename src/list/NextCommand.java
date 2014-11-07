package list;

import java.io.IOException;

import list.view.IUserInterface;
import list.view.RootWindowController;

public class NextCommand implements ICommand {
    
    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        boolean success = Controller.next();
        return success ? "Next page displayed" : "End of list";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

}
