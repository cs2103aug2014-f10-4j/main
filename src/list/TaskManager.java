package list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import list.model.Category;
import list.model.Date;
import list.model.ICategory;
import list.model.ITask;
import list.model.ITask.TaskStatus;

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

	private ObservableList<ITask> floatingTasks = FXCollections.observableArrayList();
	private ObservableList<ITask> currentTasks = FXCollections.observableArrayList();
	private ObservableList<ITask> overdueTasks = FXCollections.observableArrayList();
	
	private Map<String, ObservableList<ITask>> categoryLists = new HashMap<String, ObservableList<ITask>>();
	private Map<String, ICategory> categories = new HashMap<String, ICategory>();
	
    //private List<ITask> floatingTasks = new ArrayList<ITask>();
    private List<ITask> tasks = new ArrayList<ITask>();
    private Stack<ITask> deletedTasks = new Stack<ITask>();
    
    private ReaderWriter readerWriter = new ReaderWriter();
    
    
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
     * Creates a new category with the specified name in the input
     * 
     * @param categoryName
     * @return true if new category has successfully been added. false otherwise.
     */
    boolean addCategory(String categoryName) {
    	if (categoryName == null || categoryName.isEmpty()) {
    		return false;
    	} 
    	
		ICategory category = new Category();
		category.setName(categoryName);
		ObservableList<ITask> newCategoryList = FXCollections.observableArrayList();
		categoryLists.put(categoryName, newCategoryList);
		categories.put(categoryName, category);
		
		return true;
    	
    }
    
    /**
     * Deletes a category as specified by <code>categoryName</code>
     * 
     * @param categoryName
     * @return true if new category has successfully been deleted. false otherwise.
     */
    boolean deleteCategory(String categoryName) {
    	if (categoryName == null || categoryName.isEmpty()) {
    		return false;
    	} 
    	
    	if (categories.containsKey(categoryName) && 
    		categoryLists.containsKey(categoryName)) {
    		
    		categoryLists.remove(categoryName);
    		categories.remove(categoryName);
    		return true;
    		
    	} else {
    		return false;
    	}
    }    
    
    /**
     * Accepts a string and returns a ICategory object with the same name as the input.
     * If the input is <code>null</code>, will return a default category object.
     * 
     * @param categoryName
     * @return
     */
    ICategory getCategory(String categoryName) {
    	//return a default category if undefined by user
        if (categoryName == null || categoryName.isEmpty()) {
    	    return Category.getDefaultCategory();
    	}
    	
        if (!categories.containsKey(categoryName)) {
    		addCategory(categoryName);
        }
    		
    	return categories.get(categoryName);
    }
    
    List<ICategory> getAllCategories() {
        List<ICategory> categoryList = new ArrayList<ICategory>();
        for (ICategory category: this.categories.values()) {
            if (!category.getName().equals("")) {
                categoryList.add(category);
            }
        }
        return categoryList;
    }
    
    //TASK GETTERS
    /**
     * Gets all tasks in the TaskManager. 
     * The list of all tasks returned by method will be used for storage purposes,
     * and not displaying purposes, hence, there is no need to keep the list of 
     * all tasks sorted
     * 
     * @return list of all tasks in TaskManager
     */
    List<ITask> getAllTasks() {
        int sizeAllTasks = floatingTasks.size() + currentTasks.size() + overdueTasks.size();
        
    	List<ITask> allTasks = new ArrayList<ITask>(sizeAllTasks);
    	for (ITask task: floatingTasks) {
    	    allTasks.add(task);
    	}
    	for (ITask task: currentTasks) {
    	    allTasks.add(task);
    	}
    	for (ITask task: overdueTasks) {
    		allTasks.add(task);
    	}
    	
        return allTasks;
    }
    
    ObservableList<ITask> getFloatingTasks() {
        FXCollections.sort(this.floatingTasks);
        
        return this.floatingTasks;
    }
    
    ObservableList<ITask> getCurrentTasks() {
        FXCollections.sort(this.currentTasks);
        
        return this.currentTasks;
    }
    
    ObservableList<ITask> getOverdueTasks() {
        FXCollections.sort(this.overdueTasks);
        
        return this.overdueTasks;
    }
    
    /**
     * Gets the tasks list of a certain category as specified by <code>categoryName</code>
     * in the input
     * 
     * @param categoryName
     * @return the tasks list of a certain category
     */
    ObservableList<ITask> getTasksInCategory(String categoryName) {
    	if (categoryName == null || categoryName.isEmpty()) {
    		return null;
    	}
    	
    	if (categoryLists.containsKey(categoryName)) {
    		return categoryLists.get(categoryName); 
    	} else {
    		return null;
    	}
    }    
    
    
    //METHODS FOR COMMANDS EXECUTION
    void addTask(ITask task) {
        if (hasDeadline(task)) {
            if (isOverdue(task)) {
            	overdueTasks.add(task);
            } else {
            	currentTasks.add(task);
            }
        } else {
            floatingTasks.add(task);
        }
        
        if (hasCategory(task)) {
        	String categoryName = task.getCategory().getName();
        	ObservableList<ITask> tasksListInCategory = getTasksInCategory(categoryName);
        	
        	if (tasksListInCategory != null) {
        		tasksListInCategory.add(task);
        	}
        }
    }
    
    public void addToCategoryList(ITask task) {
    	if (hasCategory(task)) {
    		String categoryName = task.getCategory().getName(); 
    		ObservableList<ITask> tasksListInCategory = getTasksInCategory(categoryName);
        	tasksListInCategory.add(task);
    	}
    }
    
    public void removeFromCategoryList(ITask task) {
    	if (hasCategory(task)) {
    		String categoryName = task.getCategory().getName();
    		ObservableList<ITask> tasksListInCategory = getTasksInCategory(categoryName);
    		tasksListInCategory.remove(task);
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

    void deleteTask(ITask task) {
        if (hasDeadline(task)) {
        	if (isOverdue(task)) {
            	overdueTasks.remove(task);
            } else {
            	currentTasks.remove(task);
            }
        } else {
            floatingTasks.remove(task);
        }
        
        if (hasCategory(task)) {
        	String categoryName = task.getCategory().getName();
        	ObservableList<ITask> tasksListInCategory = getTasksInCategory(categoryName);
        	
        	if (tasksListInCategory != null) {
        		tasksListInCategory.remove(task);
        	}
        }
        
        deletedTasks.push(task);
    }
    
    void undeleteTask(ITask task) {
        if (deletedTasks.contains(task)) {
        	addTask(task);
            deletedTasks.remove(task);
        }   
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
        currentTasks.clear();
        overdueTasks.clear();
        deletedTasks.clear();
    }
    
    // SAVING AND LOADING  
    void loadData() throws IOException, JSONException {
    	loadCategories();
    	loadTasks();
    }
    
    void saveData() throws IOException {
    	saveCategories();
    	saveTasks();
    }
    
    private void loadCategories() throws IOException, JSONException {
    	HashMap<String, ICategory> categories = readerWriter.loadCategoriesFromFile();
    	this.categories = categories;
    }
    
    private void saveCategories() throws IOException {
    	readerWriter.saveCategoriesToFile(getAllCategories());
    }
    
    private void loadTasks() throws IOException, JSONException {
    	List<ITask> tasks = readerWriter.loadTasksFromFile();
    	for (ITask task: tasks) {
    	    this.addTask(task);
    	}
	}   
    
    private void saveTasks() throws IOException {
    	readerWriter.saveTasksToFile(this.getAllTasks());
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
        return !task.getEndDate().equals(Date.getFloatingDate());
    }
    
    private static boolean isOverdue(ITask task) {
    	Date today = new Date();
    	return (task.getEndDate().compareTo(today) < 0);    	
    }
    
    private static boolean hasCategory(ITask task) {
    	return (task.getCategory() != null && 
    		   task.getCategory().getName() != null &&
    		   !task.getCategory().getName().isEmpty());
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
