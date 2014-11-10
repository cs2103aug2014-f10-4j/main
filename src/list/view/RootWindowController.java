package list.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import list.CommandParser;
import list.Controller;
import list.NextCommand;
import list.PrevCommand;
import list.IParser.ParseException;
import list.model.ICategory;
import list.model.ITask;

public class RootWindowController implements IUserInterface {
	
	private static final double DURATION_CATEGORY = 0.2;
	
	@FXML
	private static final DropShadow DROP_SHADOW = new DropShadow(3.0d, Color.WHITE);
    @FXML
	private Pane rootPane;
	@FXML
    private TextField console;
	//@author A0126722L
	@FXML
	private Button buttonToHome;
	@FXML
	private Button buttonToCategory;
	@FXML
	private Button buttonToNext;
	@FXML
	private Button buttonToPrev;
	//@author A0126722L
	@FXML
	private Label labelPageTitle;
	
	private Pane taskDetail;
	//@author A0126722L
	private ScrollPane help;
	private ScrollPane paneForCategories;
	//@author A0126722L
	private Pane taskOverview;
	//@author A0126722L
	private Pane congratulations;
	//@author A0126722L
	private boolean isShowingCategories = false;
	private String pageTitle;
   
	private TaskOverviewController taskOverviewController;
	private TaskDetailController taskDetailController;
	//@author A0126722L
	private CategoriesController categoriesController;
	private HelpController helpController;
	private CongratulationsController congratulationsController;
	//@author A0126722L
	private CommandParser parser = new CommandParser();
	
	
	
    @Override
    public void displayTaskDetail(ITask task) {
    	int taskNumber = taskOverviewController.getTaskNumber(task);
        showTaskDetailLayout();
        taskDetailController.setParentController(this);
        taskDetailController.displayTaskDetail(task, taskNumber);
        
    }
    
    @Override
	public void hideTaskDetail() {
		rootPane.getChildren().remove(taskDetail);
		console.requestFocus();
	}
    //@author A012//@author A0126722L6722L
    @Override
    public void displayCategories(List<ICategory> categories) {
        categoriesController.clearAll();
    	categoriesController.setUpView(categories);
    	categoriesController.setParentController(this);
    	animateCategoryAndTaskOverview(true);
    	isShowingCategories = true;
    }
    
    @Override
	public void hideCategories() {
    	animateCategoryAndTaskOverview(false);    	
    	isShowingCategories = false;
	}
    @Override
    public void displayHelp() {
        showHelpLayout();
        helpController.setParentController(this);        
    }
    
    @Override
	public void hideHelp() {
		rootPane.getChildren().remove(help);
		console.requestFocus();
	}
    
    @Override
    public void displayCongratulations(List<ITask> floatingTasks) {
    	showCongratulationsLayout();
    	congratulationsController.setUpView(floatingTasks);
    	congratulationsController.setParentController(this);
    };

	@Override
    public void hideCongratulations() {
		rootPane.getChildren().remove(congratulations);
		console.requestFocus();
	}
	//@author A0126722L
    @Override
    public void setDisplayItems(String pageTitle, List<ITask> tasks) {
        this.pageTitle = pageTitle;
        taskOverviewController.setDisplayTasks(tasks);
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
		updatePageTitle();
		taskOverviewController.highlightTask(task);
	}

	@Override
    public void clearConsole() {
        console.setText("");
    }
    
	@Override
	public void refreshUI() {
        updatePageTitle();
		taskOverviewController.refresh();
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
        Platform.runLater(() -> {
            console.requestFocus();
        });
    }
	//@author A0126722L
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
    	    	NextCommand cmd = new NextCommand();
    	    	displayMessageToUser(cmd.execute());
    	    }
    	});
    	buttonToPrev.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	PrevCommand cmd = new PrevCommand();
    	    	displayMessageToUser(cmd.execute());
    	    }
    	});
    }
    //@author A0126722L
    private void setConsoleKeyPressHandler() {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.SPACE) || console.getText().isEmpty()) {
                    showSyntaxSuggestion();   
                } else if (event.getCode() == KeyCode.TAB) {
                    console.requestFocus();
                    event.consume();
                }
            }

            private void showSyntaxSuggestion() {
                try {
                    parser.clear();
                    parser.append(console.getText());
                    String expected = parser.getExpectedInputs();
                    displayMessageToUser(expected);
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
    //@author A0126722L
    private void showCategoriesLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/Categories.fxml"));

            paneForCategories = (ScrollPane) loader.load();

            categoriesController = loader.getController();

            paneForCategories.setLayoutX(0);
            paneForCategories.setLayoutY(42);
            paneForCategories.setHbarPolicy(ScrollBarPolicy.NEVER);
            paneForCategories.setVbarPolicy(ScrollBarPolicy.NEVER);
            rootPane.getChildren().add(paneForCategories);
            paneForCategories.requestFocus();
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    //@author A0126722L
    private void showTaskDetailLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/TaskDetail.fxml"));
            
            taskDetail = (Pane) loader.load();
            
            taskDetailController = loader.getController();
            taskDetail.setEffect(DROP_SHADOW);
            taskDetail.setLayoutX(120);
            taskDetail.setLayoutY(60);
            rootPane.getChildren().add(taskDetail);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    //@author A0126722L
    private void showHelpLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/Help.fxml"));
            
            help = (ScrollPane) loader.load();
            
            helpController = loader.getController();
            help.setEffect(DROP_SHADOW);
            help.setLayoutX(50);
            help.setLayoutY(33);
            help.setHbarPolicy(ScrollBarPolicy.NEVER);
            help.setVbarPolicy(ScrollBarPolicy.NEVER);
            rootPane.getChildren().add(help);
            help.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    //@author A0126722L
    
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
    
    //@author A0126722L
    private void showCongratulationsLayout() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("view/Congratulations.fxml"));
            
            congratulations = (Pane) loader.load();
            
            congratulationsController = loader.getController();
            congratulations.setEffect(DROP_SHADOW);
            congratulations.setLayoutX(120);
            congratulations.setLayoutY(60);
            rootPane.getChildren().add(congratulations);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    //@author A0126722L
    private void animateCategoryAndTaskOverview(boolean willDisplay) {
    	if (willDisplay) {
    		TranslateTransition translateForTaskOverview;
    		translateForTaskOverview = new TranslateTransition(Duration.seconds(DURATION_CATEGORY), taskOverview);
    		translateForTaskOverview.setToX(140);
    		translateForTaskOverview.setCycleCount(1);
    		translateForTaskOverview.setAutoReverse(false);
    		translateForTaskOverview.play();
    		taskOverview.requestFocus();
    	} else {
    		TranslateTransition translateForTaskOverview;
    		translateForTaskOverview = new TranslateTransition(Duration.seconds(DURATION_CATEGORY), taskOverview);
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
    	hideHelp();
    	hideTaskDetail();
        String userInput = console.getText();
        Controller.processUserInput(userInput);
    }

    private void updatePageTitle() {
        String title = pageTitle;
	    if (title == null) {
	        title = "";
	    }
	    labelPageTitle.setText(title);
    }

   
}
