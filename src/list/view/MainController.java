package list.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import list.Controller;
import list.ITask;
import list.IUserInterface;

public class MainController implements IUserInterface {

	private static final int MAX_NUMBER_OF_TASKS_PER_PAGE = 10;
	private static final int MIN_PAGE_NUMBER = 1;
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
	
	@FXML
	private Label labelAllTasks;
	@FXML
	private Label labelFloatingTasks;
	@FXML
	private Label labelCategory1;
	@FXML
	private Label labelCategory2;
	@FXML
	private Label labelCategory3;
	@FXML
	private Label labelCategory4;
	@FXML
	private Label labelCategory5;
	@FXML
	private Label labelCategory6;
	@FXML
	private Label labelCategory7;
	@FXML
	private Label labelCategory8;
	
	private Label[] taskLabels = new Label[10];
	private ImageView[] dateImageViews = new ImageView[10];
	
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
	
	private static MainController singletonInstance = null;
		
		// for task detail view*******To here*******
		
	/**
	 * Constructor
	 */
	public MainController() {
		singletonInstance = this;
	}
	
	public static MainController getInstance() {
	    return singletonInstance;
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
		
		setUpTaskLabels();
	}
	
	private void setUpTaskLabels() {
		taskLabels[0] = labelTask1;
		taskLabels[1] = labelTask2;
		taskLabels[2] = labelTask3;
		taskLabels[3] = labelTask4;
		taskLabels[4] = labelTask5;
		taskLabels[5] = labelTask6;
		taskLabels[6] = labelTask7;
		taskLabels[7] = labelTask8;
		taskLabels[8] = labelTask9;
		taskLabels[9] = labelTask10;
	}
	
	/**
	 * This method is called when user presses 'enter' on keyboard
	 * after writing a command in the console.
	 */
	@FXML
	private void handleEnterAction() {
		String userInput = console.getText();
		String reply = Controller.processUserInput(userInput);
		displayMessageToUser(reply);
		
		//labelTask1.requestFocus(); //set focus to something else
		console.setText("");
		//console.promptTextProperty();
	}
	
	@FXML
	private void doneAction() {
		hideAndShowDetailView(false);
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
		
		for (Label label: taskLabels) {
		    label.setText("");
		}
		
		for (int i = startIndex; i < startIndex + MAX_NUMBER_OF_TASKS_PER_PAGE; i++) {
			if (i < tasksList.size()) {
				String taskTitle = tasksList.get(i).getTitle();
				taskLabels[index].setText((i + 1) + ". " + taskTitle);
			} else {
				break;
			}
			
			index++;
		}
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
		prepareData(tasks);
		displayTasksFrom(1);
	}

	@Override
	public void clearDisplay() {
		for (Label label: taskLabels) {
		    label.setText("");
		}
	}

	@Override
	public void displayMessageToUser(String message) {
		System.out.println(message);
	}

	@Override
	public boolean back() {
		if (currentPageDisplayed == MIN_PAGE_NUMBER ) {
			return false;
		} else {
			displayTasksFrom(currentPageDisplayed - 1);
			return true;
		}
	}

	@Override
	public boolean next() {
		if (currentPageDisplayed + 1 > totalPages) {
			return false;
		} else {
			displayTasksFrom(currentPageDisplayed  + 1); 
			return true;
		}
	}
	
	private void hideAndShowDetailView(boolean isVisible) {
		taskDetailPane.setVisible(isVisible);
	}
	
	
}
