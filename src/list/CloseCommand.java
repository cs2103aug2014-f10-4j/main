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
		//TODO: save necessary files, configuration, etc.
		return MESSAGE_EXIT;
	}

}
