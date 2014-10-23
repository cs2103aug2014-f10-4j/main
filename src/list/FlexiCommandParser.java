package list;

/**
 * An extension of Parser class that supports a more flexible command (FlexiCommand)
 * It works by pre-processing user input string into a more formal LIST syntax,
 * and then passing it to the Parser to process.
 * 
 * @author Shotaro Watanabe
 */
public class FlexiCommandParser extends Parser {

    
    @Override
    public ICommand parse(String input) throws ParseException {
    	String listSyntax = preprocessUserInput(input);
    	ICommand result = super.parse(listSyntax);
        return result;
    }
    
    /**
     * Pre-processes user input by replacing informal language as much
     * as possible into LIST formal syntax. 
     */
    private String preprocessUserInput(String input) {
    	// add functions here. please separate to small functions. 
    	
    	return input;
    }
    
    
    
}
