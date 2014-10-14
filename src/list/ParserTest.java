package list;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
    String COMMAND_EDIT = "edit 157 -t title with multiple words -n testing notes. -s 23-05-2014 -d 28-05-2014";
    String COMMAND_ADD = "add --title the original title -n a random note.";
    
    IParser mParser = new Parser();
    
    
    @Test
    public void testEditCommand() throws Exception {
        ICommand cmd = mParser.parse(COMMAND_EDIT);
        assertTrue(cmd instanceof EditCommand);
        EditCommand editCommand = (EditCommand) cmd;
        assertEquals("title with multiple words", editCommand.getTitle());
        assertEquals("testing notes.", editCommand.getNotes());
        assertEquals(null, editCommand.getRepeatFrequency());
        
        Date startDate  = new Date(23, 5, 2014);
        assertEquals(startDate, editCommand.getStartTime());
        
        Date endDate = new Date(28, 5, 2014);
        assertEquals(endDate, editCommand.getEndTime());

    }
    
    @Test
    public void testAddCommand() throws Exception {
        ICommand addCommand = mParser.parse(COMMAND_ADD);
        assertTrue(addCommand instanceof AddCommand);
    }
    

}
