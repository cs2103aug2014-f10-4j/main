package list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import list.Converter.CorruptedJsonObjectException;
import list.Date.InvalidDateException;
import list.ITask.TaskStatus;

import org.json.JSONException;

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
    private List<ITask> floatingTasks = new ArrayList<ITask>();
    private List<ITask> tasks = new ArrayList<ITask>();
    private Stack<ITask> deletedTasks = new Stack<ITask>();
    
    private ReaderWriter readerWriter = new ReaderWriter();
    private Map<String, ICategory> categories = new HashMap<String, ICategory>();
    
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
    ICategory getCategory(String categoryName) {
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
    
    //TASK GETTERS
    
    /**
     * Returns all task in the TaskManager
     * @return
     */
    List<ITask> getAllTasks() {
        Collections.sort(this.floatingTasks);
        Collections.sort(this.tasks);
        
    	List<ITask> allTasks = new ArrayList<ITask>(tasks.size() + floatingTasks.size());
    	for (ITask task: tasks) {
    	    allTasks.add(task);
    	}
    	for (ITask task: floatingTasks) {
    	    allTasks.add(task);
    	}
        return allTasks;
    }
    
    List<ITask> getFloatingTasks() {
        Collections.sort(this.floatingTasks);
        
        List<ITask> duplicateList = new ArrayList<ITask>(this.floatingTasks);
        return duplicateList;
    }
    
    List<ITask> getTodayTasks() {
        Collections.sort(this.tasks);
        
        List<ITask> todayTasks = new ArrayList<ITask>();
        Date todayDate = new Date();
        for (ITask task: this.tasks) {
            if (task.getEndDate().equals(todayDate)) {
                todayTasks.add(task);
            } else {
                break; //tasks is sorted by deadline
            }
        }
        return todayTasks;
    }
    
    //METHODS FOR COMMANDS EXECUTION
    void addTask(ITask task) {
        if (hasDeadline(task)) {
            tasks.add(task);
        } else {
            floatingTasks.add(task);
        }
    }
    
    void markTaskAsDone(ITask task) {
    	task.setStatus(TaskStatus.DONE);
    }
    
    void unmarkTask(ITask task) {
    	task.setStatus(TaskStatus.PENDING);
    }
    
    @Deprecated
    ITask getTask(Integer taskNumberShownOnScreen) {
        return tasks.get(getTaskId(taskNumberShownOnScreen));
    }
    
    @Deprecated
    void deleteTask(Integer taskNumberShownOnScreen) {
        ITask task = getTask(taskNumberShownOnScreen);
        if (hasDeadline(task)) {
            tasks.remove(task);
        } else {
            floatingTasks.remove(task);
        }
        deletedTasks.push(task);
    }
    
    @Deprecated
    boolean hasTaskWithNumber(Integer taskNumberShownOnScreen) {
        return taskNumberShownOnScreen > 0 &&
               taskNumberShownOnScreen <= tasks.size();
    }
    
    /**
     * Clears all references to all tasks. Danger: not undo-able.
     * Used for testing.
     */
    void clearTasks() {
        floatingTasks.clear();
        tasks.clear();
        deletedTasks.clear();
    }
    
    void loadTasks() throws IOException, JSONException {
    	List<ITask> listOfTasks = readerWriter.loadFromFile();
    	tasks = listOfTasks;
	}   
    
    void saveTasks() throws IOException {
    	readerWriter.saveToFile(getAllTasks());
    }
    
    @Deprecated
    int getNumberOfTasks() {
    	return tasks.size();
    }
    
    boolean hasTask(ITask task) {
    	return tasks.contains(task) ||
    	       floatingTasks.contains(task);
    }
        
    //Private functions
    private static boolean hasDeadline(ITask task) {
        return task.getEndDate() != null;
    }
    
    /**
     * Converts a 1-based id to 0-based id as represented in the array.
     * @param taskNumberShownOnScreen 1-based index
     * @return 0-based index
     */
    private static int getTaskId(Integer taskNumberShownOnScreen) {
        return taskNumberShownOnScreen - 1;
    }
     
}
