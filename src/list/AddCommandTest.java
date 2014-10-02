package list;

import static org.junit.Assert.*;

import java.util.Calendar;

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

	@Before
	public void initializeTest() {
		TaskManager.clearTasks();
	}
	
	@Test
	public void shouldIncreaseNumberOfTasksByOne() {
		
		int initialNumberOfTasks = TaskManager.getNumberOfTasks();
		
		AddCommand addCommand = new AddCommand("test",
				Calendar.getInstance(), Calendar.getInstance(), RepeatFrequency.DAILY, "", "", "");
		
		addCommand.execute();
		
		assertEquals(initialNumberOfTasks + 1, TaskManager.getNumberOfTasks());
	}

	@Test
	public void shouldAddTheTaskWithCorrectTitle() {
		int initialNumberOfTasks = TaskManager.getNumberOfTasks();
		
		AddCommand addCommand = new AddCommand("test",
				Calendar.getInstance(), Calendar.getInstance(), RepeatFrequency.DAILY, "", "", "");
		
		addCommand.execute();
		
		ITask newlyAddedTask = TaskManager.getTask(initialNumberOfTasks + 1);
		
		assertEquals("test", newlyAddedTask.getTitle());
	}
	
}
