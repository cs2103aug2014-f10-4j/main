package list;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {
    
    @Test
    public void nullPointerTest() throws Exception {
        Task task = new Task();
        task.setNotes(null);
        assertNull(task.getNotes());
    }
    
    

}
