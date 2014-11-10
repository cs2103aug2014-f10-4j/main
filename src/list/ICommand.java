//@author A0113672L
package list;

import java.io.IOException;

interface ICommand {	
	@SuppressWarnings("serial")
    public class CommandExecutionException extends Exception {
	    public CommandExecutionException(String message) {
	        super(message);
	    }
	};
	
	/**
	 * Executes this command.
	 * 
	 * @return the response to be shown in the console.
	 * @throws CommandExecutionException  
	 * @throws IOException  
	 */
	String execute() throws CommandExecutionException, IOException;
	
	
	/**
	 * Returns the ICommand object necessary to undo the operation.
	 * @return ICommand
	 */
	ICommand getInverseCommand();
	
}
