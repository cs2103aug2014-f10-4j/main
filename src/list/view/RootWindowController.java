package list.view;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import list.Controller;
import list.model.ICategory;
import list.model.ITask;

public class RootWindowController implements IUserInterface {
	private Pane taskDetail;
	@FXML
	private Pane rootPane;
	@FXML
    private TextField console;
	@FXML
	private Label labelFeedback;
   
	private TaskOverviewController taskOverviewController;
	private TaskDetailController taskDetailController;
	
    @Override
    public void displayTaskDetail(ITask task) {
    	int taskNumber = taskOverviewController.getTaskNumber(task);
        showTaskDetailLayout();
        taskDetailController.getParentController(this);
        taskDetailController.displayTaskDetail(task, taskNumber);
        
    }
    
    @Override
	public void hideTaskDetail() {
		rootPane.getChildren().remove(taskDetail);
		console.requestFocus();
	}

    @Override
    public void display(String pageTitle, List<ITask> tasks) {
        taskOverviewController.displayTasks(tasks);
        
    }

    @Override
    public void clearDisplay() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void displayMessageToUser(String message) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void displayCategories(List<ICategory> categories) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void hideCategories() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public boolean back() {
        return taskOverviewController.back();
    }

    @Override
    public boolean next() {
        return taskOverviewController.next();
    }

    @FXML
    private void initialize() {
    	console.requestFocus();
        showTaskOverviewLayout();
        
        console.setOnAction((event) -> {
            handleEnterAction();
        });        
    }
    
    private void showTaskDetailLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/TaskDetail.fxml"));
            
            taskDetail = (Pane) loader.load();
            
            taskDetailController = loader.getController();
            
            taskDetail.setLayoutX(120);
            taskDetail.setLayoutY(75);
            rootPane.getChildren().add(taskDetail);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    private void showTaskOverviewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/TaskOverview.fxml"));
            
            Pane taskOverview = (Pane) loader.load();
            
            taskOverviewController = loader.getController();
            
            taskOverview.setLayoutX(0);
            taskOverview.setLayoutY(40);
            rootPane.getChildren().add(taskOverview);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * This method is called when user presses 'enter' on keyboard
     * after writing a command in the console.
     */
    @FXML
    private void handleEnterAction() {
        String userInput = console.getText();
        String reply = Controller.processUserInput(userInput);
        displayMessageToUser(reply);
        
        //labelTask1.requestFocus(); //set focus to something else
        console.setText("");
        //console.promptTextProperty();
    }

}
