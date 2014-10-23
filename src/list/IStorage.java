package list;

import java.io.IOException;
import java.util.List;

import list.Converter.CorruptedJsonObjectException;
import list.Date.InvalidDateException;

import org.json.JSONException;

/**
 * This class handles persistent storage of tasks (save and load).
 * 
 * @author andhieka, michael
 */
interface IStorage {
    
    /**
     * Loads a text file and creates a list of tasks.
     * @param filename file name of the saved file.
     * @return List of tasks.
     * @throws IOException 
     * @throws JSONException 
     * @throws InvalidDateException 
     * @throws CorruptedJsonObjectException 
     */
    List<ITask> loadFromFile() throws IOException, JSONException;
    
    
    /**
     * Save tasks in the given list into the file with specified filename.
     * 
     * @param tasks list of tasks.
     * @param filename name of file.
     * @throws JSONException 
     * @throws IOException 
     */
    void saveToFile(List<ITask> tasks) throws IOException;
    
}
