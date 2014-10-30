package list.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import list.ITask;
import list.IUserInterface;

public class MainController implements IUserInterface{

	private static final int MAX_NUMBER_OF_TASKS_PER_PAGE = 10;
	private static final int MIN_NUMBER_OF_PAGE = 0;
	private int totalPages = 0;
	private int currentPageDisplayed = 0;
	private List<ITask> tasksList = null;
	
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
		
	/**
	 * Constructor
	 */
	public MainController() {
	}
	
	/**
	 * Initializes this controller class
	 */
	@FXML
	private void initialize() {
				
		console.setOnAction((event) -> {
			handleEnterAction();
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

	private void prepareData(List<ITask> tasks) {
		this.tasksList = tasks;
		this.totalPages = calculateNumberOfPages(tasks.size());
	}
	
	private int calculateNumberOfPages(int taskSize) {
		if (taskSize % MAX_NUMBER_OF_TASKS_PER_PAGE == 0) {
			return taskSize / MAX_NUMBER_OF_TASKS_PER_PAGE;
		} else {
			return (taskSize / MAX_NUMBER_OF_TASKS_PER_PAGE) + 1;
		}
	}
	
	private void displayTasksFrom(int pageNumber) {
		assert (pageNumber > 0 && pageNumber <= totalPages);
		this.currentPageDisplayed = pageNumber;
		int startIndex = (pageNumber - 1) * MAX_NUMBER_OF_TASKS_PER_PAGE; 
		int index = 0;
		for (int i = startIndex; i < startIndex + MAX_NUMBER_OF_TASKS_PER_PAGE; i++) {
			if (i < totalPages) {
				String taskTitle = tasksList.get(i).getTitle();
				taskLabels[index].setText(i + ". " + taskTitle);
			} 
			
			index++;
		}
	}
	
	@Override
	public void displayTaskDetail(ITask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String pageTitle, List<ITask> tasks) {
		prepareData(tasks);
		displayTasksFrom(1);
	}

	@Override
	public void clearDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMessageToUser(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void back() {
		if (currentPageDisplayed - 1 < MIN_NUMBER_OF_PAGE) {
			//Do nothing or tell user?
		} else {
			displayTasksFrom(currentPageDisplayed - 1);
		}
	}

	@Override
	public void next() {
		if (currentPageDisplayed + 1 > totalPages) {
			//Do nothing or tell user?
		} else {
			displayTasksFrom(currentPageDisplayed  + 1); 
		}
	}
	
	
	
	
	
	
}
