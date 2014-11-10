package list.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HelpController {
	
	private static final double VERTICAL_SCROLL_AMOUNT = 0.025;

	private RootWindowController rootContoller;
	
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Button buttonDone;
	@FXML
	private ImageView imageHelp;
	
	void setParentController(RootWindowController rootController) {
		this.rootContoller = rootController;
	}
	
	
	@FXML
	private void initialize() {					
		setupButtonDoneAction();
		scrollPane.setOnKeyPressed((event) -> {
			handleScrollPaneKeyPress(event);
		}); 
		
		cacheImageViewForPerformance();
	}

	private void handleScrollPaneKeyPress(KeyEvent event) {
		double currentPosition = scrollPane.getVvalue();
		double vmin = scrollPane.getVmin();
		double vmax = scrollPane.getVmax();
		if (event.getCode() == KeyCode.DOWN) {
			scrollPane.setVvalue(Math.min(vmax, currentPosition + VERTICAL_SCROLL_AMOUNT));
		} else if (event.getCode() == KeyCode.UP) {
			scrollPane.setVvalue(Math.max(vmin, currentPosition - VERTICAL_SCROLL_AMOUNT));
		} else if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
			handleDoneAction();
		}
		event.consume(); //to prevent further propagation
	}

	private void cacheImageViewForPerformance() {
	    imageHelp.setCache(true);
		imageHelp.setSmooth(true);
    }

	private void setupButtonDoneAction() {
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
		rootContoller.hideHelp();
	}	
}
