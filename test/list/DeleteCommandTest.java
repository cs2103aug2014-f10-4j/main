package list;

import static org.junit.Assert.assertEquals;

import java.util.List;

import list.AddCommand;
import list.DeleteCommand;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.ICommand.CommandExecutionException;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.model.Date.InvalidDateException;

import org.junit.Before;
import org.junit.Test;

public class DeleteCommandTest {

	private TaskManager taskManager = TaskManager.getInstance();
	private final String TITLE = "test";
	private final Date START_TIME = null;
	private Date END_TIME = null;
	private final RepeatFrequency REPEAT_FREQUENCY = RepeatFrequency.DAILY;
	private final String PLACE = null;
	private final ICategory CATEGORY = null;
	private final String NOTES = null;
	
	@Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();
		END_TIME = new Date(1,1,1);
		
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		addCommand.execute();		
	}
	
	@Test
	public void shouldDecreaseNumberOfTasksByOne() throws Exception {
	    List<ITask> tasks = taskManager.getAllTasks();
		int initialNumberOfTasks = tasks.size();
		
		DeleteCommand deleteCommand = new DeleteCommand(tasks.get(0));
		deleteCommand.execute();

		assertEquals(initialNumberOfTasks - 1, taskManager.getAllTasks().size());
	}
	
	@Test
	public void deletedTaskShouldNotExistAnymore() throws Exception {
		int taskNumber = 1;
		ITask task = Controller.getTaskWithNumber(taskNumber);
		
		DeleteCommand deleteCommand = new DeleteCommand(task);
		deleteCommand.execute();
		
		assertEquals(false, taskManager.hasTask(task));
	}

}
