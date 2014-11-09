package list.view;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Callback;
import list.Controller;
import list.model.ITask;

public class CongratulationsController {

    @FXML
    private ListView<String> listView;
    @FXML
    private Button buttonDone;
    
	private RootWindowController rootController;
	
	private static final String MESSAGE_EDITED_SUCCESS = "Task is successfully edited";
	private static final String MESSAGE_ADDED_SUCCESS = "Task is successfully added";
	private static final String HELVETICA_NEUE = "Helvetica Neue";	
	
	private static Glow glow = new Glow(0.5);
	
	private ObservableList<String> observableTaskTitles;
	private List<String> selectedTitles = new ArrayList<String>();
	private List<ITask> floatingTasks;
	
	
	public void setUpView(List<ITask> floatingTasks) {
	    this.floatingTasks = floatingTasks;
	    populateListView();
	}

	public void setParentController(RootWindowController rootController) {
		this.rootController = rootController;
	}
	
	public void populateListView() {
		List<String> taskTitles = new ArrayList<String>();
		for(int i = 0; i < floatingTasks.size(); i++) {
			taskTitles.add(floatingTasks.get(i).getTitle());
		}
		observableTaskTitles = FXCollections.observableArrayList(taskTitles);
		listView.setItems(observableTaskTitles);
		listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new CellFactory(selectedTitles);
			}
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
		    if (event.getCode() == KeyCode.ENTER) {
                handleDoneAction();
            } else if (event.getCode() == KeyCode.SPACE) {
                String selectedString = listView.getSelectionModel().getSelectedItem();
                if(selectedTitles.contains(selectedString)) {
                    selectedTitles.remove(selectedString);
                } else {
                    selectedTitles.add(selectedString);
                }
                updateCellColor();
            } else if (event.getCode() == KeyCode.TAB) {
                DropShadow dropShadow = new DropShadow(20,Color.WHITE);
                buttonDone.setEffect(dropShadow);
            }
		});
		buttonDone.setOnKeyPressed((event) -> {
		    if (event.getCode() == KeyCode.ENTER) {
                handleDoneAction();
            } else if (event.getCode() == KeyCode.TAB) {
                buttonDone.setEffect(null);
            }
		});
	}

	private void handleDoneAction() {
		String reply = "";
		for(int i = 0; i < selectedTitles.size(); i++) {
			StringBuilder inputStringBuilder = new StringBuilder();
			inputStringBuilder.append("edit " + (selectedTitles.get(i) + 1) + " -d today");
			reply = Controller.processUserInput(inputStringBuilder.toString());
		}
		if (reply.equals(MESSAGE_EDITED_SUCCESS)) {
			rootController.displayMessageToUser(MESSAGE_ADDED_SUCCESS);
			rootController.hideCongratulations();
		}
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
                    label.setTextFill(Color.TURQUOISE);
                } else {
                    label.setTextFill(Color.BLACK);
                }
                label.setFont(Font.font(HELVETICA_NEUE, 12.0d));
                setGraphic(label);
            }
        }
    }
}
