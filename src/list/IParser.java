package list;

/**
 * This class parses user input into a command object.
 * It makes use of <code>CommandBuilder</code> class.
 * 
 * @author andhieka, michael
 */
interface IParser {
	class ParseException extends Exception { };
	
	/**
	 * Processes a given input into a corresponding command object
	 * 
	 * @param input the user input read from console
	 * @return command object implementing <code>ICommand</code>
	 * @throws ParseException if input string is invalid.
	 */
	ICommand parse(String input) throws ParseException;
	
}
