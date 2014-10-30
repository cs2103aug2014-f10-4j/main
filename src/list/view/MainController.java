package list.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import list.ITask;
import list.IUserInterface;

public class MainController implements IUserInterface{

	@FXML
	private TextField console;
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private BorderPane taskDetailPane;
	
	@FXML
	private Label labelTask1;
	@FXML
	private Label labelTask2;
	@FXML
	private Label labelTask3;
	@FXML
	private Label labelTask4;
	@FXML
	private Label labelTask5;
	@FXML
	private Label labelTask6;
	@FXML
	private Label labelTask7;
	@FXML
	private Label labelTask8;
	@FXML
	private Label labelTask9;
	@FXML
	private Label labelTask10;
	
	@FXML
	private ImageView imageViewDate1;
	@FXML
	private ImageView imageViewDate2;
	@FXML
	private ImageView imageViewDate3;
	@FXML
	private ImageView imageViewDate4;
	@FXML
	private ImageView imageViewDate5;
	@FXML
	private ImageView imageViewDate6;
	@FXML
	private ImageView imageViewDate7;
	@FXML
	private ImageView imageViewDate8;
	@FXML
	private ImageView imageViewDate9;
	@FXML
	private ImageView imageViewDate10;
	
	private Label[] taskLabels = { 
		labelTask1, labelTask2, labelTask3, labelTask4, labelTask5, 
		labelTask6, labelTask7, labelTask8, labelTask9, labelTask10 
	};
	
	private ImageView[] dateImageViews = {
		imageViewDate1, imageViewDate2, imageViewDate3, imageViewDate4,
		imageViewDate5, imageViewDate6, imageViewDate7, imageViewDate8,
		imageViewDate9, imageViewDate10
	};
	
	// for task detail view*******From here*******
	@FXML
	private Label labelTaskTitle;
	@FXML
	private Label labelTaskCategory;
	@FXML
	private Label labelTaskDate;
	@FXML
	private Label labelTaskFrequency;
	@FXML
	private Label labelTaskPlace;
	@FXML
	private Label labelTaskNote;
	@FXML
	private Button buttonDone;
		
		// for task detail view*******To here*******
		
	/**
	 * Constructor
	 */
	public MainController() {
	}

	public void prepareData(List<ITask> tasks) {
		assert (tasks.size() == 10);
		for (int i = 0; i < tasks.size(); i++) {
			String taskTitle = tasks.get(i).getTitle();
			taskLabels[i].setText(taskTitle);
		
		}
		
		//TODO: Update ImageView
	}
	
	
	/**
	 * Initializes this controller class
	 */
	@FXML
	private void initialize() {
				
		console.setOnAction((event) -> {
			handleEnterAction();
		});
		
		buttonDone.setOnAction((event) -> {
			doneAction();
		});
	}
	
	/**
	 * This method is called when user presses 'enter' on keyboard
	 * after writing a command in the console.
	 */
	@FXML
	private void handleEnterAction() {
		String userInput = console.getText();
		//Controller.processUserInput(userInput);
		
		labelTask1.requestFocus(); //set focus to something else
		console.setText("");
		console.promptTextProperty();
	}
	
	@FXML
	private void doneAction() {
		hideAndShowDetailView(false);
	}

	@Override
	public void displayTaskDetail(ITask task) {
		labelTaskTitle.setText(task.getTitle());
		labelTaskCategory.setText(task.getCategory().getName());
		labelTaskDate.setText(task.getStartDate().getPrettyFormat() + "-" + task.getEndDate().getPrettyFormat());
		labelTaskFrequency.setText(task.getRepeatFrequency().name());
		labelTaskPlace.setText(task.getPlace());
		labelTaskNote.setText(task.getNotes());
		hideAndShowDetailView(true);
	}

	@Override
	public void display(String pageTitle, List<ITask> tasks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareForUserInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMessageToUser(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
	private void hideAndShowDetailView(boolean isVisible) {
		taskDetailPane.setVisible(isVisible);
	}
	
	
}
