package list;

import java.util.List;

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
     */
    List<ITask> loadFromFile(String filename);
    
    
    /**
     * Save tasks in the given list into the file with specified filename.
     * 
     * @param tasks list of tasks.
     * @param filename name of file.
     */
    void saveToFile(List<ITask> tasks, String filename);
    
}
