package list;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import list.CommandBuilder.RepeatFrequency;
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

public class Converter {
	
	@SuppressWarnings("serial")
    class CorruptedJsonObjectException extends Exception { };
	
	// Keys used to identify attributes of a task in JSON	
	private static final String KEY_TITLE = "title";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_START_TIME = "start_time";
	private static final String KEY_END_TIME = "end_time";
	private static final String KEY_REPEAT_FREQUENCY = "repeat_frequency";
	private static final String KEY_PLACE = "place";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_NOTES = "notes";
	private static final String KEY_STATUS = "status";
		
	// Keys used to identify attributes of a category in JSON
	private static final String KEY_CAT_NAME = "name";
	private static final String KEY_CAT_COLOR = "color";
	
	private int numOfCorruptedTaskProperties = 0;
	private int numOfCorruptedJsonTaskObjects = 0;
	private int numOfCorruptedJsonCategoryObjects = 0;
	
	//TODO: Check JsonException 
	List<ITask> convertJsonToTasksList(JSONArray jsonArray) {
		
		resetCorruptedJSONTaskObjectCounter();
		
		List<ITask> listOfTasks = new ArrayList<ITask>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject taskInJson = jsonArray.getJSONObject(i);
				ITask task = convertJsonToTask(taskInJson);
				listOfTasks.add(task);
								
			} catch (CorruptedJsonObjectException e) {
				numOfCorruptedJsonTaskObjects++;
			} catch (JSONException e) {
				assert (false): e.getMessage();
			}
		}
		
		return listOfTasks;
	}
	
	JSONArray convertTasksListToJson(List<ITask> tasks) {
		JSONArray tasksListInJson = new JSONArray();
		
		for (ITask task: tasks) {
			try {
				JSONObject taskInJson = convertTaskToJson(task);
				tasksListInJson.put(taskInJson);
			} catch (JSONException e) {
				assert (false): e.getMessage();
			}
			
		}
		
		return tasksListInJson;
	}
	
	HashMap<String, ICategory> convertJsonToCategoryList(JSONArray jsonArray) {
		
		resetCorruptedJSONCategoryObjectCounter();
		
		HashMap<String, ICategory> categories = new HashMap<String, ICategory>();
				
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject categoryInJson = jsonArray.getJSONObject(i);
				
				ICategory category = convertJsonToCategory(categoryInJson);
				
				if (category != null) {
					categories.put(category.getName(), category);
				}
				
			} catch (CorruptedJsonObjectException e) {
				numOfCorruptedJsonCategoryObjects++;
			} catch (JSONException e) {
				assert (false): e.getMessage();
			}
		}
		
		return categories;
	}
	
	JSONArray convertCategoryListToJson(List<ICategory> categories) {
		JSONArray categoryListInJson = new JSONArray();	
		
		for (ICategory category: categories) {
			try {
				JSONObject taskInJson = convertCategoryToJson(category);
				categoryListInJson.put(taskInJson);
			} catch (JSONException e) {
				assert (false): e.getMessage();
			}
			
		}
		
		return categoryListInJson;		
	}
	
	//TODO: make this private here and remove in JUnitTest
	ITask convertJsonToTask(JSONObject jsonObject) 
			throws CorruptedJsonObjectException, JSONException {
		if (!jsonObject.has(KEY_TITLE)) {
			throw new CorruptedJsonObjectException();
		}
		
		ITask task = new Task();
		
		setTaskTitle(jsonObject, task);
		
		setTaskDetail(jsonObject, task);
		
		return task;
		
	}
	
	int getNumberOfCorruptedTaskProperties() {
		return numOfCorruptedTaskProperties;
	}
	
	int getNumberOfCorruptedJsonTaskObjects() {
		return numOfCorruptedJsonTaskObjects;
	}
	
	int getNumOfCorruptedJsonCategoryObjects() {
		return numOfCorruptedJsonCategoryObjects;
	}
	
	private void setTaskDetail(JSONObject jsonObject, ITask task)
			throws JSONException {
		if (hasTaskDetails(jsonObject)) {
			JSONObject taskDetailInJson = jsonObject.getJSONObject(KEY_DETAILS);
			extractTaskDetailFromJson(task, taskDetailInJson);
		}
	}

	private void setTaskTitle(JSONObject jsonObject, ITask task) throws JSONException {
		String title = jsonObject.getString(KEY_TITLE);		
		task.setTitle(title);		
	}
	
	private boolean hasTaskDetails(JSONObject jsonObject)
			throws JSONException {
		return jsonObject.has(KEY_DETAILS) && 
			   jsonObject.getJSONObject(KEY_DETAILS).length() > 0;
	}
	
	private JSONObject convertTaskToJson(ITask task) throws JSONException {
		JSONObject taskInJson = new JSONObject();
		JSONObject taskDetails = new JSONObject();
		taskInJson.put(KEY_TITLE, task.getTitle());
		taskInJson.put(KEY_DETAILS, taskDetails);
		putAllJsonAttributesOfTask(taskDetails, task);
		return taskInJson;
	}
	
	private void putAllJsonAttributesOfTask(JSONObject taskDetail, ITask task) 
			throws JSONException {
		
		if (task.getStartDate() != null) {
			taskDetail.put(KEY_START_TIME, task.getStartDate().toString());
		}
		
		if (task.getEndDate() != null) {
			taskDetail.put(KEY_END_TIME, task.getEndDate().toString());
		}
		
		if (task.getRepeatFrequency() != null) {
			taskDetail.put(KEY_REPEAT_FREQUENCY, task.getRepeatFrequency().name());
		}
		
		if (task.getStatus() != null) {
		    taskDetail.put(KEY_STATUS, task.getStatus().name());
		}
		
		if (task.getPlace() != null) {
		    taskDetail.put(KEY_PLACE, task.getPlace());
		}
			
		if (task.getCategory() != null) {
			taskDetail.put(KEY_CATEGORY, task.getCategory().getName());
		}
		
		if (task.getNotes() != null) {
	        taskDetail.put(KEY_NOTES, task.getNotes());   
		}
	}
	
	private void extractTaskDetailFromJson(ITask task, JSONObject taskDetailInJson) 
			throws JSONException {	
		resetCorruptedTaskPropertiesCounter();
	
		if (taskDetailInJson.has(KEY_START_TIME)) {
			String startDate = taskDetailInJson.getString(KEY_START_TIME);
						
			try {
				if (startDate != "") {
					task.setStartDate(new Date(startDate));
				}
			} catch (InvalidDateException e) {
				numOfCorruptedTaskProperties++;
			}
			
		}
		
		if (taskDetailInJson.has(KEY_END_TIME)) {
			String endTime = taskDetailInJson.getString(KEY_END_TIME);
			
			try {
				if (endTime != "") {
					task.setEndDate(new Date(endTime));
				}
			} catch (InvalidDateException e) {
				numOfCorruptedTaskProperties++;
			}
			
		}
		
		if (taskDetailInJson.has(KEY_CATEGORY)) {
			String categoryName = taskDetailInJson.getString(KEY_CATEGORY);
			TaskManager taskManager = TaskManager.getInstance();
			
			if (categoryName != "") {
			    ICategory category = taskManager.getCategory(categoryName);
			    task.setCategory(category);
			}
		}
			
		if (taskDetailInJson.has(KEY_REPEAT_FREQUENCY)) {
			String repeatFrequencyName = taskDetailInJson.getString(KEY_REPEAT_FREQUENCY);
			
			if (repeatFrequencyName != null &&
				RepeatFrequency.isValidRepeatFrequencyType(repeatFrequencyName.trim().toUpperCase())) {
				task.setRepeatFrequency(RepeatFrequency.valueOf(repeatFrequencyName.trim().toUpperCase()));
			} else {
				task.setRepeatFrequency(RepeatFrequency.NONE);
			}
		}
		
		if (taskDetailInJson.has(KEY_STATUS)) {
		    String statusName = taskDetailInJson.getString(KEY_STATUS);
		    TaskStatus taskStatus = null;
		    if (statusName != null) {
		        taskStatus = TaskStatus.valueOf(statusName);
		    }
		    
		    if (taskStatus == null) {
		        task.setStatus(TaskStatus.PENDING);
		    } else {
		        task.setStatus(taskStatus);
		    }
		}
		
		if (taskDetailInJson.has(KEY_PLACE)) {
			String place = taskDetailInJson.getString(KEY_PLACE);
			
			if (place != "") {
				task.setPlace(place);
			}
		}
		
		if (taskDetailInJson.has(KEY_NOTES)) {
			String notes = taskDetailInJson.getString(KEY_NOTES);
			
			if (notes != null) {
				task.setNotes(notes);
			}
		}
	}
	
	private void resetCorruptedTaskPropertiesCounter() {
		numOfCorruptedTaskProperties = 0;
	}
	
	private void resetCorruptedJSONTaskObjectCounter() {
		numOfCorruptedJsonTaskObjects = 0;
	}
	
	private void resetCorruptedJSONCategoryObjectCounter() {
		numOfCorruptedJsonCategoryObjects = 0;
	}
		
	private JSONObject convertCategoryToJson(ICategory category) throws JSONException {
		JSONObject categoryInJson = new JSONObject();
		
		if (category.getName() != null && !category.getName().equals("")) {
			categoryInJson.put(KEY_CAT_NAME, category.getName());
		} 
		
		if (category.getColor() != null) {
			String colorInRGBA = Integer.toHexString(category.getColor().getRGB());
			categoryInJson.put(KEY_CAT_COLOR, colorInRGBA.substring(2, colorInRGBA.length()));
		}
				
		return categoryInJson;
	}
	
	private ICategory convertJsonToCategory(JSONObject jsonObject) 
			throws CorruptedJsonObjectException, JSONException {
		if (hasNoCategoryName(jsonObject)) {
			throw new CorruptedJsonObjectException();
		}
		
		String categoryName = jsonObject.getString(KEY_CAT_NAME);
		if (categoryName.equals(Category.getDefaultName())) {
			return null;
		}
		
		ICategory category = new Category();
		category.setName(categoryName);
			
		Color categoryColor = extractCategoryColor(jsonObject);
		category.setColor(categoryColor);
		
		return category;
		
	}
	
	private Color extractCategoryColor(JSONObject jsonObject) throws JSONException {
		if (hasNoCategoryColor(jsonObject)) {
			return Category.getDefaultColor();
		} else {
			String colorInHexString = jsonObject.getString(KEY_CAT_COLOR);
			Color categoryColor;
			try {
				int colorInRGB = Integer.parseInt(colorInHexString, 16);
				categoryColor = new Color(colorInRGB);
			} catch (NumberFormatException e) {
				categoryColor = Category.getDefaultColor();
			}
			
			return categoryColor;
		}
	}

	private boolean hasNoCategoryName(JSONObject jsonObject) {
		return !jsonObject.has(KEY_CAT_NAME);
	}
	
	private boolean hasNoCategoryColor(JSONObject jsonObject) {
		return !jsonObject.has(KEY_CAT_COLOR);
	}	
}
