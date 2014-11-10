package list;

import static org.junit.Assert.*;
import javafx.application.Application;
import list.AddCommand;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.model.Category;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.util.Utilities;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class is used to test the AddCommand class
 * 
 * @author Michael. Edited by andhieka (on 19-10-2014).
 *
 */
//@author A0094022R

public class AddCommandTest {

	private TaskManager taskManager = TaskManager.getInstance();

	@Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    @BeforeClass
    public static void setup() throws Exception {
        Thread thread = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(Controller.class, new String[0]);
            }
        };
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
	
    @Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();
	}
	
	@Test
	public void shouldIncreaseNumberOfTasksByOne() throws Exception {
		
		AddCommand addCommand = new AddCommand();
		addCommand.setTitle("Testing").setEndDate(Date.getTodayMidnight());
		
		addCommand.execute();
		
		assertEquals(1, taskManager.getCurrentTasks().size());
	}

	@Test
	public void shouldAddTheCorrectTask() throws Exception {
		int initialNumberOfTasks = taskManager.getNumberOfTasks();
		
		String title = "Should add the correct task.";
		Date startDate = Date.tryParse("today 7am");
		Date endDate = Date.getTodayMidnight();
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
		
		ITask newlyAddedTask = taskManager.getCurrentTasks().get(0);
		
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

        assertEquals(true, Utilities.isSorted(taskManager.getAllTasks()));
	}
}
