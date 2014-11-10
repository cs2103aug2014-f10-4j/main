//@author A0113672L
package list;

import list.model.Task;

import org.junit.Test;

public class TaskTest {
    
    /**
     * null notes field is an valid condition and should not raise an exception.
     * @author andhieka
     * @throws Exception
     */
    @Test
    public void shouldNotThrowExceptionEvenWhenNotesIsNull() throws Exception {
        Task task = new Task();
        task.setNotes(null);
    }
    

}
