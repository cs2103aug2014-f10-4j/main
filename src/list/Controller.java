package list;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import list.ICommand.CommandExecutionException;
import list.IParser.ParseException;
import list.model.ICategory;
import list.model.ITask;
import list.view.IUserInterface;
import list.view.MainController;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.json.JSONException;

public class Controller extends Application {
	private static final String SAVE_FILE_NAME = "tasks_list.json";
    private static final String LOAD_ERROR_MASTHEAD = "LOADING FAILED";
    private static final String APPLICATION_NAME = "LIST";	
	private Stage primaryStage;
	private Pane rootLayout;
	
    
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
	private static boolean isUndoRedoOperation = false;
	private static Controller singletonInstance = null;
	
	public static Controller getInstance() {
		return singletonInstance;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	    singletonInstance = this;
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
	
		initializeMainLayout();
		showTaskOverviewLayout();
		
		loadInitialData();		
	}
	
	private void initializeMainLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controller.class.getResource("view/RootLayout.fxml"));
			
            rootLayout = (Pane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
		} 
	}
	
	private void showTaskOverviewLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controller.class.getResource("view/TaskOverviewLayout.fxml"));
			
            Pane taskOverviewLayout = (Pane) loader.load();
            
            taskOverviewLayout.setLayoutX(0);
            taskOverviewLayout.setLayoutY(40);
            rootLayout.getChildren().add(taskOverviewLayout);
            
        } catch (IOException e) {
            e.printStackTrace();
		} 
	}
	
	
	public static String processUserInput(String userInput) {
	    String reply;
        try {
            isUndoRedoOperation = false;
            ICommand commandMadeByParser = parser.parse(userInput);
            reply = commandMadeByParser.execute();
            ICommand inverseCommand = commandMadeByParser.getInverseCommand();
            if (inverseCommand != null) {
                undoStack.add(inverseCommand);
            }
            if (!isUndoRedoOperation) {
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
	    if (userInterface == null) {
            userInterface = MainController.getInstance();
        }
		userInterface.displayTaskDetail(selectedTask);
		Controller.displayedTaskDetail = selectedTask;
	}

	public static void displayTasks(String pageTitle, ObservableList<ITask> tasks) {
		if (userInterface == null) {
		    userInterface = MainController.getInstance();
		}
        userInterface.display(pageTitle, tasks);
		rememberDisplayedTasks(tasks);
	    displayCategories();
	}

    private static void displayCategories() {
        if (userInterface == null) {
            userInterface = MainController.getInstance();
        }
        userInterface.updateCategory(taskManager.getAllCategories());
    }

    private static void rememberDisplayedTasks(ObservableList<ITask> tasks) {
        displayedTasks = tasks;
    }
	 
	public static void displayCurrentTasks() {
		displayTasks("CURRENT TASK", taskManager.getCurrentTasks());
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
	
	static void reportUndoRedoOperation() {
	    isUndoRedoOperation = true;
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
