package list.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import list.CommandParser;
import list.Controller;
import list.IParser.ParseException;
import list.model.ICategory;
import list.model.ITask;

public class RootWindowController implements IUserInterface {
	
	@FXML
	private Pane rootPane;
	@FXML
    private TextField console;
	@FXML
	private Button buttonToHome;
	@FXML
	private Button buttonToCategory;
	@FXML
	private Button buttonToNext;
	@FXML
	private Button buttonToPrev;
	@FXML
	private Label labelPageTitle;
	
	private Pane taskDetail;
	private ScrollPane paneForCategories;
	private Pane taskOverview;
	private boolean isShowingCategories = false;
	private String pageTitle;
   
	private TaskOverviewController taskOverviewController;
	private TaskDetailController taskDetailController;
	private CategoriesController categoriesController;
	private CommandParser parser = new CommandParser();
	
	
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
    public void displayCategories(List<ICategory> categories) {
    	categoriesController.setUpView(categories);
    	animateCategoryAndTextOverview(true);
    	isShowingCategories = true;
    }
    
    @Override
	public void hideCategories() {
    	animateCategoryAndTextOverview(false);    	
    	isShowingCategories = false;
	}

    @Override
    public void setDisplayItems(String pageTitle, List<ITask> tasks) {
        this.pageTitle = pageTitle;
        taskOverviewController.setDisplayTasks(tasks);
    }

    @Override
    public void clearDisplay() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void displayMessageToUser(String message) {
        taskOverviewController.displayMessageToUser(message);
    }

    @Override
    public boolean back() {
        return taskOverviewController.back();
    }

    @Override
    public boolean next() {
        return taskOverviewController.next();
    }

	@Override
	public void highlightTask(ITask task) {
		taskOverviewController.highlightTask(task);
	}

	@FXML
    private void initialize() {
        showTaskOverviewLayout();
        setUpButtons();
        console.setOnAction((event) -> {
            handleEnterAction();
        });
        
        setConsoleKeyPressHandler();
        setWindowKeyPressHandler();
    }
    
    private void setUpButtons() {
    	buttonToHome.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	Controller.displayHome();
    	    	Controller.refreshUI();
    	    }
    	});
    	buttonToCategory.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	if(!isShowingCategories){
    	    		Controller.displayCategories();
    	    	} else {
    	    		hideCategories();
    	    	}
    	    }
    	});
    	buttonToNext.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	boolean success = next();
    	    	if (!success) {
    	    	    //show something
    	    	}
    	    }
    	});
    	buttonToPrev.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	back();
    	    }
    	});
    }

    private void setConsoleKeyPressHandler() {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.SPACE)) {
                    showSyntaxSuggestion();   
                }
            }

            private void showSyntaxSuggestion() {
                try {
                    parser.clear();
                    parser.append(console.getText());
                    Map<String, String> expected = parser.getExpectedInputs();
                    StringBuilder expectedStr = new StringBuilder();
                    for (String key: expected.keySet()) {
                        expectedStr.append(key).append(": ").append(expected.get(key)).append(" | ");
                    }
                    displayMessageToUser(expectedStr.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            
        };
        console.setOnKeyPressed(handler);
    }
    
    private void setWindowKeyPressHandler() {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(!event.getText().isEmpty()) {
                    console.requestFocus();
                }
            }
            
        };
        rootPane.setOnKeyPressed(handler);
    }
    
    private void showCategoriesLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/Categories.fxml"));

            paneForCategories = (ScrollPane) loader.load();

            categoriesController = loader.getController();

            paneForCategories.setLayoutX(0);
            paneForCategories.setLayoutY(42);
            rootPane.getChildren().add(paneForCategories);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    private void showTaskDetailLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/TaskDetail.fxml"));
            
            taskDetail = (Pane) loader.load();
            
            taskDetailController = loader.getController();
            taskDetail.setEffect(new DropShadow(2.0d, Color.BLACK));
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
            
            taskOverview = (Pane) loader.load();
            
            taskOverviewController = loader.getController();
            
            taskOverview.setLayoutX(0);
            taskOverview.setLayoutY(42);
            taskOverview.setEffect(new DropShadow());
            showCategoriesLayout();
            rootPane.getChildren().add(taskOverview);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    private void animateCategoryAndTextOverview(boolean willDisplay) {
    	if (willDisplay) {
    		TranslateTransition translateForTaskOverview;
    		translateForTaskOverview = new TranslateTransition(Duration.seconds(1), taskOverview);
    		translateForTaskOverview.setToX(140);
    		translateForTaskOverview.setCycleCount(1);
    		translateForTaskOverview.setAutoReverse(false);
    		translateForTaskOverview.play();
    	} else {
    		TranslateTransition translateForTaskOverview;
    		translateForTaskOverview = new TranslateTransition(Duration.seconds(1), taskOverview);
    		translateForTaskOverview.setToX(0);
    		translateForTaskOverview.setCycleCount(1);
    		translateForTaskOverview.setAutoReverse(false);
    		
    		translateForTaskOverview.play();
    		
    	}
    }

    /**
     * This method is called when user presses 'enter' on keyboard
     * after writing a command in the console.
     */
    @FXML
    private void handleEnterAction() {
    	if (isShowingCategories) {
    		hideCategories();
    	}
    	
        String userInput = console.getText();
        String reply = Controller.processUserInput(userInput);
        displayMessageToUser(reply);
        
        //labelTask1.requestFocus(); //set focus to something else
        console.setText("");
        //console.promptTextProperty();
    }

	@Override
	public void refreshUI() {
	    updatePageTitle();
		taskOverviewController.refresh();
	}

    private void updatePageTitle() {
        String title = pageTitle;
	    if (title == null) {
	        title = "";
	    }
	    labelPageTitle.setText(title);
    }
}
