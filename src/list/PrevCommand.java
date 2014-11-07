package list;

import java.io.IOException;

import list.view.IUserInterface;

public class PrevCommand implements ICommand {
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        boolean success = Controller.back();
        return success ? "Previous page displayed" : "End of list";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
