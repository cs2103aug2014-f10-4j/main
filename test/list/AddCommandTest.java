package list;

import static org.junit.Assert.*;

import java.awt.Color;

import list.AddCommand;
import list.Category;
import list.Date;
import list.ICategory;
import list.ITask;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the AddCommand class
 * 
 * @author Michael. Edited by andhieka (on 19-10-2014).
 *
 */
public class AddCommandTest {

	private TaskManager taskManager = TaskManager.getInstance();
	
	@Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();
	}
	
	@Test
	public void shouldIncreaseNumberOfTasksByOne() throws Exception {
		
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
		
		AddCommand addCommand = new AddCommand();
		addCommand.setTitle("Testing").setEndDate(new Date("19-10-2014"));
		
		addCommand.execute();
		
		assertEquals(initialNumberOfTasks + 1, taskManager.getNumberOfTasks());
	}

	@Test
	public void shouldAddTheCorrectTask() throws Exception {
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
		
		String title = "Should add the correct task.";
		Date startDate = new Date("11-12-2013");
		Date endDate = new Date("31-12-2013");
		RepeatFrequency repeatFrequency = RepeatFrequency.DAILY;
		String place = "The school of computing";
		ICategory category = new Category().setName("Software Engineering");
		String notes = "A task must be added with complete set of properties.";
		
		AddCommand addCommand = new AddCommand();
		addCommand.setTitle(title)
		    .setStartDate(startDate)
		    .setEndDate(endDate)
		    .setRepeatFrequency(repeatFrequency)
		    .setPlace(place)
		    .setCategory(category)
		    .setNotes(notes);
		addCommand.execute();
		
		ITask newlyAddedTask = taskManager.getTask(initialNumberOfTasks + 1);
		
		assertEquals(title, newlyAddedTask.getTitle());
		assertEquals(startDate, newlyAddedTask.getStartDate());
		assertEquals(endDate, newlyAddedTask.getEndDate());
		assertEquals(repeatFrequency, newlyAddedTask.getRepeatFrequency());
		assertEquals(place, newlyAddedTask.getPlace());
		assertEquals(category, newlyAddedTask.getCategory());
		assertEquals(notes, newlyAddedTask.getNotes());
	}	
	
	@Test
	public void shouldMaintainListOfTasksSortedAfterAddingTasks() throws Exception {
	    Date firstDate = new Date(2, 1, 2014);
        Date secondDate = new Date(1, 1, 2014);
        Date thirdDate = new Date(3, 1, 2014);

        AddCommand firstAddCommand = new AddCommand().setTitle("Task 1").setEndDate(firstDate);
        AddCommand secondAddCommand = new AddCommand().setTitle("Task 2").setEndDate(secondDate);
        AddCommand thirdAddCommand = new AddCommand().setTitle("Task 3").setEndDate(thirdDate);
        firstAddCommand.execute();
        secondAddCommand.execute();
        thirdAddCommand.execute();

        assertEquals(true, taskManager.isListOfTasksSorted());
	}
	
	@Test
	public void shouldDefaultToNonRepeating() throws Exception {
	    AddCommand command = new AddCommand();
	    command.setTitle("Testing").setEndDate(new Date("01-01-2001"));
	    command.execute();
	    
	    ITask task = taskManager.getTask(1);
	    assertEquals("Testing", task.getTitle());
	    assertEquals(RepeatFrequency.NONE, task.getRepeatFrequency());
	}
}
