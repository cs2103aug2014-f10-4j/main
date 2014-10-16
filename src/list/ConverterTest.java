package list;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import list.CommandBuilder.RepeatFrequency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	// String used as a key in JSONObject for Task
	private static final String KEY_TITLE = "title";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_START_TIME = "start_time";
	private static final String KEY_END_TIME = "end_time";
	private static final String KEY_REPEAT_FREQUENCY = "repeat_frequency";
	private static final String KEY_PLACE = "place";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_NOTES = "notes";
	
	// Tasks Details
	private static final String TASK_1_NOTES = "This is task 1 under test";
	private static final String TASK_1_TITLE = "Task 1";
	private static final String TASK_2_CATEGORY = "school";
	private static final String TASK_2_TITLE = "Task 2";
	private static final String TASK_3_TITLE = "Task 3";	
		
	private Converter converter;
	private List<ITask> tasks;
	private ITask taskOne;
	private ITask taskTwo;
	private ITask taskThree;
			
//	@Test
//	public void shouldConvertTaskToCorrectJsonObject() 
//			throws JSONException {
//		JSONObject taskInJson = converter.convertTaskToJson(taskOne);
//		
//		assertEquals(true, taskInJson.has(KEY_TITLE));
//		assertEquals(TASK_1_TITLE, taskInJson.get(KEY_TITLE));
//		assertEquals(true, taskInJson.has(KEY_DETAILS));
//		
//		JSONObject taskDetails = taskInJson.getJSONObject(KEY_DETAILS);
//		
//		assertEquals(new Date(1, 1, 2014).toString(), taskDetails.get(KEY_END_TIME));
//		assertEquals(TASK_1_NOTES, taskDetails.get(KEY_NOTES));
//		assertEquals(false, taskDetails.has(KEY_CATEGORY));
//		assertEquals(false, taskDetails.has(KEY_REPEAT_FREQUENCY));
//		assertEquals(false, taskDetails.has(KEY_START_TIME));
//		assertEquals(false, taskDetails.has(KEY_PLACE));		
//	}
//	
//	@Test
//	public void shouldConvertTaskWithOnlyTitleToJsonObject() 
//			throws JSONException {
//		ITask task = new Task();
//		task.setTitle("Task 3");
//		
//		JSONObject taskInJson = converter.convertTaskToJson(task);
//		
//		assertEquals(true, taskInJson.has(KEY_TITLE));
//		assertEquals("Task 3", taskInJson.get(KEY_TITLE));
//		assertEquals(true, taskInJson.has(KEY_DETAILS));
//		
//		JSONObject taskDetails = taskInJson.getJSONObject(KEY_DETAILS);
//		
//		assertEquals(0, taskDetails.length());		
//	}
		
	@Before
	public void initializeTest() {
		converter = new Converter();
		tasks = new ArrayList<ITask>();
		prepareTask();
	}
	
	@Test
	public void shouldConvertListOfTasksToCorrectJsonObject() 
			throws JSONException {	
		JSONArray tasksListInJson = converter.convertTasksListToJson(tasks);
		
		JSONObject firstTaskInJson = tasksListInJson.getJSONObject(0);
		JSONObject firstTaskDetail = firstTaskInJson.getJSONObject(KEY_DETAILS);
		
		JSONObject secondTaskInJson = tasksListInJson.getJSONObject(1);
		JSONObject secondTaskDetail = secondTaskInJson.getJSONObject(KEY_DETAILS);
		
		JSONObject thirdTaskInJson = tasksListInJson.getJSONObject(2);
		JSONObject thirdTaskDetail = thirdTaskInJson.getJSONObject(KEY_DETAILS);
		
		// Checking the first task
		assertEquals(TASK_1_TITLE, firstTaskInJson.get(KEY_TITLE));
		assertEquals(new Date(1, 1, 2014).toString(), firstTaskDetail.get(KEY_END_TIME));
		assertEquals(TASK_1_NOTES, firstTaskDetail.get(KEY_NOTES));
		assertEquals(false, firstTaskDetail.has(KEY_CATEGORY));
		assertEquals(false, firstTaskDetail.has(KEY_REPEAT_FREQUENCY));
		assertEquals(false, firstTaskDetail.has(KEY_START_TIME));
		assertEquals(false, firstTaskDetail.has(KEY_PLACE));
		
		// Checking the second task
		assertEquals(TASK_2_TITLE, secondTaskInJson.get(KEY_TITLE));
		assertEquals(new Date(2, 1, 2014).toString(), secondTaskDetail.get(KEY_START_TIME));
		assertEquals(TASK_2_CATEGORY, secondTaskDetail.get(KEY_CATEGORY));
		assertEquals("DAILY", secondTaskDetail.get(KEY_REPEAT_FREQUENCY));
		assertEquals(false, secondTaskDetail.has(KEY_END_TIME));
		assertEquals(false, secondTaskDetail.has(KEY_NOTES));
		assertEquals(false, secondTaskDetail.has(KEY_PLACE));
		
		// Checking the third task
		assertEquals(true, thirdTaskInJson.has(KEY_TITLE));
		assertEquals(TASK_3_TITLE, thirdTaskInJson.get(KEY_TITLE));
		assertEquals(true, thirdTaskInJson.has(KEY_DETAILS));
		assertEquals(0, thirdTaskDetail.length());	
		
		System.out.println(tasksListInJson.toString());
	}
	
	@Test
	public void shouldConvertJsonObjectToTask() throws JSONException {
		JSONObject originalTaskInJson = new JSONObject();
		JSONObject originalTaskDetail = new JSONObject();
		
		originalTaskDetail.put(KEY_END_TIME, new Date(1, 1, 2014));
		originalTaskDetail.put(KEY_NOTES, TASK_1_NOTES);
		originalTaskInJson.put(KEY_TITLE, TASK_1_TITLE);
		originalTaskInJson.put(KEY_DETAILS, originalTaskDetail);
		
		ITask taskFromJson = converter.convertJsonToTask(originalTaskInJson);
		assertEquals(taskOne.getTitle(), taskFromJson.getTitle());
		assertEquals(taskOne.getStartTime(), taskFromJson.getStartTime());
		assertEquals(taskOne.getEndTime(), taskFromJson.getEndTime());
		assertEquals(taskOne.getRepeatFrequency(), taskFromJson.getRepeatFrequency());
		assertEquals(taskOne.getPlace(), taskFromJson.getPlace());
		assertEquals(taskOne.getCategory(), taskFromJson.getCategory());
		assertEquals(taskOne.getNotes(), taskFromJson.getNotes());
	}
	
	private void prepareTask() {
		taskOne = new Task();
		taskOne.setTitle(TASK_1_TITLE)
			.setEndTime(new Date(1, 1, 2014))
			.setNotes(TASK_1_NOTES);
		taskTwo = new Task();
		taskTwo.setTitle(TASK_2_TITLE)
			.setStartTime(new Date(2, 1, 2014))
			.setCategory(new Category(TASK_2_CATEGORY))
			.setRepeatFrequency(RepeatFrequency.DAILY);	
		taskThree = new Task();
		taskThree.setTitle(TASK_3_TITLE);
		
		tasks.add(taskOne);
		tasks.add(taskTwo);	
		tasks.add(taskThree);
	}
	
}
