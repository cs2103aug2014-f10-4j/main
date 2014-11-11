//@author A0113672L
package list;

public interface IParser {
	@SuppressWarnings("serial")
    class ParseException extends Exception {
        public ParseException(String string) {
            super(string);
        }
        public ParseException() {
            super();
        }
    };
	
	/**
	 * Processes a given input into a corresponding command object
	 * 
	 * @param input the user input read from console
	 * @return command object implementing <code>ICommand</code>
	 * @throws ParseException if input string is invalid.
	 */
	ICommand parse(String input) throws ParseException;
	
	void append(String input) throws ParseException;
	
	String getExpectedInputs();
	
	ICommand finish() throws ParseException;
	
	void clear();
	
}
