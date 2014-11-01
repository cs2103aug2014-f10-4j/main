package list;

import static org.junit.Assert.assertEquals;
import list.AddCommand;
import list.DisplayCommand;
import list.TaskManager;
import list.model.Date;
import list.model.ITask;
import org.junit.Before;
import org.junit.Test;

public class DisplayCommandTest {
	
	private static final String TITLE = "Test Should Get Correct Task";
    private TaskManager taskManager = TaskManager.getInstance();
	
	
	@Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();

		AddCommand addCommand = new AddCommand().setTitle(TITLE).setEndDate(new Date("19-10-2014"));
		addCommand.execute();
	}
	
	@Test
	public void test() throws Exception {
	    ITask task = taskManager.getAllTasks().get(0);
	    DisplayCommand dc = new DisplayCommand(task);
	    dc.execute();
	    
	    assertEquals(task, Controller.getLastDisplayedTaskDetail());
	}
	
}
