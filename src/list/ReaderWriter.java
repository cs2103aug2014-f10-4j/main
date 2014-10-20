package list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import list.Converter.CorruptedJsonObjectException;
import list.Date.InvalidDateException;

import org.json.JSONArray;
import org.json.JSONException;

public class ReaderWriter implements IStorage {
	
	//TODO: Better naming for messages
	private static final String MESSAGE_ERROR = "IO Error!";
	private static final String TEXTFILE_NAME = "tasks";
	private static final int INDENTATION_FACTOR = 4;	
	
	private Converter jsonConverter = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;
	
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
		writer.close();
	}
	
	@Override
	public List<ITask> loadFromFile() throws IOException, JSONException, 
			CorruptedJsonObjectException, InvalidDateException {
		
		File taskStorageInTextFile = new File(TEXTFILE_NAME);
		
		if (taskStorageInTextFile.exists()) {
			setUpBufferedReader();
			
			StringBuilder sb = new StringBuilder();
			
			while (reader.ready()) {
				sb.append(new Character((char) reader.read()).toString());
			}
			
			JSONArray jsonArray = new JSONArray(sb.toString());

			List<ITask> tasksList = jsonConverter.convertJsonToTasksList(jsonArray);
			
			return tasksList;
			
		} else {
			return new ArrayList<ITask>();
		}
	}

	@Override
	public void saveToFile(List<ITask> tasks) throws JSONException, IOException {
		JSONArray tasksListInJson = jsonConverter.convertTasksListToJson(tasks);
		setUpBufferedWriter();
		writer.write(tasksListInJson.toString(INDENTATION_FACTOR));
		writer.flush();		
	}
	
	private void setUpBufferedWriter() {
		try {
			writer = new BufferedWriter(new FileWriter(TEXTFILE_NAME));
		} catch (IOException e) {
			System.out.println(MESSAGE_ERROR);
		}
	}
	
	private void setUpBufferedReader() {
		try {
			reader = new BufferedReader(new FileReader(TEXTFILE_NAME));
		} catch (IOException e) {
			System.out.println(MESSAGE_ERROR);
		}
	}
	
	public static void main(String[] args) throws IOException, JSONException, CorruptedJsonObjectException, InvalidDateException {
		ReaderWriter rw = new ReaderWriter();
		rw.loadFromFile();
	}
	

	
}
