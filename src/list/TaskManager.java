package list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import list.model.Category;
import list.model.ICategory;
import list.model.ITask;
import list.model.ITask.TaskStatus;

import org.json.JSONException;

/**
 * This is a Singleton class that keeps track of Tasks and Categories. Execution
 * of commands is not handled by this class but by the individual command types.
 * 
 * This class modifies the user interface by the methods given in
 * <code>IUserInterface</code>.
 * 
 * @author andhieka, michael
 */
public class TaskManager {

	private List<ITask> floatingTasks = new ArrayList<ITask>();
	private List<ITask> currentTasks = new ArrayList<ITask>();
	private List<ITask> overdueTasks = new ArrayList<ITask>();

	private Map<String, ICategory> categories = new HashMap<String, ICategory>();

	// private List<ITask> floatingTasks = new ArrayList<ITask>();
	private List<ITask> tasks = new ArrayList<ITask>();
	private Stack<ITask> deletedTasks = new Stack<ITask>();

	private ReaderWriter readerWriter = new ReaderWriter();

	private static TaskManager taskManagerInstance = null;

	private TaskManager() {
	}

	static TaskManager getInstance() {
		if (taskManagerInstance == null) {
			taskManagerInstance = new TaskManager();
		}

		return taskManagerInstance;
	}

	// CATEGORY METHODS
	/**
	 * Creates a new category with the specified name in the input
	 * 
	 * @param categoryName
	 * @return true if new category has successfully been added. false
	 *         otherwise.
	 */
	private boolean addCategory(String categoryName) {
		if (categoryName == null || categoryName.isEmpty()) {
			return false;
		}

		ICategory category = new Category();
		category.setName(categoryName);
		categories.put(categoryName.trim().toLowerCase(), category);

		return true;
	}

	boolean hasCategory(String categoryName) {
        categoryName = categoryName.trim().toLowerCase();
		return categories.containsKey(categoryName);
	}

	/**
	 * Deletes the given category from the manager's category list.
	 * 
	 * @param category
	 */
	void deleteCategory(ICategory category) {
		String categoryName = category.getName().trim().toLowerCase();
		if (categories.containsKey(categoryName)) {
			categories.remove(categoryName);
		}
	}
	
	void moveCategory(String oldName, String newName) {
		if (categories.containsKey(oldName.trim().toLowerCase())) {
			ICategory category = categories.get(oldName.trim().toLowerCase());
			categories.remove(oldName.trim().toLowerCase());
			categories.put(newName.trim().toLowerCase(), category);
		}
	}

	/**
	 * Accepts a string and returns a ICategory object with the same name as the
	 * input. If the input is <code>null</code>, will return a default category
	 * object.
	 * 
	 * @param categoryName
	 * @return
	 */
	ICategory getCategory(String categoryName) {
		// return a default category if undefined by user
		if (categoryName == null || categoryName.isEmpty()) {
			return Category.getDefaultCategory();
		}

		if (!categories.containsKey(categoryName.trim().toLowerCase())) {
			addCategory(categoryName);
		}

		return categories.get(categoryName.trim().toLowerCase());
	}

	List<ICategory> getAllCategories() {
		List<ICategory> categoryList = new ArrayList<ICategory>();
		for (ICategory category : this.categories.values()) {
			if (!category.getName().equals("")) {
				categoryList.add(category);
			}
		}
		return categoryList;
	}

	void addToFloatingTasks(ITask task) {
		floatingTasks.add(task);
	}

	void addToCurrentTasks(ITask task) {
		currentTasks.add(task);
	}

	void addToOverdueTasks(ITask task) {
		overdueTasks.add(task);
	}

	void removeFromFloatingTasks(ITask task) {
		floatingTasks.remove(task);
	}

	void removeFromCurrentTasks(ITask task) {
		floatingTasks.remove(task);
	}

	void removeFromOverdueTasks(ITask task) {
		floatingTasks.remove(task);
	}

	// TASK GETTERS
	/**
	 * Gets all tasks in the TaskManager. The list of all tasks returned by
	 * method will be used for storage purposes, and not displaying purposes,
	 * hence, there is no need to keep the list of all tasks sorted
	 * 
	 * @return list of all tasks in TaskManager
	 */
	List<ITask> getAllTasks() {
		int sizeAllTasks = floatingTasks.size() + currentTasks.size()
				+ overdueTasks.size();

		List<ITask> allTasks = new ArrayList<ITask>(sizeAllTasks);
		for (ITask task : floatingTasks) {
			allTasks.add(task);
		}
		for (ITask task : currentTasks) {
			allTasks.add(task);
		}
		for (ITask task : overdueTasks) {
			allTasks.add(task);
		}

		return allTasks;
	}

	List<ITask> getFloatingTasks() {
		Collections.sort(this.floatingTasks);

		return this.floatingTasks;
	}

	List<ITask> getCurrentTasks() {
		Collections.sort(this.currentTasks);

		return this.currentTasks;
	}

	List<ITask> getOverdueTasks() {
		Collections.sort(this.overdueTasks);

		return this.overdueTasks;
	}

	// METHODS FOR COMMANDS EXECUTION
	void addTask(ITask task) {
		if (task.hasDeadline()) {
			if (task.isOverdue()) {
				overdueTasks.add(task);
				task.setList(overdueTasks);
				Collections.sort(overdueTasks);
			} else {
				currentTasks.add(task);
				task.setList(currentTasks);
				Collections.sort(currentTasks);
			}
		} else {
			floatingTasks.add(task);
			task.setList(floatingTasks);
			Collections.sort(floatingTasks);
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
		if (task.hasDeadline()) {
			tasks.remove(task);
		} else {
			floatingTasks.remove(task);
		}
		deletedTasks.push(task);
	}

	void deleteTask(ITask task) {
		if (task.hasDeadline()) {
			if (task.isOverdue()) {
				overdueTasks.remove(task);
			} else {
				currentTasks.remove(task);
			}
		} else {
			floatingTasks.remove(task);
		}

		if (hasCategory(task)) {
			ICategory category = task.getCategory();
			category.getList().remove(task);
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
		return taskNumberShownOnScreen > 0
				&& taskNumberShownOnScreen <= tasks.size();
	}

	/**
	 * Clears all references to all tasks. Danger: not undo-able. Used for
	 * testing.
	 */
	void clearTasks() {
		floatingTasks.clear();
		currentTasks.clear();
		overdueTasks.clear();
		deletedTasks.clear();
		
		for (ICategory category: this.categories.values()) {
			category.getList().clear();
		}
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
		HashMap<String, ICategory> categories = readerWriter
				.loadCategoriesFromFile();
		this.categories = categories;
	}

	private void saveCategories() throws IOException {
		readerWriter.saveCategoriesToFile(getAllCategories());
	}

	private void loadTasks() throws IOException, JSONException {
		List<ITask> tasks = readerWriter.loadTasksFromFile();
		for (ITask task : tasks) {
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
		return tasks.contains(task) || floatingTasks.contains(task);
	}

	private static boolean hasCategory(ITask task) {
		return (task.getCategory() != null
				&& task.getCategory().getName() != null && !task.getCategory()
				.getName().isEmpty());
	}

	/**
	 * Converts a 1-based id to 0-based id as represented in the array.
	 * 
	 * @param taskNumberShownOnScreen
	 *            1-based index
	 * @return 0-based index
	 */
	private static int getTaskId(Integer taskNumberShownOnScreen) {
		return taskNumberShownOnScreen - 1;
	}
}
