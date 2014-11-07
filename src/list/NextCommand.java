package list;

import java.io.IOException;

public class NextCommand implements ICommand {
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        boolean success = Controller.next();
        return success ? "Next page displayed" : "End of list";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; // cannot be undone
    }

}
