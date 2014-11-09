package list;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Scanner;

import list.IParser.ParseException;
import list.model.Date;

import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
    String COMMAND_ADD = "add -t the original title -n a random note.";
    
    CommandParser parser = new CommandParser();
    
    @Before
    public void setup() throws Exception{
        parser.clear();
    }
    
    @Test
    public void expectingCommandTypes() throws Exception {
        String actions = parser.getExpectedInputs();
        assertNotNull(actions);        
        
        parser.append("add");
        
        String parameters = parser.getExpectedInputs();
        assertNotNull(parameters);
        
        parser.append("-d on saturday 8pm");
        parser.finish();
        
        parser.clear();
        
        parser.append("cat edit");
        assertTrue(parser.getExpectedInputs().contains("category name"));

        parser.append("student exchange");
        assertTrue(parser.getExpectedInputs().contains("-c"));
        assertTrue(parser.getExpectedInputs().contains("-t"));
    }
    
    @Test(expected = ParseException.class)
    public void shouldRejectTaskSpanningMoreThanOneDay() throws Exception {
    	String commandString = "add -t long task -s Nov 12 4pm -d Nov 13 4pm";
    	parser.parse(commandString);
    }
    
    @Test
    public void testAddCommand() throws Exception {
        ICommand command = parser.parse(COMMAND_ADD);
        assertTrue(command instanceof AddCommand);
    }

}
