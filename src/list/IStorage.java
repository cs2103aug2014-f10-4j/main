package list;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import list.model.ICategory;
import list.model.ITask;

import org.json.JSONException;

/**
 * This class handles persistent storage of tasks (save and load).
 * 
 * @author A0094022R
 */
interface IStorage {
    
    /**
     * Loads a text file and creates a list of tasks.
     * 
     * @return List of tasks.
     * @throws IOException 
     * @throws JSONException 
     */
    List<ITask> loadTasksFromFile() throws IOException, JSONException;
    
    
    /**
     * Save tasks in the given list into a text file.
     * 
     * @param tasks list of tasks.
     * @throws IOException 
     */
    void saveTasksToFile(List<ITask> tasks) throws IOException;
    
    /**
     * Loads a text file and creates a list of categories
     * 
     * @return List of categories
     * @throws IOException 
     * @throws JSONException 
     */
    HashMap<String, ICategory> loadCategoriesFromFile() throws IOException, JSONException;
    
    /**
     * Save categories in the given list into a text file
     * 
     * @param categories list of categories
     * @throws IOException 
     */
    void saveCategoriesToFile(List<ICategory> categories) throws IOException;
}
