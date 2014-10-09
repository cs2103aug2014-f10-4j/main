package list;

import static org.junit.Assert.*;

import list.CommandBuilder.RepeatFrequency;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the AddCommand class
 * 
 * @author Michael
 *
 */
public class AddCommandTest {

	private final String TITLE = "test";
	private final Date START_TIME = new Date(0,0,0);
	private final Date END_TIME = new Date(0,0,0);
	private final RepeatFrequency REPEAT_FREQUENCY = RepeatFrequency.DAILY;
	private final String PLACE = null;
	private final String CATEGORY = null;
	private final String NOTES = null;
	
	@Before
	public void initializeTest() {
		TaskManager.clearTasks();
	}
	
	@Test
	public void shouldIncreaseNumberOfTasksByOne() {
		
		int initialNumberOfTasks = TaskManager.getNumberOfTasks();
		
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		addCommand.execute();
		
		assertEquals(initialNumberOfTasks + 1, TaskManager.getNumberOfTasks());
	}

	@Test
	public void shouldAddTheCorrectTask() {
		int initialNumberOfTasks = TaskManager.getNumberOfTasks();
	
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		
		addCommand.execute();
		
		ITask newlyAddedTask = TaskManager.getTask(initialNumberOfTasks + 1);
		
		assertEquals(TITLE, newlyAddedTask.getTitle());
		assertEquals(START_TIME, newlyAddedTask.getStartTime());
		assertEquals(END_TIME, newlyAddedTask.getEndTime());
		assertEquals(REPEAT_FREQUENCY, newlyAddedTask.getRepeatFrequency());
		assertEquals(PLACE, newlyAddedTask.getPlace());
		assertEquals(CATEGORY, newlyAddedTask.getCategory());
		assertEquals(NOTES, newlyAddedTask.getNotes());
	}	
}
