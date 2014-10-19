package list;

/**
 * An extension of Parser class that supports a more flexible command (FlexiCommand)
 * 
 * @author Shotaro Watanabe
 */
public class FlexiCommandParser extends Parser {

    
    @Override
    public ICommand parse(String input) throws ParseException {
        if (super.isListSyntax(input)) {
            return super.parse(input);
        }
        //FlexiCommand parser starts here
        
        return null;
    }
}
