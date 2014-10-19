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
	
	private int numOfCorruptedJsonObjects = 0;
	
	//TODO: JsonException not handled when string supplied is invalid
	//TODO: CorruptedJsonObjectException
	List<ITask> convertJsonToTasksList(JSONArray jsonArray) 
			throws JSONException, CorruptedJsonObjectException, InvalidDateException {
		List<ITask> listOfTasks = new ArrayList<ITask>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			
			try {
				JSONObject taskInJson = jsonArray.getJSONObject(i);
				ITask task = convertJsonToTask(taskInJson);
				listOfTasks.add(task);
				
			} catch (InvalidDateException e) {
				
			} catch (CorruptedJsonObjectException e) {
				numOfCorruptedJsonObjects++;
			}
		
		}
		
		return listOfTasks;
		
	}
	
	//TODO: make this private here and remove in JUnitTest
	ITask convertJsonToTask(JSONObject jsonObject) throws JSONException, 
			CorruptedJsonObjectException, InvalidDateException {
		if (!jsonObject.has(KEY_TITLE)) {
			throw new CorruptedJsonObjectException();
		}
		
		String title = jsonObject.getString(KEY_TITLE);
				
		ITask task = new Task();
		task.setTitle(title);
		
		if (hasOnlyTitleAttribute(jsonObject)) {
			return task;
		}
		
		JSONObject taskDetailInJson = jsonObject.getJSONObject(KEY_DETAILS);
				
		convertTaskDetailFromJson(task, taskDetailInJson);
		
		return task;
		
	}

	JSONArray convertTasksListToJson(List<ITask> tasks) throws JSONException {
		JSONArray tasksListInJson = new JSONArray();
		
		for (ITask task: tasks) {
			JSONObject taskInJson = convertTaskToJson(task);
			tasksListInJson.put(taskInJson);
		}
		
		return tasksListInJson;
	}
	
	private boolean hasOnlyTitleAttribute(JSONObject jsonObject)
			throws JSONException {
		return !jsonObject.has(KEY_DETAILS) || 
			jsonObject.getJSONObject(KEY_DETAILS).length() == 0;
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
	
	private void convertTaskDetailFromJson(ITask task,
			JSONObject taskDetailInJson) throws JSONException,
			InvalidDateException {
		if (taskDetailInJson.has(KEY_START_TIME)) {
			String startDate = taskDetailInJson.getString(KEY_START_TIME);
						
			if (startDate != null) {
				task.setStartDate(new Date(startDate));
			}
		}
		
		if (taskDetailInJson.has(KEY_END_TIME)) {
			String endTime = taskDetailInJson.getString(KEY_END_TIME);
						
			if (endTime != null) {
				task.setEndDate(new Date(endTime));
			}
		}
		
		if (taskDetailInJson.has(KEY_CATEGORY)) {
			String categoryName = taskDetailInJson.getString(KEY_CATEGORY);
			
			if (categoryName != null) {
				ICategory category = new Category();
				category.setName(categoryName);
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
}
