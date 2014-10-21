package list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a Singleton class that keeps track of Tasks 
 * and Categories. Execution of commands is not handled by this class
 * but by the individual command types.
 * 
 * This class modifies the user interface by the methods
 * given in <code>IUserInterface</code>.
 * 
 * @author andhieka, michael
 */
public class TaskManager {
    //TODO: keep the methods and variables static
    //TODO: please maintain mTasks sorted at all times
    private static List<ITask> tasks = new ArrayList<ITask>();
    private static Map<String, ICategory> categories = new HashMap<String, ICategory>();
    
    private static TaskManager taskManagerInstance = null;
    
    private TaskManager() { } ;
    
    static TaskManager getInstance() {
    	if (taskManagerInstance == null) {
    		taskManagerInstance = new TaskManager();
    	}
    	
    	return taskManagerInstance;
    }
    
    //CATEGORY METHODS
    /**
     * Accepts a string and returns a ICategory object with the same name as the input.
     * If the input is <code>null</code>, will return a default category object.
     * 
     * @param categoryName
     * @return
     */
    static ICategory getCategory(String categoryName) {
    	//return a default category if undefined by user
        if (categoryName == null || categoryName == "") {
    	    return Category.getDefaultCategory();
    	}
    	
        if (categories.containsKey(categoryName)) {
    		return categories.get(categoryName);
    	} else {
    		ICategory category = new Category();
    		category.setName(categoryName);
    		categories.put(categoryName, category);
    		return category;
    	}
    }
    
    //METHODS FOR STORAGE
    List<ITask> getTasksList() {
    	return tasks;
    }
    
    //METHODS FOR COMMANDS EXECUTION
    void addTask(ITask task) {
        tasks.add(task);
    }
    
    ITask getTask(Integer taskNumberShownOnScreen) {
        return tasks.get(getTaskId(taskNumberShownOnScreen));
    }
    
    void deleteTask(Integer taskNumberShownOnScreen) {
        tasks.remove(getTaskId(taskNumberShownOnScreen));
    }
    
    void clearTasks() {
    	tasks.clear();
    }
    
    void sortTasks() {
    	Collections.sort(tasks);
	}
    
    int getNumberOfTasks() {
    	return tasks.size();
    }
    
    boolean hasTask(ITask task) {
    	return tasks.contains(task);
    }
    
    boolean hasTaskWithNumber(Integer taskNumber) {
		return (taskNumber >= 1) && (taskNumber <= getNumberOfTasks());
	}
    
    boolean isListOfTasksSorted() {
    	if (tasks.size() <= 1) {
    		return true;
    	} else {
    		for (int i = 0; i < tasks.size() - 1; i++) {
    			if (tasks.get(i).compareTo(tasks.get(i + 1)) > 0) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
	}
    
    /**
     * Converts a 1-based id to 0-based id as represented in the array.
     * @param taskNumberShownOnScreen 1-based index
     * @return 0-based index
     */
    private static int getTaskId(Integer taskNumberShownOnScreen) {
        return taskNumberShownOnScreen - 1;
    }
    
    private static void printTasks() {
    	for (int i = 0; i < tasks.size(); i++) {
    		System.out.println(tasks.get(i).getTitle());
    	}
    }    
}
