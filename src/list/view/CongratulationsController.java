package list.view;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
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

public class CongratulationsController {
	
	
	private RootWindowController rootContoller;

	@FXML
	private ListView<String> listOfFloatingTaskView;
	@FXML
	private Button buttonDone;

	public void getParentController(RootWindowController rootController) {
		this.rootContoller = rootController;
	}
	
	public void displayFloatingTaskList() {	
		
	}
	
	public void setUpView(List<ITask> tasks) {
		
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
	}

	private void handleDoneAction() {
		rootContoller.hideCongratulations(); 
	}
}
