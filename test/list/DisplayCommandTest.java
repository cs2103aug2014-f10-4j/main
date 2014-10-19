package list;

import static org.junit.Assert.assertEquals;
import list.AddCommand;
import list.Date;
import list.DisplayCommand;
import list.ICategory;
import list.ITask;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;
import list.ICommand.InvalidTaskNumberException;

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
	public void shouldRunWithoutException() throws Exception {
		int taskNumber = 1;
		DisplayCommand displayCommand = new DisplayCommand(taskNumber);
		displayCommand.execute();
		
		ITask selectedTask = taskManager.getTask(taskNumber);
		
		assertEquals(TITLE, selectedTask.getTitle());
		assertEquals(new Date("19-10-2014"), selectedTask.getEndDate());
	}

}
