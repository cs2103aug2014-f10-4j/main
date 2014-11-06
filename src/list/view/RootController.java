package list.view;

import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import list.Controller;
import list.model.ICategory;
import list.model.ITask;

public class RootController implements IUserInterface {
	@FXML
	private Pane rootPane;
	@FXML
    private TextField console;
	@FXML
	private Label labelFeedback;
    
	private TaskOverviewController taskOverviewController;
	
    @Override
    public void displayTaskDetail(ITask task) {
        showTaskDetailLayout();
    }
    
    @Override
	public void hideTaskDetail() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void display(String pageTitle, ObservableList<ITask> tasks) {
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean next() {
        // TODO Auto-generated method stub
        return false;
    }
	

    @FXML
    private void initialize() {
        showTaskOverviewLayout();
        console.setOnAction((event) -> {
            handleEnterAction();
        });
        
    }
    
    private void showTaskDetailLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/TaskDetail.fxml"));
            
            Pane taskDetail = (Pane) loader.load();
            
            //taskOverviewController = loader.getController();
            
            taskDetail.setLayoutX(125);
            taskDetail.setLayoutY(150);
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
