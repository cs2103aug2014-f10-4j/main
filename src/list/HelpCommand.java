//@author A0113672L
package list;

import java.io.IOException;

public class HelpCommand implements ICommand {

    @Override
    public String execute() throws CommandExecutionException, IOException {
    	Controller.displayHelp();
        return null;
    }

    @Override
    public ICommand getInverseCommand() {
        return null;
    }

}
