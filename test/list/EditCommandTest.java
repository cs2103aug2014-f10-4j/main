//@author A0094022R
package list;

import static org.junit.Assert.assertEquals;
import javafx.application.Application;
import list.AddCommand;
import list.EditCommand;
import list.TaskManager;
import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.util.Utilities;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
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

		AddCommand addCommand = new AddCommand().setTitle(TITLE);
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
						
		ITask modifiedTask = taskManager.getAllTasks().get(0);
		
		assertEquals(newTitle, modifiedTask.getTitle());
		
	}
	
	@Test
	public void shouldOnlyEditTheStartTime() throws Exception {
		int taskNumber = 1;
		Date newStartDate = new Date(1,1,2014);
		
		EditCommand editCommand = new EditCommand().
		        setTask(Controller.getTaskWithNumber(taskNumber)).
		        setStartDate(newStartDate);
		editCommand.execute();

        ITask modifiedTask = taskManager.getAllTasks().get(0);
		
		assertEquals(TITLE, modifiedTask.getTitle());
		assertEquals(newStartDate, modifiedTask.getStartDate());
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
