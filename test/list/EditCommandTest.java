package list;

import static org.junit.Assert.assertEquals;
import list.AddCommand;
import list.Date;
import list.EditCommand;
import list.ICategory;
import list.ITask;
import list.TaskManager;
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
	
	private TaskManager taskManager = TaskManager.getInstance();
	private final String TITLE = "test";
	private final Date START_TIME = null;
	private Date END_TIME = null; //01-01-2014
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
	public void shouldOnlyEditTheTitle() throws Exception {
		int taskNumber = 1;
		String newTitle = "play";
		
		EditCommand editCommand = new EditCommand().
		        setTask(Controller.getTaskWithNumber(taskNumber)).
		        setTitle(newTitle);
		
		editCommand.execute();
						
		ITask modifiedTask = taskManager.getTask(taskNumber);
		
		assertEquals(newTitle, modifiedTask.getTitle());
		assertEquals(START_TIME, modifiedTask.getStartDate());
		assertEquals(END_TIME, modifiedTask.getEndDate());
		assertEquals(REPEAT_FREQUENCY, modifiedTask.getRepeatFrequency());
		assertEquals(PLACE, modifiedTask.getPlace());
		assertEquals(CATEGORY, modifiedTask.getCategory());
		assertEquals(NOTES, modifiedTask.getNotes());
	}
	
	@Test
	public void shouldOnlyEditTheStartTime() throws Exception {
		int taskNumber = 1;
		Date newStartDate = new Date(1,1,2014);
		
		EditCommand editCommand = new EditCommand().
		        setTask(Controller.getTaskWithNumber(taskNumber)).
		        setStartDate(newStartDate);
		editCommand.execute();
		
		ITask modifiedTask = taskManager.getTask(taskNumber);
		
		assertEquals(TITLE, modifiedTask.getTitle());
		assertEquals(newStartDate, modifiedTask.getStartDate());
		assertEquals(END_TIME, modifiedTask.getEndDate());
		assertEquals(REPEAT_FREQUENCY, modifiedTask.getRepeatFrequency());
		assertEquals(PLACE, modifiedTask.getPlace());
		assertEquals(CATEGORY, modifiedTask.getCategory());
		assertEquals(NOTES, modifiedTask.getNotes());
	}
	
	@Test
	public void shouldMaintainListOfTasksSortedAfterEditingTask() throws Exception {
		AddCommand addCommand = new AddCommand().
		        setTitle("task 1").
		        setEndDate(new Date(2,1,2014));
		
		addCommand.execute();
				
		int taskNumber = 2;
		EditCommand editCommand = new EditCommand().
		        setTask(Controller.getTaskWithNumber(taskNumber)).
		        setEndDate(new Date(2,1,2013));
		
		editCommand.execute();
				
		assertEquals(true, Utilities.isSorted(taskManager.getAllTasks()));
	}
}
