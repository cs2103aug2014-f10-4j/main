package list.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import list.Controller;

public class MainController {

	@FXML
	private TextField console;
	
	@FXML
	private GridPane gridPane;
	
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
		Controller.processUserInput(userInput);
		
		labelTask1.requestFocus(); //set focus to something else
		console.setText("");
		console.promptTextProperty();
	}
	
	
}
