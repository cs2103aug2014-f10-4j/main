package list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import list.model.ICategory;
import list.model.ITask;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 
 * @author Michael
 *
 */
public class ReaderWriter implements IStorage {
	
	@SuppressWarnings("serial")
    class CorruptedFileException extends Exception { };
	
	//TODO: Better naming for messages
	private static final String MESSAGE_IO_ERROR = "IO Error!";
	private static final String TEXTFILE_NAME_TASKS = "list_tasks.json";
	private static final String TEXTFILE_NAME_CATEGORIES = "list_categories.json";
	private static final int INDENTATION_FACTOR = 4;	
	
	private Converter jsonConverter = null;
	private BufferedWriter taskWriter = null;
	private BufferedReader taskReader = null;
	private BufferedWriter categoryWriter = null;
	private BufferedReader categoryReader = null;
	
	//TODO: Better error handling, and how to give message to UI?
	ReaderWriter() {
		jsonConverter = new Converter();		
	}
		
	/**
	 * Closing the BufferedWriter, preventing any further writing process.
	 * This method should only be executed when the application is about to exit. 
	 * 
	 * @throws IOException 
	 */
	void closeWriter() throws IOException {
		taskWriter.close();
	}
	
	@Override
	public HashMap<String,ICategory> loadCategoriesFromFile() throws IOException, JSONException {
		File categoryStorageInTextFile = new File(TEXTFILE_NAME_CATEGORIES);
		
		if (categoryStorageInTextFile.exists()) {
			setUpCategoryBufferedReader();
			
			StringBuilder sb = new StringBuilder();
			
			while (categoryReader.ready()) {
				sb.append(new Character((char) categoryReader.read()).toString());
			}
			
			JSONArray jsonArray = new JSONArray(sb.toString());

			HashMap<String, ICategory> categories = jsonConverter.convertJsonToCategoryList(jsonArray);
			
			return categories;
			
		} else {
			return new HashMap<String, ICategory>();
		}
	}

	@Override
	public void saveCategoriesToFile(List<ICategory> categories) throws IOException {
		JSONArray categoryListInJson = jsonConverter.convertCategoryListToJson(categories);
		setUpCategoryBufferedWriter();
		
		try {
			categoryWriter.write(categoryListInJson.toString(INDENTATION_FACTOR));
			categoryWriter.flush();
		} catch (JSONException e) {
			// JSONException is thrown when indentFactor is not valid,
			// but INDENTATION_FACTOR is indeed a valid number, so this
			// should be impossible
			assert (false): e.getMessage();
		}
		
	}
	
	@Override
	public List<ITask> loadTasksFromFile() throws IOException, JSONException {
		
		File taskStorageInTextFile = new File(TEXTFILE_NAME_TASKS);
		
		if (taskStorageInTextFile.exists()) {
			setUpTaskBufferedReader();
			
			StringBuilder sb = new StringBuilder();
			
			while (taskReader.ready()) {
				sb.append(new Character((char) taskReader.read()).toString());
			}
			
			JSONArray jsonArray = new JSONArray(sb.toString());

			List<ITask> tasksList = jsonConverter.convertJsonToTasksList(jsonArray);
			
			return tasksList;
			
		} else {
			return new ArrayList<ITask>();
		}
	}

	@Override
	public void saveTasksToFile(List<ITask> tasks) throws IOException {
		JSONArray tasksListInJson = jsonConverter.convertTasksListToJson(tasks);
		setUpTaskBufferedWriter();
		
		try {
			taskWriter.write(tasksListInJson.toString(INDENTATION_FACTOR));
			taskWriter.flush();

		} catch (JSONException e) { 
			// JSONException is thrown when indentFactor is not valid,
			// but INDENTATION_FACTOR is indeed a valid number, so this
			// should be impossible
			assert (false): e.getMessage();
		}
				
	}
	
	private void setUpTaskBufferedWriter() {
		try {
			taskWriter = new BufferedWriter(new FileWriter(TEXTFILE_NAME_TASKS));
		} catch (IOException e) {
			System.out.println(MESSAGE_IO_ERROR);
		}
	}
	
	private void setUpCategoryBufferedWriter() throws IOException {
		categoryWriter = new BufferedWriter(new FileWriter(TEXTFILE_NAME_CATEGORIES));
	}
	
	private void setUpTaskBufferedReader() throws IOException {
		taskReader = new BufferedReader(new FileReader(TEXTFILE_NAME_TASKS));
	}

	private void setUpCategoryBufferedReader() {
		try {
			categoryReader = new BufferedReader(new FileReader(TEXTFILE_NAME_CATEGORIES));
		} catch (IOException e) {
			System.out.println(MESSAGE_IO_ERROR);
		}
	}
	
}
