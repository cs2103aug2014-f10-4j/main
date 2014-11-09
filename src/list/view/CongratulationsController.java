package list.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import list.Controller;
import list.model.ITask;

public class CongratulationsController {
	
	
	private RootWindowController rootContoller;
	
	private static final String MESSAGE_EDITED_SUCCESS = "Task is successfully edited";
	private static final String MESSAGE_ADDED_SUCCESS = "Task is successfully added";
	
	ObservableList<String> listOfFloatingTaskString;
	List<Integer> listOfIndexToAddToday = new ArrayList<Integer>();

	@FXML
	private ListView<String> listOfFloatingTaskView;
	@FXML
	private Button buttonDone;

	public void getParentController(RootWindowController rootController) {
		this.rootContoller = rootController;
	}
	
	public void setUpView(List<ITask> floatingTasks) {
		displayFloatingTaskList(floatingTasks);
	}
	
	public void displayFloatingTaskList(List<ITask> floatingTasks) {
		List<String> listOfTaskTitle = new ArrayList<String>();
		for(int i = 0; i < floatingTasks.size(); i++) {
			listOfTaskTitle.add(floatingTasks.get(i).getTitle());
		}
		listOfFloatingTaskString = FXCollections.observableArrayList(listOfTaskTitle);
		listOfFloatingTaskView.setItems(listOfFloatingTaskString);
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
					int selectedIndex = listOfFloatingTaskView.getFocusModel().getFocusedIndex();
					
					// add and color the cell if it is not yet selected, and undo if it was
					if(listOfIndexToAddToday.contains(selectedIndex)) {
						listOfIndexToAddToday.remove(selectedIndex);
						uncolorCellAtIndex(selectedIndex);
					} else {
						listOfIndexToAddToday.add(selectedIndex);
						colorCellAtIndex(selectedIndex);
					}
				} else if (event.getCode() == KeyCode.TAB) {
					listOfFloatingTaskView.setEffect(null);
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
	
	private void colorCellAtIndex(int indexOfCell) {
		
	}
	
	private void uncolorCellAtIndex(int indexOfCell) {
		
	}

	private void handleDoneAction() {
		
		String reply = "";
		
		for(int i = 0; i < listOfIndexToAddToday.size(); i++) {
			StringBuilder inputStringBuilder = new StringBuilder();
			inputStringBuilder.append("edit " + (listOfIndexToAddToday.get(i) + 1) + " -d today");
			reply = Controller.processUserInput(inputStringBuilder.toString());
		}
		if (reply.equals(MESSAGE_EDITED_SUCCESS)) {
			rootContoller.displayMessageToUser(MESSAGE_ADDED_SUCCESS);
			rootContoller.hideCongratulations();
		}
	}
}
