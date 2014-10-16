package list;

import static org.junit.Assert.assertEquals;
import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;
import list.ICommand.InvalidTaskNumberException;

import org.junit.Before;
import org.junit.Test;

public class DisplayCommandTest {
	
	private final String TITLE = "test";
	private final Date START_TIME = null;
	private Date END_TIME = null;
	private final RepeatFrequency REPEAT_FREQUENCY = RepeatFrequency.DAILY;
	private final String PLACE = null;
	private final ICategory CATEGORY = null;
	private final String NOTES = null;
	
	@Before
	public void initializeTest() throws Exception {
		TaskManager.clearTasks();
		END_TIME = new Date(1,1,1);
        
		AddCommand addCommand = new AddCommand(TITLE, START_TIME, END_TIME, 
											   REPEAT_FREQUENCY, PLACE, 
											   CATEGORY, NOTES);
		addCommand.execute();
	}
	
	@Test
	public void shouldGetTheCorrectTask() throws InvalidTaskNumberException {
		int taskNumber = 1;
		DisplayCommand displayCommand = new DisplayCommand(taskNumber);
		displayCommand.execute();
		
		ITask selectedTask = TaskManager.getTask(taskNumber);
		
		assertEquals(TITLE, selectedTask.getTitle());
		assertEquals(START_TIME, selectedTask.getStartTime());
		assertEquals(END_TIME, selectedTask.getEndTime());
		assertEquals(REPEAT_FREQUENCY, selectedTask.getRepeatFrequency());
		assertEquals(PLACE, selectedTask.getPlace());
		assertEquals(CATEGORY, selectedTask.getCategory());
		assertEquals(NOTES, selectedTask.getNotes());
	}

}
