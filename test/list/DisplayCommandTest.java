//@author A0094022R
package list;

import static org.junit.Assert.assertEquals;
import javafx.application.Application;
import list.AddCommand;
import list.DisplayCommand;
import list.TaskManager;
import list.model.Date;
import list.model.ITask;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class DisplayCommandTest {
	
	private static final String TITLE = "Test Should Get Correct Task";
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
	
	@Before
	public void initializeTest() throws Exception {
		taskManager.clearTasks();

		AddCommand addCommand = new AddCommand().setTitle(TITLE).setEndDate(new Date("19-10-2014"));
		addCommand.execute();
	}
	
	@Test
	@Ignore
	public void test() throws Exception {
	    ITask task = taskManager.getAllTasks().get(0);
	    DisplayCommand dc = new DisplayCommand(task);
	    dc.execute();
	    
	    assertEquals(task, Controller.getLastDisplayedTaskDetail());
	}
	
}
