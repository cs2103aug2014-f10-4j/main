package list;

import java.io.IOException;

public class HelpCommand implements ICommand {

    @Override
    public String execute() throws CommandExecutionException, IOException {
        // TODO Auto-generated method stub
    	Controller.displayHelp();
        return null;
    }

    @Override
    public ICommand getInverseCommand() {
        // TODO Auto-generated method stub
        return null;
    }

}
