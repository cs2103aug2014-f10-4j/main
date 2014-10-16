package list;

import static org.junit.Assert.assertEquals;
import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;
import list.ICommand.InvalidTaskNumberException;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test EditCommand class
 * 
 * @author Michael
 *
 */
public class EditCommandTest {
	
	private final String TITLE = "test";
	private final Date START_TIME = null;
	private Date END_TIME = null; //01-01-2014
	private final RepeatFrequency REPEAT_FREQUENCY = RepeatFrequency.DAILY;
	private final String PLACE = null;
	private final ICategory CATEGORY = null;
	private final String NOTES = null;
	
	@Before
	public void initializeTest() throws Exception {
		TaskManager.clearTasks();
		END_TIME = new Date(1,1,1);
		
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);		
		addCommand.execute();
	}
	
	@Test(expected = InvalidTaskNumberException.class)
	public void shouldHandleInvalidTaskNumberSmallerThanOne() throws InvalidTaskNumberException {
		int numberSmallerThanOne = 0;
		
		EditCommand editCommand = new EditCommand(numberSmallerThanOne, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null);
		editCommand.execute();
	}
	
	@Test(expected = InvalidTaskNumberException.class)
	public void shouldHandleInvalidTaskNumberGreaterThanTotalTasks() 
			throws InvalidTaskNumberException {
		int numberGreaterThanTotalNumberOfTasks = TaskManager.getNumberOfTasks() + 1;
		
		EditCommand editCommand = new EditCommand(numberGreaterThanTotalNumberOfTasks, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null);
		editCommand.execute();
	}
	
	@Test
	public void shouldOnlyEditTheTitle() throws InvalidTaskNumberException {
		int taskNumber = 1;
		String newTitle = "play";
		
		EditCommand editCommand = new EditCommand(taskNumber, 
												  newTitle, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null);
		editCommand.execute();
						
		ITask modifiedTask = TaskManager.getTask(taskNumber);
		
		assertEquals(newTitle, modifiedTask.getTitle());
		assertEquals(START_TIME, modifiedTask.getStartTime());
		assertEquals(END_TIME, modifiedTask.getEndTime());
		assertEquals(REPEAT_FREQUENCY, modifiedTask.getRepeatFrequency());
		assertEquals(PLACE, modifiedTask.getPlace());
		assertEquals(CATEGORY, modifiedTask.getCategory());
		assertEquals(NOTES, modifiedTask.getNotes());
	}
	
	@Test
	public void shouldOnlyEditTheStartTime() throws InvalidTaskNumberException, InvalidDateException {
		int taskNumber = 1;
		Date newStartTime = new Date(1,1,2014);
		
		EditCommand editCommand = new EditCommand(taskNumber, 
												  null, 
												  newStartTime, 
												  null, 
												  null, 
												  null, 
												  null, 
												  null);
		editCommand.execute();
		
		ITask modifiedTask = TaskManager.getTask(taskNumber);
		
		assertEquals(TITLE, modifiedTask.getTitle());
		assertEquals(newStartTime, modifiedTask.getStartTime());
		assertEquals(END_TIME, modifiedTask.getEndTime());
		assertEquals(REPEAT_FREQUENCY, modifiedTask.getRepeatFrequency());
		assertEquals(PLACE, modifiedTask.getPlace());
		assertEquals(CATEGORY, modifiedTask.getCategory());
		assertEquals(NOTES, modifiedTask.getNotes());
	}
	
	@Test
	public void shouldMaintainListOfTasksSortedAfterEditingTask() 
			throws InvalidTaskNumberException, InvalidDateException {
		AddCommand addCommand = new AddCommand("task 1", null, new Date(2,1,2014), 
											   null, null, 
										   	   null, null);
		addCommand.execute();
				
		int taskNumber = 2;
		EditCommand editCommand = new EditCommand(taskNumber, 
												  null, 
												  null, 
												  new Date(2,1,2013), 
												  null, 
												  null, 
												  null, 
												  null);
		editCommand.execute();
				
		assertEquals(true, TaskManager.isListOfTasksSorted());
	}
}
