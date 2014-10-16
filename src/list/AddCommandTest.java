package list;

import static org.junit.Assert.*;

import java.awt.Color;

import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the AddCommand class
 * 
 * @author Michael
 *
 */
public class AddCommandTest {

	private TaskManager taskManager = TaskManager.getInstance();
	private final String TITLE = "test";
	private Date START_TIME = null;
	private Date END_TIME = null;
	private final RepeatFrequency REPEAT_FREQUENCY = RepeatFrequency.DAILY;
	private final String PLACE = null;
	private final ICategory CATEGORY = new Category().
	        setName("Default").
	        setColor(Color.BLACK);
	private final String NOTES = null;
	
	@Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();
		END_TIME = new Date(1,1,1);
	}
	
	@Test
	public void shouldIncreaseNumberOfTasksByOne() {
		
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
		
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		addCommand.execute();
		
		assertEquals(initialNumberOfTasks + 1, taskManager.getNumberOfTasks());
	}

	@Test
	public void shouldAddTheCorrectTask() {
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
	
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		
		addCommand.execute();
		
		ITask newlyAddedTask = taskManager.getTask(initialNumberOfTasks + 1);
		
		assertEquals(TITLE, newlyAddedTask.getTitle());
		assertEquals(START_TIME, newlyAddedTask.getStartTime());
		assertEquals(END_TIME, newlyAddedTask.getEndTime());
		assertEquals(REPEAT_FREQUENCY, newlyAddedTask.getRepeatFrequency());
		assertEquals(PLACE, newlyAddedTask.getPlace());
		assertEquals(CATEGORY, newlyAddedTask.getCategory());
		assertEquals(NOTES, newlyAddedTask.getNotes());
	}	
	
	@Test
	public void shouldMaintainListOfTasksSortedAfterAddingTasks() throws Exception {
	    Date firstDate = new Date(2, 1, 2014);
        Date secondDate = new Date(1, 1, 2014);
        Date thirdDate = new Date(3, 1, 2014);

        AddCommand firstAddCommand = new AddCommand("task 1", null, firstDate, 
									                REPEAT_FREQUENCY, PLACE, 
									                CATEGORY, NOTES);
        AddCommand secondAddCommand = new AddCommand("task 2", null, secondDate, 
										             REPEAT_FREQUENCY, PLACE, 
										             CATEGORY, NOTES);
        AddCommand thirdAddCommand = new AddCommand("task 3", null, thirdDate, 
								                    REPEAT_FREQUENCY, PLACE, 
								                    CATEGORY, NOTES);
        firstAddCommand.execute();
        secondAddCommand.execute();
        thirdAddCommand.execute();

        assertEquals(true, taskManager.isListOfTasksSorted());
	}
}
