package list;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Scanner;

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
        Map<String, String> actions = parser.getExpectedInputs();
        assertNotNull(actions);        
        
        parser.append("add");
        
        Map<String, String> parameters = parser.getExpectedInputs();
        assertNotNull(parameters);
        
        parser.append("-t testing");
        assertEquals(parameters.size() - 1, parser.getExpectedInputs().size());
        
        parser.append("-d on saturday 8pm");
        parser.finish();
        
        parser.clear();
        parser.append("edit");
        assertEquals(1, parser.getExpectedInputs().size());
        
        parser.append("1");
        
        
        
        parser.clear();
        parser.append("cat edit");
        assertTrue(parser.getExpectedInputs().containsKey("Number"));

        parser.append("1");
        assertTrue(parser.getExpectedInputs().containsKey("-c"));
        assertTrue(parser.getExpectedInputs().containsKey("-t"));
    }
    
    @Test
    public void testAddCommand() throws Exception {
        ICommand command = parser.parse(COMMAND_ADD);
        assertTrue(command instanceof AddCommand);
    }

}
