package list;

import java.util.List;

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
	
	List<ITask> convertJsonToTasksList(JSONObject jsonObject) {
		return null;
	}
	
	ITask convertJsonToTask(JSONObject jsonObject) 
			throws JSONException, CorruptedJsonObjectException {
		if (!jsonObject.has(KEY_TITLE)) {
			throw new CorruptedJsonObjectException();
		}
		
		String title = jsonObject.getString(KEY_TITLE);
		ITask task = new Task();
		
		
		if (hasOnlyTitleAttribute(jsonObject)) {
			task.setTitle(title);
			return task;
		}
		
		
		return null;
		
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
		
		if (task.getStartTime() != null) {
			taskDetail.put(KEY_START_TIME, task.getStartTime().toString());
		}
		
		if (task.getEndTime() != null) {
			taskDetail.put(KEY_END_TIME, task.getEndTime().toString());
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
}
