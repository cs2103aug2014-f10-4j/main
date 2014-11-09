package list.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import list.model.ICategory;

/**
 * Handle all GUI of the Categories
 * There are buttons and labels
 * 
 * @author Shotaro
 */
public class CategoriesController {
	
	private RootWindowController rootController;
	
	// fixed value for the size and number of buttons
    private static final double BUTTON_X = 1.0d;
    private static final double BUTTON_WIDTH = 138.0d;
    private static final double BUTTON_HEIGHT = 26.0d;
    private static final double NUMBER_OF_DEFAULT_BUTTON = 3.0d;
    
    // fixed value for the size and number of labels
    private static final double LABEL_X = 1.0d;
    private static final double LABEL_Y = 0.0d;
    private static final double LABEL_WIDTH = 138.0d;
    private static final double LABEL_HEIGHT = 13.0d;
    private static final double NUMBER_OF_DEFAULT_LABEL = 2.0d;
    
    // fixed value for the size of pane
    private static final double PANE_X = 1.0d;
    private static final double PANE_Y = 0.0d;
    private static final double PANE_WIDTH = 138.0d;
    private double PANE_HEIGHT;
    
    // fixed value for the string of labels and buttons
    private static final String stringForLabelAllCategory = " Group by deadline";
    private static final String stringForLabelOtherCategory = " Categories";
    private static final String stringForButtonCurrentTask = "Current Task";
    private static final String stringForButtonFloatingTask = "Floating Task";
    private static final String stringForButtonOverDueTask = "Overdue Task";
    
    // lists to hold the value of tasks and categories to keep on track them
    private List<ICategory> listOfCategories;
    
    // position to start displaying the categories made by user
    private double positonToDisplayOthers = LABEL_HEIGHT * NUMBER_OF_DEFAULT_LABEL + BUTTON_HEIGHT * NUMBER_OF_DEFAULT_BUTTON;
    
    // ScrollPane to make the categories to be able to scroll
    @FXML
    ScrollPane paneContainer;
    private static final double VERTICAL_SCROLL_AMOUNT = 0.2;

    // Pane to hold the buttons and labels
    Pane categoriesContainer = new Pane();
    
    public void setUpView(List<ICategory> categories) {
    	
    	listOfCategories = categories;
    	
    	PANE_HEIGHT = LABEL_HEIGHT * NUMBER_OF_DEFAULT_LABEL + BUTTON_HEIGHT * (NUMBER_OF_DEFAULT_BUTTON + categories.size());
    	
    	// set the style, layout and the size of the Pane
    	categoriesContainer.setLayoutX(PANE_X);
        categoriesContainer.setLayoutY(PANE_Y);
        categoriesContainer.setPrefWidth(PANE_WIDTH);
        categoriesContainer.setPrefHeight(PANE_HEIGHT);
        categoriesContainer.setStyle("-fx-background-color: #333333;");
        
        // create the default label to be displayed
    	Label labelForAllCategory = setUpLabel(stringForLabelAllCategory, LABEL_X, LABEL_Y);
    	Label labelForOtherCategory = setUpLabel(stringForLabelOtherCategory, LABEL_X, LABEL_Y + LABEL_HEIGHT+BUTTON_HEIGHT * NUMBER_OF_DEFAULT_BUTTON);
    	
    	// create the default button to be displayed
    	Button buttonForCurrentTask = setUpButton(stringForButtonCurrentTask, BUTTON_X, LABEL_Y + LABEL_HEIGHT);
    	Button buttonForFloatingTask = setUpButton(stringForButtonFloatingTask, BUTTON_X, LABEL_Y + LABEL_HEIGHT+BUTTON_HEIGHT * 1);
    	Button buttonForOverDueTask = setUpButton(stringForButtonOverDueTask, BUTTON_X, LABEL_Y + LABEL_HEIGHT+BUTTON_HEIGHT * 2);
    
        // add the labels and buttons into the Pane
        categoriesContainer.getChildren().add(labelForAllCategory);
        categoriesContainer.getChildren().add(labelForOtherCategory);
        categoriesContainer.getChildren().add(buttonForCurrentTask);
        categoriesContainer.getChildren().add(buttonForFloatingTask);
        categoriesContainer.getChildren().add(buttonForOverDueTask);
        
        // display other categories on to the screen
        displayOtherCategory();
        
        // set the Pane on the ScrollPane
        paneContainer.setContent(categoriesContainer);
        
        //set ScrollPane key handler
        paneContainer.setOnKeyPressed((event) -> {
        	handleScrollPaneKeyPress(event);
        });
    }
    
    private Label setUpLabel(String title, double xPos, double yPos) {
    	
    	// set up the label according to the title, layout, and size
    	Label label = new Label();
    	label.setText(title);
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);
        label.setPrefWidth(LABEL_WIDTH);
        label.setPrefHeight(LABEL_HEIGHT);
        label.setFont(Font.font("Helvetica", 10.0d));
        label.setStyle("-fx-background-color: white; -fx-opacity: 60%;");
        label.setTextFill(Color.web("#000000"));
        
        // return the label created
        return label;
    }
    
    private Button setUpButton(String title, double xPos, double yPos) {
    	
    	// set up the button according to the title, layout, and size
    	Button button = new Button();
    	button.setText(title);
        button.setLayoutX(xPos);
        button.setLayoutY(yPos);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setFont(Font.font("Helvetica", 14.0d));
        button.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff;");
        
        // return the button created
        return button;
    }
    
    public void clearAll() {
    	
    	// clear all the content in the ScrollPane
        categoriesContainer.getChildren().clear();;
    	paneContainer.setContent(null);
    }
	
	private void displayOtherCategory() {
		
		// keep on track the current position 
		int currentPosition = 0;
		
		// check whether the category is already added
		for(int i = 0; i < listOfCategories.size(); i++) {				
				// set up the button according to the title, layout, and size
				Button button = new Button();
		        button.setText(listOfCategories.get(i).getName());
		        button.setLayoutX(BUTTON_X);
		        button.setLayoutY(positonToDisplayOthers + BUTTON_HEIGHT * currentPosition);
		        button.setPrefHeight(BUTTON_HEIGHT);
		        button.setPrefWidth(BUTTON_WIDTH);
		        button.setFont(Font.font("Helvetica", 14.0d));
		        button.setStyle("-fx-background-color: #333333;");
		        String s = Integer.toHexString(listOfCategories.get(i).getColor().getRGB());
		        button.setTextFill(Color.web("#" + s.substring(2, 8)));
		        // add the button on the Pane
		        categoriesContainer.getChildren().add(button);
			
		        currentPosition++;
		}
    }

	public void setParentController(RootWindowController rootController) {
		this.rootController = rootController;
	}
	
	private void handleScrollPaneKeyPress(KeyEvent event) {
		double currentPosition = paneContainer.getVvalue();
		double vmin = paneContainer.getVmin();
		double vmax = paneContainer.getVmax();
		if (event.getCode() == KeyCode.DOWN) {
			paneContainer.setVvalue(Math.min(vmax, currentPosition + VERTICAL_SCROLL_AMOUNT));
		} else if (event.getCode() == KeyCode.UP) {
			paneContainer.setVvalue(Math.max(vmin, currentPosition - VERTICAL_SCROLL_AMOUNT));
		}
		//do not consume event. must propagate to root
	}
}
