package list;

import static org.junit.Assert.assertEquals;
import list.AddCommand;
import list.Date;
import list.DeleteCommand;
import list.ICategory;
import list.ITask;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;
import list.ICommand.InvalidTaskNumberException;

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
	
	@Test(expected = InvalidTaskNumberException.class)
	public void shouldHandleInvalidTaskNumberSmallerThanOne() 
			throws InvalidTaskNumberException {
		int numberSmallerThanOne = 0;
		
		DeleteCommand deleteCommand = new DeleteCommand(numberSmallerThanOne);
		deleteCommand.execute();
	}
	
	@Test(expected = InvalidTaskNumberException.class)
	public void shouldHandleInvalidTaskNumberGreaterThanTotalTasks() 
			throws InvalidTaskNumberException {
		int numberGreaterThanTotalNumberOfTasks = taskManager.getNumberOfTasks() + 1;
		
		DeleteCommand deleteCommand = new DeleteCommand(numberGreaterThanTotalNumberOfTasks);
		deleteCommand.execute();
	}
	 
	@Test
	public void shouldDecreaseNumberOfTasksByOne() throws InvalidTaskNumberException {
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
		
		DeleteCommand deleteCommand = new DeleteCommand(1);
		deleteCommand.execute();
				
		assertEquals(initialNumberOfTasks - 1, taskManager.getNumberOfTasks());
	}
	
	@Test
	public void deletedTaskShouldNotExistAnymore() throws InvalidTaskNumberException {
		int taskNumber = 1;
		ITask task = taskManager.getTask(taskNumber);
		
		DeleteCommand deleteCommand = new DeleteCommand(taskNumber);
		deleteCommand.execute();
		
		assertEquals(false, taskManager.hasTask(task));
	}

}
