package list;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import list.ICommand.CommandExecutionException;
import list.IParser.ParseException;
import list.model.ICategory;
import list.model.ITask;
import list.util.Constants;
import list.view.IUserInterface;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.json.JSONException;

public class Controller extends Application {
	private static final String SAVE_FILE_NAME = "tasks_list.json";
    private static final String LOAD_ERROR_MASTHEAD = "LOADING FAILED";
    private static final String APPLICATION_NAME = "LIST";	
	private Stage primaryStage;
	private Pane root;
    
	private static final String MESSAGE_UNKNOWN_ERROR = "Unknown error!";
    private static final String MESSAGE_ERROR_LOADING = "There is unknown error when loading data.";
	private static final String MESSAGE_ERROR_INVALID_JSON_FORMAT;
	static {
	    File saveFile = new File(SAVE_FILE_NAME);
	    MESSAGE_ERROR_INVALID_JSON_FORMAT = String.format(
	            "Data is not in a valid JSON format. Please repair the save file to a valid JSON format " + 
	            "and then relaunch LIST. Path to file:\n\n%s", saveFile.getAbsolutePath());
	}
	private static final String MESSAGE_ERROR_SAVING = "Error saving data";
    
	private static IUserInterface userInterface = null;
	private static IParser parser = new CommandParser();
	private static TaskManager taskManager = TaskManager.getInstance();
	private static List<ITask> displayedTasks = null;
	private static ITask displayedTaskDetail = null;
	
	private static Stack<ICommand> undoStack = new Stack<ICommand>();
	private static Stack<ICommand> redoStack = new Stack<ICommand>();
	private static Controller singletonInstance = null;
	private static ICategory categoryOnDisplay;
	
	public static Controller getInstance() {
		return singletonInstance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	    singletonInstance = this;
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
	
		
		initializeMainLayout();
		
		loadInitialData();
		
		displayCurrentTasks();
		refreshUI();
	}
	
    private void initializeMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/RootWindow.fxml"));
            
            root = (Pane) loader.load();
            userInterface = loader.getController();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
	public static String processUserInput(String userInput) {
	    String reply;
        try {
            ICommand commandMadeByParser = parser.parse(userInput);
            reply = commandMadeByParser.execute();
            ICommand inverseCommand = commandMadeByParser.getInverseCommand();
            if (inverseCommand != null) {
                undoStack.add(inverseCommand);
                redoStack.clear();
            }            
            
        } catch (ParseException e) {
            reply = e.getMessage();
        } catch (CommandExecutionException e) {
            reply = e.getMessage();
        } catch (IOException e) {
        	reply = MESSAGE_ERROR_SAVING;
        } catch (Exception e) {
            reply = MESSAGE_UNKNOWN_ERROR;
            e.printStackTrace();
        }
        
        userInterface.displayMessageToUser(reply);
        
        return reply;
	}

	public static ITask getTaskWithNumber(int taskNumber) {
	    int taskId = taskNumber - 1;
		return displayedTasks.get(taskId);
	}
	
	public static boolean hasTaskWithNumber(int taskNumber) {
		return (taskNumber > 0 && taskNumber <= displayedTasks.size());		
	}
	
	public static ITask getLastDisplayedTaskDetail() {
	    return displayedTaskDetail;
	}
	
	//UI FUNCTIONS
	public static void displayTaskDetail(ITask selectedTask) {
	    userInterface.displayTaskDetail(selectedTask);
	}

	public static void displayTasks(String pageTitle, List<ITask> tasks) {
		userInterface.setDisplayItems(pageTitle, tasks);
		rememberDisplayedTasks(tasks);
	}

    public static void displayCategories() {
        userInterface.displayCategories(taskManager.getAllCategories());
    }
    
    public static void hideCategories() {
        userInterface.hideCategories();
    }
    
    public static void displayHelp() {
        userInterface.displayHelp();
    }
    
    public static void hideHelp() {
        userInterface.hideHelp();
    }
    
    public static void reportCategoryDelete(ICategory category) {
    	if (categoryOnDisplay == category) {
    		displayCurrentTasks();
    		refreshUI();
    	}
    }
    
    private static void rememberDisplayedTasks(List<ITask> tasks) {
        displayedTasks = tasks;
    }
	 
    public static boolean next() {
    	return userInterface.next();
    }
    
    public static boolean back() {
    	return userInterface.back();
    }
    
    public static void highlightTask(ITask task) {
    	userInterface.highlightTask(task);
	}
    
    public static boolean hasTask(ITask task) {
    	return displayedTasks.contains(task);
    }
    
    public static void refreshUI() {
    	userInterface.refreshUI();
    }
    
	private static void displayCurrentTasks() {
		displayTasks(Constants.CURRENT_TASKS, taskManager.getCurrentTasks());
	}
	
	private static void displayFloatingTasks() {
		displayTasks(Constants.FLOATING_TASKS, taskManager.getFloatingTasks());
	}
	
	private static void displayOverdueTasks() {
		displayTasks(Constants.OVERDUE_TASKS, taskManager.getOverdueTasks());
	}
	
	public static void displayHome() {
	    displayCurrentTasks();
    }
	
	private static void displayTasksInCategory(ICategory category) {
		categoryOnDisplay = category;
		displayTasks(category.getName().toUpperCase(), category.getList());
	}
	
//	public static boolean changeDisplayMode(String name) {
//		String viewingMode = name.toLowerCase().trim();
//		
//		if (viewingMode.equalsIgnoreCase("floating") ||
//			viewingMode.equalsIgnoreCase("overdue") || 
//			viewingMode.equalsIgnoreCase("current")) {
//			displayMode = viewingMode;
//			return true;
//		} else {
//			if (taskManager.hasCategory(viewingMode)) {
//				displayMode = viewingMode;
//				return true;
//			} else {
//				return false;
//			}
//		}
//	}

	public static boolean displayTasksBasedOnDisplayMode(String displayMode) {
		categoryOnDisplay = null;
		if (displayMode.equalsIgnoreCase("floating")) {
			displayFloatingTasks();
			return true;
		} else if (displayMode.equalsIgnoreCase("overdue")) {
			displayOverdueTasks();
			return true;
		} else if (displayMode.equalsIgnoreCase("current")) {
			displayCurrentTasks();
			return true;
		} else {
			if (taskManager.hasCategory(displayMode)) {
				ICategory category = taskManager.getCategory(displayMode);
				displayTasksInCategory(category);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static void updateUiWithTaskDetail(ITask selectedTask) {
		userInterface.displayTaskDetail(selectedTask);
	}
	
	static Stack<ICommand> getUndoStack() {
	    return undoStack;
	}
	
	static Stack<ICommand> getRedoStack() {
	    return redoStack;
	}
	
	//TODO: Error with UI when loading
	public static void loadInitialData() {
		try {
			taskManager.loadData();
		} catch (IOException e) {
		    e.printStackTrace();
			showError(LOAD_ERROR_MASTHEAD, MESSAGE_ERROR_LOADING);
		} catch (JSONException e) {
			showError(LOAD_ERROR_MASTHEAD, MESSAGE_ERROR_INVALID_JSON_FORMAT);
		    e.printStackTrace();
			System.exit(1);
		}
	}

    @SuppressWarnings("deprecation")
    private static void showError(String title, String errorMessage) {
        Dialogs.create()
            .title("LIST")
            .masthead(title)
            .message(errorMessage)
            .styleClass(Dialog.STYLE_CLASS_UNDECORATED)
            .showError();
    }
	
	public static void main(String[] args) {
	    launch(args);
	}
}
