//@author A0126722L
package list.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Callback;
import list.Controller;
import list.model.ITask;

public class CongratulationsController {

    @FXML
    private Pane pane;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button buttonDone;
    
	private RootWindowController rootController;
	
	private static final String MESSAGE_MOVED_SUCCESS = "%d tasks moved to today midnight. Keep it up!";
	private static final String MESSAGE_DONE_FOR_THE_DAY = "Congratulations! You are done for the day!";
	private static final String FONT = "Helvetica Neue";	
	
	private static Glow glow = new Glow(0.5);
	
	private ObservableList<String> observableTaskTitles;
	private List<String> selectedTitles = new ArrayList<String>();
	private List<ITask> floatingTasks;
	private HashMap<String, ITask> taskMap = new HashMap<String, ITask>();
	
	
	void setUpView(List<ITask> floatingTasks) {
	    this.floatingTasks = floatingTasks;
	    populateListView();
	}

	void setParentController(RootWindowController rootController) {
		this.rootController = rootController;
	}
	
	private void populateListView() {
		List<String> taskTitles = new ArrayList<String>();
		for(int i = 0; i < floatingTasks.size(); i++) {
		    ITask task = floatingTasks.get(i);
		    String title = task.getTitle();
			taskTitles.add(title);
			taskMap.put(title, task);
		}
		observableTaskTitles = FXCollections.observableArrayList(taskTitles);
		listView.setItems(observableTaskTitles);
		listView.setCellFactory((callback) -> {
		    return new CellFactory(selectedTitles);
		}); 
	}
    
    private void updateCellColor() {
        populateListView();
    }
	
	@FXML
	private void initialize() {					
		buttonDone.setOnAction((event) -> {
			handleDoneAction();
		});
		listView.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                if (listView.getSelectionModel().getSelectedItem() == null) {
                    listView.getSelectionModel().select(0);
                } 
                String selectedString = listView.getSelectionModel().getSelectedItem();
                
                if (selectedTitles.contains(selectedString)) {
                    selectedTitles.remove(selectedString);
                } else {
                    selectedTitles.add(selectedString);
                }
                updateCellColor();                    
            } else if (event.getCode() == KeyCode.TAB) {
                DropShadow dropShadow = new DropShadow(20,Color.WHITE);
                buttonDone.setEffect(dropShadow);
                buttonDone.requestFocus();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                exitWithoutMovingTask();
            }
		});
		buttonDone.setOnKeyPressed((event) -> {
		    if (event.getCode() == KeyCode.ENTER) {
                handleDoneAction();
            } else if (event.getCode() == KeyCode.TAB) {
                buttonDone.setEffect(null);
                listView.requestFocus();
            }
		});
		pane.setOnKeyPressed((event) -> {
		    if (event.getCode() == KeyCode.ESCAPE) {
		        exitWithoutMovingTask();
		    }
		    event.consume();
		}); 
		Platform.runLater(() -> {
		    listView.requestFocus();
		});
	}
	//@author A0126722L
	private void handleDoneAction() {
	    List<ITask> selectedTasks = new ArrayList<ITask>();
		for(String title: selectedTitles) {
			selectedTasks.add(taskMap.get(title));
		}
		Controller.moveTasksToTodayMidnight(selectedTasks);
		if (selectedTasks.isEmpty()) {
		    exitWithoutMovingTask();
		} else {
	        rootController.hideCongratulations();
		    Controller.displayHome();
		    Controller.refreshUI();
		    rootController.displayMessageToUser(String.format(MESSAGE_MOVED_SUCCESS, selectedTasks.size()));		    
		}
	}
	//@author A0126722L
	private void exitWithoutMovingTask() {
        rootController.hideCongratulations();
        rootController.displayMessageToUser(MESSAGE_DONE_FOR_THE_DAY);
	}
	
    private static class CellFactory extends ListCell<String> {
        private List<String> selectedTitles;

        public CellFactory(List<String> selectedTitles) {
            this.selectedTitles = selectedTitles;
        }
        
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Label label = new Label(item);
            if (item != null) {
                if(selectedTitles.contains(item)){
                    label.setEffect(glow);
                    label.setTextFill(Color.ORANGE);
                } else {
                    label.setTextFill(Color.BLACK);
                }
                label.setFont(Font.font(FONT, 12.0d));
                setGraphic(label);
            }
        }
    }
}
//@author A0126722L