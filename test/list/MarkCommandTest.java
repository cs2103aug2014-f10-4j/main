package list;

import static org.junit.Assert.*;
import list.ICommand.CommandExecutionException;
import list.ICommand.InvalidTaskNumberException;
import list.ITask.TaskStatus;

import org.junit.Before;
import org.junit.Test;

public class MarkCommandTest {
	
	private TaskManager taskManager = TaskManager.getInstance();
	
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
		
		Controller.refreshUi();
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
