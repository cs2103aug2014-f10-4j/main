//@author A0094022R
package list;

import static org.junit.Assert.*;
import javafx.application.Application;
import list.model.ITask;
import list.model.Task;
import list.model.ITask.TaskStatus;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class UnmarkCommandTest {

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
	public void initializeTest() {
		taskManager.clearTasks();
		
		ITask taskOne = new Task();
		taskOne.setTitle("task 1");
		taskOne.setStatus(TaskStatus.DONE);
		
		ITask taskTwo = new Task();
		taskTwo.setTitle("task 2");
		taskTwo.setStatus(TaskStatus.DONE);
		
		taskManager.addTask(taskOne);
		taskManager.addTask(taskTwo);
		
		Controller.displayTasksBasedOnDisplayMode("floating");
	}
	
	@Test
	public void shouldChangeTaskStatusToPending() throws Exception {
		int taskNumber = 1;
		ITask taskToUnmark = Controller.getTaskWithNumber(taskNumber);
		UnmarkCommand unmarkCommand = new UnmarkCommand(taskToUnmark);
		
		unmarkCommand.execute();
				
		assertEquals(TaskStatus.PENDING, taskToUnmark.getStatus());
	}
	
	@Test
	public void shouldReturnSuccessMessageUponSuccessfulOperation() 
			throws Exception {
		int taskNumber = 1;
		UnmarkCommand unmarkCommand = new UnmarkCommand(Controller.getTaskWithNumber(taskNumber));
		
		String reply = unmarkCommand.execute();
				
		assertEquals("Task is unmarked successfully.", reply);
	}
	
}
