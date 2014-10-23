package list;

import java.util.ArrayList;
import java.util.List;

import list.CommandBuilder.RepeatFrequency;
import list.Date.InvalidDateException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Converter {
	
	class CorruptedJsonObjectException extends Exception { };
	
	// Exception: user manually deletes title field -> corrupted	
	private static final String KEY_TITLE = "title";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_START_TIME = "start_time";
	private static final String KEY_END_TIME = "end_time";
	private static final String KEY_REPEAT_FREQUENCY = "repeat_frequency";
	private static final String KEY_PLACE = "place";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_NOTES = "notes";
		
	private int numOfCorruptedProperties = 0;
	private int numOfCorruptedJsonObjects = 0;
	
	//TODO: Check JsonException 
	List<ITask> convertJsonToTasksList(JSONArray jsonArray) {
		
		resetCorruptedJSONObjectCounter();
		
		List<ITask> listOfTasks = new ArrayList<ITask>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject taskInJson = jsonArray.getJSONObject(i);
				ITask task = convertJsonToTask(taskInJson);
				listOfTasks.add(task);
								
			} catch (CorruptedJsonObjectException e) {
				numOfCorruptedJsonObjects++;
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
	
	int getNumberOfCorruptedProperties() {
		return numOfCorruptedProperties;
	}
	
	int getNumerOfCorruptedJsonObjects() {
		return numOfCorruptedJsonObjects;
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
		
		taskDetail.put(KEY_PLACE, task.getPlace());
			
		if (task.getCategory() != null) {
			taskDetail.put(KEY_CATEGORY, task.getCategory().getName());
		}
		
		taskDetail.put(KEY_NOTES, task.getNotes());
	}
	
	private void extractTaskDetailFromJson(ITask task, JSONObject taskDetailInJson) 
			throws JSONException {	
		resetCorruptedPropertyCounter();
	
		if (taskDetailInJson.has(KEY_START_TIME)) {
			String startDate = taskDetailInJson.getString(KEY_START_TIME);
						
			try {
				if (startDate != null) {
					task.setStartDate(new Date(startDate));
				}
			} catch (InvalidDateException e) {
				numOfCorruptedProperties++;
			}
			
		}
		
		if (taskDetailInJson.has(KEY_END_TIME)) {
			String endTime = taskDetailInJson.getString(KEY_END_TIME);
			
			try {
				if (endTime != null) {
					task.setEndDate(new Date(endTime));
				}
			} catch (InvalidDateException e) {
				numOfCorruptedProperties++;
			}
			
		}
		
		if (taskDetailInJson.has(KEY_CATEGORY)) {
			String categoryName = taskDetailInJson.getString(KEY_CATEGORY);
			TaskManager taskManager = TaskManager.getInstance();
			
			if (categoryName != null) {
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
		
		if (taskDetailInJson.has(KEY_PLACE)) {
			String place = taskDetailInJson.getString(KEY_PLACE);
			
			if (place != null) {
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
	
	private void resetCorruptedPropertyCounter() {
		numOfCorruptedProperties = 0;
	}
	
	private void resetCorruptedJSONObjectCounter() {
		numOfCorruptedJsonObjects = 0;
	}
	
	
}
