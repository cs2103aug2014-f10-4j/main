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
	
	
	private RootWindowController rootContoller;
	
	private static final String MESSAGE_EDITED_SUCCESS = "Task is successfully edited";
	private static final String MESSAGE_ADDED_SUCCESS = "Task is successfully added";
	
	private static final String HELVETICA_NEUE = "Helvetica Neue";
	
	private static Glow glow = new Glow(0.5);
	
	ObservableList<String> listOfFloatingTaskString;
	List<String> listOfStringToAddToday = new ArrayList<String>();
	List<ITask> floatingTaskList;
	

	@FXML
	private ListView<String> listOfFloatingTaskView;
	@FXML
	private Button buttonDone;

	public void getParentController(RootWindowController rootController) {
		this.rootContoller = rootController;
	}
	
	public void setUpView(List<ITask> floatingTasks) {
		floatingTaskList = floatingTasks;
		displayFloatingTaskList(floatingTaskList);
	}
	
	public void displayFloatingTaskList(List<ITask> floatingTasks) {
		List<String> listOfTaskTitle = new ArrayList<String>();
		for(int i = 0; i < floatingTasks.size(); i++) {
			listOfTaskTitle.add(floatingTasks.get(i).getTitle());
		}
		listOfFloatingTaskString = FXCollections.observableArrayList(listOfTaskTitle);
		listOfFloatingTaskView.setItems(listOfFloatingTaskString);
		listOfFloatingTaskView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new makeLabelWithBackGround(listOfStringToAddToday);
			}
		});
	}
	
	private static class makeLabelWithBackGround extends ListCell<String> {
		
		private List<String> listOfStringToAddToday;
		
		public makeLabelWithBackGround(List<String> listOfStringToAddToday) {
			this.listOfStringToAddToday = listOfStringToAddToday;
		}
		
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Label label = new Label(item);
            if (item != null) {
            	if(listOfStringToAddToday.contains(item)){
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
	
	@FXML
	private void initialize() {					
		buttonDone.setOnAction((event) -> {
			handleDoneAction();
		});
		
		listOfFloatingTaskView.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					
					handleDoneAction();
				} else if (event.getCode() == KeyCode.SPACE){
					
					// get the index of the selected cell
					String selectedString = listOfFloatingTaskView.getSelectionModel().getSelectedItem();
					
					// add and color the cell if it is not yet selected, and undo if it was
					if(listOfStringToAddToday.contains(selectedString)) {
						listOfStringToAddToday.remove(selectedString);
					} else {
						listOfStringToAddToday.add(selectedString);
					}
					updateCellColor();
				} else if (event.getCode() == KeyCode.TAB) {
					DropShadow dropShadow = new DropShadow(20,Color.WHITE);
					buttonDone.setEffect(dropShadow);
				} 
			}
		});
		
		buttonDone.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					System.out.println("Enter Pressed on button");
					handleDoneAction();
				} else if (event.getCode() == KeyCode.TAB){
					buttonDone.setEffect(null);
				} 
			}
		
		});
	}
	
	private void updateCellColor() {
		displayFloatingTaskList(floatingTaskList);
	}

	private void handleDoneAction() {
		
		String reply = "";
		
		for(int i = 0; i < listOfStringToAddToday.size(); i++) {
			StringBuilder inputStringBuilder = new StringBuilder();
			inputStringBuilder.append("edit " + (listOfStringToAddToday.get(i) + 1) + " -d today");
			reply = Controller.processUserInput(inputStringBuilder.toString());
		}
		if (reply.equals(MESSAGE_EDITED_SUCCESS)) {
			rootContoller.displayMessageToUser(MESSAGE_ADDED_SUCCESS);
			rootContoller.hideCongratulations();
		}
	}
}
