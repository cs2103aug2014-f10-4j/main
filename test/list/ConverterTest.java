package list;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import list.CommandBuilder.RepeatFrequency;
import list.Converter.CorruptedJsonObjectException;
import list.model.Category;
import list.model.Date;
import list.model.Date.InvalidDateException;
import list.model.ICategory;
import list.model.ITask;
import list.model.ITask.TaskStatus;
import list.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the functionality of Converter Class
 * 
 * @author Michael
 *
 */
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
	private static final String KEY_STATUS = "status";
	private static final String KEY_NAME = "name";
	private static final String KEY_COLOR = "color";
	
	// Tasks Details
	private static final String TASK_1_NOTES = "This is task 1 under test";
	private static final String TASK_1_TITLE = "Task 1";
	private static final String TASK_2_CATEGORY = "school";
	private static final String TASK_2_TITLE = "Task 2";
	private static final String TASK_3_TITLE = "Task 3";	
		
	// Category Details
	private static final String CATEGORY_1_NAME = "School";
	private static final Color CATEGORY_1_COLOR = Color.BLUE;
	private static final String CATEGORY_2_NAME = "Work";
	
	
	private Converter converter;
	private List<ITask> tasks;
	private ITask taskOne;
	private ITask taskTwo;
	private ITask taskThree;
	private List<ICategory> categories;
	private ICategory categoryOne;
	private ICategory categoryTwo;
	
	@Before
	public void initializeTest() throws InvalidDateException {
		converter = new Converter();
		tasks = new ArrayList<ITask>();
		categories = new ArrayList<ICategory>();
		prepareTask();
		prepareCategory();
	}
	
	@Test
	public void shouldConvertListOfTasksToCorrectJsonObject() 
			throws JSONException, InvalidDateException {	
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
		assertEquals("", firstTaskDetail.get(KEY_CATEGORY));
		assertEquals("NONE", firstTaskDetail.get(KEY_REPEAT_FREQUENCY));
		assertEquals("", firstTaskDetail.get(KEY_START_TIME));
		assertEquals("", firstTaskDetail.get(KEY_PLACE));
		assertEquals("PENDING", firstTaskDetail.get(KEY_STATUS));
		
		// Checking the second task
		assertEquals(TASK_2_TITLE, secondTaskInJson.get(KEY_TITLE));
		assertEquals(new Date(2, 1, 2014).toString(), secondTaskDetail.get(KEY_START_TIME));
		assertEquals(TASK_2_CATEGORY, secondTaskDetail.get(KEY_CATEGORY));
		assertEquals("DAILY", secondTaskDetail.get(KEY_REPEAT_FREQUENCY));
		assertEquals("", secondTaskDetail.get(KEY_END_TIME));
		assertEquals("", secondTaskDetail.get(KEY_NOTES));
		assertEquals("", secondTaskDetail.get(KEY_PLACE));
		assertEquals("PENDING", secondTaskDetail.get(KEY_STATUS));
		
		// Checking the third task
		assertEquals(true, thirdTaskInJson.has(KEY_TITLE));
		assertEquals(TASK_3_TITLE, thirdTaskInJson.get(KEY_TITLE));
		assertEquals(true, thirdTaskInJson.has(KEY_DETAILS));
		assertEquals("DONE", thirdTaskDetail.get(KEY_STATUS));
	}
	
	@Test
	public void shouldConvertJsonArrayToTaskLists() throws JSONException, 
			InvalidDateException, CorruptedJsonObjectException {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonTaskOne = new JSONObject();
		JSONObject jsonTaskDetailOne = new JSONObject();
		jsonTaskDetailOne.put(KEY_END_TIME, new Date(1, 1, 2014).toString());
		jsonTaskDetailOne.put(KEY_NOTES, TASK_1_NOTES);
		jsonTaskOne.put(KEY_TITLE, TASK_1_TITLE);
		jsonTaskOne.put(KEY_DETAILS, jsonTaskDetailOne);
		
		JSONObject jsonTaskTwo = new JSONObject();
		JSONObject jsonTaskDetailTwo = new JSONObject();
		jsonTaskDetailTwo.put(KEY_CATEGORY, TASK_2_CATEGORY);
		jsonTaskTwo.put(KEY_TITLE, TASK_2_TITLE);
		jsonTaskTwo.put(KEY_DETAILS, jsonTaskDetailTwo);
		
		jsonArray.put(jsonTaskOne);
		jsonArray.put(jsonTaskTwo);
		
		List<ITask> tasks = converter.convertJsonToTasksList(jsonArray);
		
		ITask expectedTaskOne = new Task();
		expectedTaskOne.setTitle(TASK_1_TITLE);
		expectedTaskOne.setEndDate(new Date(1, 1, 2014));
		expectedTaskOne.setNotes(TASK_1_NOTES);
		
		ITask expectedTaskTwo = new Task();
		expectedTaskTwo.setTitle(TASK_2_TITLE);
		Category category = new Category();
		category.setName(TASK_2_CATEGORY);
		expectedTaskTwo.setCategory(category);

		assertEquals(expectedTaskOne.getTitle(), tasks.get(0).getTitle());
		assertEquals(expectedTaskOne.getStartDate(), tasks.get(0).getStartDate());
		assertEquals(expectedTaskOne.getEndDate(), tasks.get(0).getEndDate());
		assertEquals(expectedTaskOne.getRepeatFrequency(), tasks.get(0).getRepeatFrequency());
		assertEquals(expectedTaskOne.getPlace(), tasks.get(0).getPlace());
		assertEquals(expectedTaskOne.getCategory(), tasks.get(0).getCategory());
		assertEquals(expectedTaskOne.getNotes(), tasks.get(0).getNotes());
		
		assertEquals(expectedTaskTwo.getTitle(), tasks.get(1).getTitle());
		assertEquals(expectedTaskTwo.getStartDate(), tasks.get(1).getStartDate());
		assertEquals(expectedTaskTwo.getEndDate(), tasks.get(1).getEndDate());
		assertEquals(expectedTaskTwo.getRepeatFrequency(), tasks.get(1).getRepeatFrequency());
		assertEquals(expectedTaskTwo.getPlace(), tasks.get(1).getPlace());
		assertEquals(expectedTaskTwo.getCategory().getName(), tasks.get(1).getCategory().getName());
		assertEquals(expectedTaskTwo.getNotes(), tasks.get(1).getNotes());
	}
	
	@Test
	public void shouldConvertJsonObjectToTask() throws JSONException, 
			InvalidDateException, CorruptedJsonObjectException {
		JSONObject originalTaskInJson = new JSONObject();
		JSONObject originalTaskDetail = new JSONObject();
		
		originalTaskDetail.put(KEY_END_TIME, new Date(1, 1, 2014).toString());
		originalTaskDetail.put(KEY_NOTES, TASK_1_NOTES);
		originalTaskInJson.put(KEY_TITLE, TASK_1_TITLE);
		originalTaskInJson.put(KEY_DETAILS, originalTaskDetail);
				
		ITask taskFromJson = converter.convertJsonToTask(originalTaskInJson);
				
		assertEquals(taskOne.getTitle(), taskFromJson.getTitle());
		assertEquals(taskOne.getStartDate(), taskFromJson.getStartDate());
		assertEquals(taskOne.getEndDate(), taskFromJson.getEndDate());
		assertEquals(taskOne.getRepeatFrequency(), taskFromJson.getRepeatFrequency());
		assertEquals(taskOne.getPlace(), taskFromJson.getPlace());
		assertEquals(taskOne.getCategory(), taskFromJson.getCategory());
		assertEquals(taskOne.getNotes(), taskFromJson.getNotes());
	}
	
	@Test(expected=CorruptedJsonObjectException.class)
	public void shouldNotConvertJsonObjectWithoutTitleToTask() throws JSONException,
			InvalidDateException, CorruptedJsonObjectException {
		
		JSONObject originalTaskInJson = new JSONObject();
		JSONObject originalTaskDetail = new JSONObject();
		
		originalTaskDetail.put(KEY_END_TIME, new Date(1, 1, 2014));
		originalTaskInJson.put(KEY_DETAILS, originalTaskDetail);
		
		converter.convertJsonToTask(originalTaskInJson);
	}
	
	@Test(expected=InvalidDateException.class)
	public void shouldNotConvertJsonObjectWithInvalidDate() throws JSONException,
			InvalidDateException, CorruptedJsonObjectException {
		
		JSONObject originalTaskInJson = new JSONObject();
		JSONObject originalTaskDetail = new JSONObject();
		
		originalTaskDetail.put(KEY_END_TIME, new Date(1, 13, 2014));
		originalTaskInJson.put(KEY_DETAILS, originalTaskDetail);
		originalTaskInJson.put(KEY_TITLE, TASK_1_TITLE);
		
		converter.convertJsonToTask(originalTaskInJson);
	}
	
	@Test
	public void shouldConvertCategoryListToJSON() throws JSONException {
		JSONArray categoriesInJson = converter.convertCategoryListToJson(categories);
		
		JSONObject firstCategory = categoriesInJson.getJSONObject(0);
		JSONObject secondCategory = categoriesInJson.getJSONObject(1);
		
		// Checking the first category
		assertEquals(CATEGORY_1_NAME, firstCategory.get(KEY_NAME));
		assertEquals(Integer.toHexString(CATEGORY_1_COLOR.getRGB()).substring(2,8), firstCategory.get(KEY_COLOR));
		
		// Checking the second category
		assertEquals(CATEGORY_2_NAME, secondCategory.get(KEY_NAME));
		assertEquals(Integer.toHexString(Color.BLACK.getRGB()).substring(2,8), secondCategory.get(KEY_COLOR));
	}
	
	@Test
	public void shouldConvertJSONArrayToCategoryList() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject firstCategoryInJson = new JSONObject();
		firstCategoryInJson.put(KEY_NAME, CATEGORY_1_NAME);
		firstCategoryInJson.put(KEY_COLOR, Integer.toHexString(CATEGORY_1_COLOR.getRGB()).substring(2,8));
		
		JSONObject secondCategoryInJson = new JSONObject();
		secondCategoryInJson.put(KEY_NAME, CATEGORY_2_NAME);
		
		jsonArray.put(firstCategoryInJson);
		jsonArray.put(secondCategoryInJson);
		
		ICategory expectedCategoryOne = new Category();
		expectedCategoryOne.setName(CATEGORY_1_NAME).setColor(CATEGORY_1_COLOR);
		ICategory expectedCategoryTwo = new Category();
		expectedCategoryTwo.setName(CATEGORY_2_NAME);
		
		HashMap<String, ICategory> categories = converter.convertJsonToCategoryList(jsonArray);
		
		assertEquals(true, categories.containsKey(expectedCategoryOne.getName()));
		assertEquals(CATEGORY_1_NAME, categories.get(expectedCategoryOne.getName()).getName());
		assertEquals(CATEGORY_1_COLOR, categories.get(expectedCategoryOne.getName()).getColor());
		assertEquals(true, categories.containsKey(expectedCategoryTwo.getName()));
		assertEquals(CATEGORY_2_NAME, categories.get(expectedCategoryTwo.getName()).getName());
		assertEquals(Color.BLACK, categories.get(expectedCategoryTwo.getName()).getColor());	
	}
	
	@Test
	public void shouldNotConvertJsonCategoryObjectWithoutName() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject firstCategoryInJson = new JSONObject();
		firstCategoryInJson.put(KEY_COLOR, Integer.toHexString(CATEGORY_1_COLOR.getRGB()).substring(2,8));
		
		jsonArray.put(firstCategoryInJson);
		
		converter.convertJsonToCategoryList(jsonArray);
		
		assertEquals(1, converter.getNumOfCorruptedJsonCategoryObjects());	
	}
	
	private void prepareTask() throws InvalidDateException {
		taskOne = new Task();
		taskOne.setTitle(TASK_1_TITLE)
			.setEndDate(new Date(1, 1, 2014))
			.setNotes(TASK_1_NOTES);
		taskTwo = new Task();
		taskTwo.setTitle(TASK_2_TITLE)
			.setStartDate(new Date(2, 1, 2014))
			.setCategory(new Category().setName(TASK_2_CATEGORY))
			.setRepeatFrequency(RepeatFrequency.DAILY);	
		taskThree = new Task();
		taskThree.setTitle(TASK_3_TITLE);
		taskThree.setStatus(TaskStatus.DONE);
		
		tasks.add(taskOne);
		tasks.add(taskTwo);	
		tasks.add(taskThree);
	}
	
	private void prepareCategory() {
		categoryOne = new Category();
		categoryOne.setName(CATEGORY_1_NAME);
		categoryOne.setColor(CATEGORY_1_COLOR);
		
		categoryTwo = new Category();
		categoryTwo.setName(CATEGORY_2_NAME);
		
		categories.add(categoryOne);
		categories.add(categoryTwo);
	}
	
}

