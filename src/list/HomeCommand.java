package list;

import java.io.IOException;

public class HomeCommand implements ICommand {

    @Override
    public String execute() throws CommandExecutionException, IOException {
        Controller.displayHome();
        Controller.refreshUI();
        return "Displaying Home";
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
