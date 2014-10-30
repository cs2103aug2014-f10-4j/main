package list;

/**
 * A command to close the program.
 * @author andhieka
 *
 */
public class CloseCommand implements ICommand {
	private final String MESSAGE_EXIT = "Program is closing...";
	
	@Override
	public String execute() {
	    System.exit(0);
	    return MESSAGE_EXIT;
	}

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
