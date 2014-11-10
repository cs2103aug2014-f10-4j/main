//@author A0113672L
package list;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.application.Platform;
import list.model.ITask;
import list.model.Task;
import list.model.ITask.TaskStatus;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class MarkCommandTest {
	
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
	        Thread.sleep(1000);
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
		taskOne.setStatus(TaskStatus.PENDING);
		
		ITask taskTwo = new Task();
		taskTwo.setTitle("task 2");
		taskTwo.setStatus(TaskStatus.PENDING);
		
		taskManager.addTask(taskOne);
		taskManager.addTask(taskTwo);
		Controller.displayTasksBasedOnDisplayMode("floating");
	}
	
	@Test
	public void shouldChangeTaskStatusToDone() throws Exception {
		int taskNumber = 1;
		ITask taskToMarkAsDone = Controller.getTaskWithNumber(taskNumber);
		MarkCommand markCommand = new MarkCommand(taskToMarkAsDone);
		
		markCommand.execute();
				
		assertEquals(TaskStatus.DONE, taskToMarkAsDone.getStatus());
	}
	
	@Test
	public void shouldReturnSuccessMessageUponSuccessfulOperation() 
			throws Exception {
		int taskNumber = 1;
		MarkCommand markCommand = new MarkCommand(Controller.getTaskWithNumber(taskNumber));
		
		String reply = markCommand.execute();
				
		assertEquals("Task is marked as done successfully.", reply);
	}

}
