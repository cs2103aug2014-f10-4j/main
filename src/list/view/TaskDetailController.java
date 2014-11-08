package list.view;

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
	
	private static final String MESSAGE_EDIT_SUCCESS = "Task is successfully edited";
	
	private RootWindowController rootContoller;
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
//	@FXML
//	private TextField taskRepeatFrequency;
	@FXML
	private TextField taskPlace;
	@FXML
	private TextField taskNotes;
	@FXML
	private CheckBox taskStatus;
	@FXML
	private Button buttonDone;

	public void getParentController(RootWindowController rootController) {
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
		
		//taskRepeatFrequency.setText(task.getRepeatFrequency().name());
		
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
		
		anchorPane.setOnKeyPressed((event) -> {
			handlePaneKeyEvent(event);
		});
		
		buttonDone.setOnKeyPressed((event) -> {
			handleDoneKeyEvent(event);
		});
		
		taskNotes.setOnKeyPressed((event) -> {
			handleTaskNotesKeyEvent(event);
		});

		taskStatus.setOnKeyPressed((event) -> {
			handleTaskStatusKeyEvent(event);
		});
	}
	
	private void handleTaskStatusKeyEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB && event.isShiftDown()) {
			taskNotes.requestFocus();
		} else if (event.getCode() == KeyCode.TAB) {
			animateDoneButton();
		} else if (event.getCode() == KeyCode.SPACE) {
			toggleTaskStatus();
			event.consume();
		}
	}

	

	private void handleTaskNotesKeyEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			taskStatus.requestFocus();
		}
	}
	
	private void handlePaneKeyEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			rootContoller.hideTaskDetail();
		}
		
		event.consume();
	}
	
	private void handleDoneKeyEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			handleDoneAction();
		} else if (event.getCode() == KeyCode.TAB) {
			buttonDone.setEffect(null);
			taskTitle.requestFocus();
		}
	}

	private void handleDoneAction() {
		StringBuilder inputStringBuilder = new StringBuilder();
		inputStringBuilder.append("edit " + this.taskNumber + " ");
		
		if (!taskTitle.getText().trim().isEmpty()) {
			inputStringBuilder.append("-t " + taskTitle.getText() + " ");
		}
		
		if (!taskCategory.getText().trim().isEmpty()) {
			inputStringBuilder.append("-c " + taskCategory.getText() + " ");
		}
		
		if (!taskStartDate.getText().trim().isEmpty()) {
			inputStringBuilder.append("-s " + taskStartDate.getText() + " ");
		}
		
		if (!taskEndDate.getText().trim().isEmpty()) {
			inputStringBuilder.append("-d " + taskEndDate.getText() + " ");
		}
		
		if (!taskPlace.getText().trim().isEmpty()) {
			inputStringBuilder.append("-p " + taskPlace.getText() + " ");
		}
		
//		if (!taskRepeatFrequency.getText().trim().isEmpty()) {
//			inputStringBuilder.append("-r " + taskRepeatFrequency.getText() + " ");
//		}
		
		if (!taskNotes.getText().trim().isEmpty()) {
			inputStringBuilder.append("-n " + taskNotes.getText());
		}
		
		inputStringBuilder.append("-f " + taskStatus.isSelected());
		
		String reply = Controller.processUserInput(inputStringBuilder.toString());
		rootContoller.displayMessageToUser(reply);
		
		if (reply.equals(MESSAGE_EDIT_SUCCESS)) {
			rootContoller.hideTaskDetail();
		} 
	}

	private void animateDoneButton() {
		DropShadow dropShadow = new DropShadow(20,Color.WHITE);
		buttonDone.setEffect(dropShadow);
		buttonDone.requestFocus();
	}
	
	private void toggleTaskStatus() {
		if (taskStatus.isSelected()) {
			taskStatus.setSelected(false);
		} else {
			taskStatus.setSelected(true);
		}
	}
	
}
