package list.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import list.Controller;
import list.model.Date;
import list.model.ITask;
import list.model.ITask.TaskStatus;

public class TaskDetailController {
	
	private RootController rootContoller;
	private int taskNumber = 0;
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField taskTitle;
	@FXML
	private TextField taskCategory;
	@FXML
	private TextField taskStartDate;
	@FXML
	private TextField taskEndDate;
	@FXML
	private TextField taskRepeatFrequency;
	@FXML
	private TextField taskPlace;
	@FXML
	private TextField taskNotes;
	@FXML
	private CheckBox taskStatus;
	@FXML
	private Button buttonDone;

	public void getParentController(RootController rootController) {
		this.rootContoller = rootController;
	}
	
	public void displayTaskDetail(ITask task, int taskNumber) {	
		this.taskNumber = taskNumber;
		
		taskTitle.requestFocus();
		
		taskTitle.setText(task.getTitle());
		
		if (task.getCategory().getName().isEmpty()) {
			taskCategory.setText("");
		} else {
			taskCategory.setText(task.getCategory().getName());
		}
		
		if (task.getStartDate().equals(Date.getFloatingDate())) {
			taskStartDate.setText("");
		} else {
			taskStartDate.setText(task.getStartDate().getPrettyFormat());
		}
		
		if (task.getEndDate().equals(Date.getFloatingDate())) {
			taskEndDate.setText("");
		} else {
			taskEndDate.setText(task.getEndDate().getPrettyFormat());
		}
		
		taskRepeatFrequency.setText(task.getRepeatFrequency().name());
		
		if (task.getPlace().isEmpty()) {
			taskPlace.setText("");
		} else {
			taskPlace.setText(task.getPlace());
		}
		
		if (task.getNotes().isEmpty()) {
			taskNotes.setText("");
		} else {
			taskNotes.setText(task.getNotes());
		}
		
		if (task.getStatus() == TaskStatus.DONE) {
			taskStatus.setSelected(true);
		} else {
			taskStatus.setSelected(false);
		}
	}
	
	@FXML
	private void initialize() {					
		buttonDone.setOnAction((event) -> {
			handleDoneAction();
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
		
		taskNotes.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB && !event.isShiftDown()) {
					DropShadow dropShadow = new DropShadow(20,Color.WHITE);
					buttonDone.setEffect(dropShadow);
					System.out.println("TAB Pressed");
					buttonDone.requestFocus();
				}
			}
		});
	}
	
	private void handleDoneAction() {
		StringBuilder inputStringBuilder = new StringBuilder();
		inputStringBuilder.append("edit " + this.taskNumber + " ");
		inputStringBuilder.append("-t " + taskTitle.getText() + " ");
		inputStringBuilder.append("-c " + taskCategory.getText() + " ");
		inputStringBuilder.append("-s " + taskStartDate.getText() + " ");
		inputStringBuilder.append("-d " + taskEndDate.getText() + " ");
		inputStringBuilder.append("-p " + taskPlace.getText() + " ");
		inputStringBuilder.append("-r " + taskRepeatFrequency.getText() + " ");
		inputStringBuilder.append("-n " + taskNotes.getText());
		
		Controller.processUserInput(inputStringBuilder.toString());
		
		rootContoller.hideTaskDetail(anchorPane);
	}

	
}
