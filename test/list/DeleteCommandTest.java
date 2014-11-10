//@author A0113672L
package list;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javafx.application.Application;
import list.AddCommand;
import list.DeleteCommand;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
     
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
