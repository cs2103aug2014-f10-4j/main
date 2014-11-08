package list.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;
import list.model.Date;
import list.model.ITask;

public class TaskOverviewController {
    private static final FadeTransition FEEDBACK_FADE_IN;
    private static final FadeTransition FEEDBACK_FADE_OUT;
    private static final PauseTransition FEEDBACK_PAUSE;
    static {
        FEEDBACK_FADE_IN = new FadeTransition(Duration.seconds(1));
        FEEDBACK_FADE_IN.setFromValue(0);
        FEEDBACK_FADE_IN.setToValue(0.7);
        FEEDBACK_PAUSE = new PauseTransition(Duration.seconds(2));
        FEEDBACK_FADE_OUT = new FadeTransition(Duration.seconds(2));
        FEEDBACK_FADE_OUT.setFromValue(0.7);
        FEEDBACK_FADE_OUT.setToValue(0);
    }
    
    private static final double TASK_LABEL_TRANSLATE_X = 42.5d;
    private static final double TASK_LABEL_WIDTH = 550.0d;
    private static final double TASK_LABEL_HEIGHT = 37.5d;
    private static final double TASK_LABEL_ANIMATION_DURATION = 0.5d;
    private static final double TASK_LABEL_OPACITY = 1.0;
    private static final double TASK_LABEL_OPACITY_ZERO = 0.0;
    
    private static final double TIMELINE_WIDTH = 37.5d;
    
    private static final int MAX_NO_OF_TASKS = 8;
    
    @FXML
    Pane tasksContainer;
    @FXML
    private Label labelFeedback;
    private Animation feedbackAnimation;
   
    private List<ITask> allTasks;
    private Integer beginIndex = 0;
    private Map<ITask, Label> taskLabels = new HashMap<ITask, Label>();
    private List<ImageView> timelineImageViews = new ArrayList<ImageView>();
    private List<Label> timelineMonthLabel = new ArrayList<Label>();
    private List<Label> timelineDateLabel = new ArrayList<Label>();
    private List<ITask> oldDisplayedTasks;
    
    public void displayTasks(List<ITask> newTasks) {
        this.allTasks = newTasks;
        beginIndex = 0;
        //refresh();
    }
    
    /**
     * Gets the task number in the list currently displayed.
     * 
     * @param task
     * @return an index of a task in the list currently displayed. It returns 0
     * if the task specified cannot be found in the list
     */
    public int getTaskNumber(ITask task) {
    	for (int i = 1; i <= allTasks.size(); i++) {
    		if (task.equals(allTasks.get(i - 1))) {
    			return i;
    		}
    	}
    	
    	return 0;
    }

    boolean next() {
		if (this.beginIndex + MAX_NO_OF_TASKS < allTasks.size()) {
			this.beginIndex += MAX_NO_OF_TASKS;
			refresh();
			return true;
		} else {
			return false;
		}
	}

	boolean back() {
		if (this.beginIndex - MAX_NO_OF_TASKS >= 0) {
			this.beginIndex -= MAX_NO_OF_TASKS;
			refresh();
			return true;
		} else {
			return false;
		}
	}
    
    void refresh() {
        List<ITask> newDisplayedTasks = getDisplayTasks();
        for (int newPositionIndex = 0; newPositionIndex < newDisplayedTasks.size(); newPositionIndex++) {
            ITask task = newDisplayedTasks.get(newPositionIndex);
            displayTaskAtPosition(task, newPositionIndex);
        }
        if (oldDisplayedTasks != null) {
            for (ITask oldTask: oldDisplayedTasks) {
                if (!newDisplayedTasks.contains(oldTask)) {
                    undisplayTask(oldTask);
                }
            }
        }
        oldDisplayedTasks = newDisplayedTasks;
        
        refreshTimeline();
    }
    
    private List<ITask> getDisplayTasks() {
        int stopIndex = Math.min(allTasks.size(), beginIndex + MAX_NO_OF_TASKS);
        ArrayList<ITask> result = new ArrayList<ITask>();
        for (int i = beginIndex; i < stopIndex; i++) {
            result.add(allTasks.get(i));
        }
        return result;
    }

    private void displayTaskAtPosition(ITask task, int positionIndex) {
        Label label;
        if (taskLabels.containsKey(task)) { //if task is already displayed now
            label = taskLabels.get(task);
            updateTaskLabelText(label, beginIndex + 1 + positionIndex, 
            					task.getTitle());
            animateMoveLabel(label, positionIndex);
        } else { //if task is not displayed yet
            label = createTaskLabel(task, positionIndex);
            taskLabels.put(task, label); //memorize so labels can be erased
            animateShowLabel(label);
        }
    }

    private void animateShowLabel(Label label) {
        label.setOpacity(0);
        tasksContainer.getChildren().add(label);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(TASK_LABEL_ANIMATION_DURATION), label);
        fadeIn.setFromValue(TASK_LABEL_OPACITY_ZERO);
        fadeIn.setToValue(TASK_LABEL_OPACITY);
        fadeIn.play();
    }

    /**
     * Contract: Set the Y position of the label to positionIndex * LABEL_HEIGHT
     * @param label The label to be moved
     * @param positionIndex 
     */
    private void animateMoveLabel(Label label, int positionIndex) {
        //label.setLayoutY(positionIndex * LABEL_HEIGHT);
        
        double targetPositionY = positionIndex * TASK_LABEL_HEIGHT;
        
        TranslateTransition translation = new TranslateTransition(Duration.seconds(TASK_LABEL_ANIMATION_DURATION), label);
        translation.setFromY(label.getTranslateY());
        translation.setToY(targetPositionY);
        translation.play();
    }

    private void updateTaskLabelText(Label label, int index, String title) {
    	label.setText(index + ". "  + title);
    }
    
    private Label createTaskLabel(ITask task, int positionIndex) {
        Label label = new Label();
        updateTaskLabelText(label, beginIndex + 1 + positionIndex, task.getTitle());
        label.setLayoutX(0);
        label.setLayoutY(0);
        label.setTranslateX(TASK_LABEL_TRANSLATE_X);
        label.setTranslateY(positionIndex * TASK_LABEL_HEIGHT); 
        label.setPrefHeight(TASK_LABEL_HEIGHT);
        label.setPrefWidth(TASK_LABEL_WIDTH);
        //label.setStyle("-fx-background-color: red;");
        label.setFont(Font.font("Helvetica", 18.0d));
        return label;
    }
    
    private void undisplayTask(ITask task) {
        if (taskLabels.containsKey(task)) {
            Label label = taskLabels.get(task);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(TASK_LABEL_ANIMATION_DURATION * 0.5), label);
            fadeOut.setFromValue(TASK_LABEL_OPACITY);
            fadeOut.setToValue(TASK_LABEL_OPACITY_ZERO);
            fadeOut.play();
            fadeOut.setOnFinished((ActionEvent event) -> {
                tasksContainer.getChildren().remove(label); 
            });
            taskLabels.remove(task);
        }
    }
    
    //ASSUME WE ARE DISPLAYING CURRENT TASKS, all tasks have deadline
    private void refreshTimeline() {
    	clearTimeline();
    	List<ITask> newDisplayedTask = getDisplayTasks();
    	Date prevDate = Date.getFloatingDate();
    	for (int i = 0; i < newDisplayedTask.size(); i++) {
    		Date curDate = newDisplayedTask.get(i).getTimelineDate();
    		if(prevDate.equals(curDate)) {
    			displayLineAtPosition(i);
    		} else {
    			displayDateAtPosition(curDate, i);
    		}
    		prevDate = curDate;
    	}
    }
    
    private void clearTimeline() {
    	for (int i = 0; i < timelineImageViews.size(); i++) {
    		tasksContainer.getChildren().remove(timelineImageViews.get(i));
    	}
    	
    	for (int i = 0; i < timelineDateLabel.size(); i++) {
    		tasksContainer.getChildren().remove(timelineDateLabel.get(i));
    	}
    	
    	for (int i = 0; i < timelineMonthLabel.size(); i++) {
    		tasksContainer.getChildren().remove(timelineMonthLabel.get(i));
    	}
    }
    
    private void displayDateAtPosition(Date date, int positionIndex) {
    	Label labelDay = new Label();
    	Label labelMonth = new Label();
    	Label labelYear = new Label();
    	
    	ImageView imageView = new ImageView();
    	Image calendar = new Image("list/view/icon_calender.png");
    	imageView.setImage(calendar);
    	imageView.setFitHeight(TASK_LABEL_HEIGHT);
    	imageView.setFitWidth(TIMELINE_WIDTH);
    	imageView.setLayoutY(positionIndex * TASK_LABEL_HEIGHT);
    	timelineImageViews.add(imageView);
    	
    	labelDay.setPrefWidth(TIMELINE_WIDTH);
    	labelDay.setPrefHeight(TASK_LABEL_HEIGHT / 2);
    	labelDay.setLayoutY(positionIndex * TASK_LABEL_HEIGHT + TASK_LABEL_HEIGHT * 0.25);
    	labelDay.setText(date.getDay() + "");
    	labelDay.setStyle("-fx-font-size:12pt;-fx-font-weight:bold");
    	labelDay.setAlignment(Pos.CENTER);
    	timelineDateLabel.add(labelDay);
    	
    	labelMonth.setPrefWidth(TIMELINE_WIDTH);
    	labelMonth.setPrefHeight(TASK_LABEL_HEIGHT / 5);
    	labelMonth.setLayoutY(positionIndex * TASK_LABEL_HEIGHT);
    	labelMonth.setText(date.getMonthName().toUpperCase());
    	labelMonth.setStyle("-fx-font-size:7pt;-fx-text-fill:white;-fx-font-weight:bold");
    	labelMonth.setAlignment(Pos.CENTER);
    	timelineMonthLabel.add(labelMonth);
    	
    	
//    	labelYear.setPrefWidth(TIMELINE_WIDTH);
//    	labelYear.setPrefHeight(LABEL_HEIGHT / 3);
//    	labelYear.setLayoutY(positionIndex * LABEL_HEIGHT + LABEL_HEIGHT * 2 / 3);
//    	labelYear.setText(date.getYear() + "");
    	
    	tasksContainer.getChildren().add(imageView);
    	tasksContainer.getChildren().add(labelDay);
    	tasksContainer.getChildren().add(labelMonth);
//    	tasksContainer.getChildren().add(labelYear);
    	
    	
    }
    
    private void displayLineAtPosition(int positionIndex) {
    	ImageView imageView = new ImageView();
    	Image line = new Image("list/view/icon_bar.png");
    	imageView.setImage(line);
    	imageView.setFitHeight(TASK_LABEL_HEIGHT);
    	imageView.setFitWidth(TIMELINE_WIDTH);
    	imageView.setLayoutY(positionIndex * TASK_LABEL_HEIGHT);
    	timelineImageViews.add(imageView);
    	
    	tasksContainer.getChildren().add(imageView);
    }

    public void displayMessageToUser(String message) {
        if (this.feedbackAnimation != null) {
            this.feedbackAnimation.stop();
        }
        labelFeedback.setText(message);
        SequentialTransition animation = new SequentialTransition(labelFeedback, FEEDBACK_FADE_IN, FEEDBACK_PAUSE, FEEDBACK_FADE_OUT);
        animation.play();
        this.feedbackAnimation = animation;
    }

	public void highlightTask(ITask task) {
		goToPageContaininingTask(task);
		refresh();
		Label label = taskLabels.get(task);
		//label.setStyle("-fx-background-color: yellow;");
		//wait a few seconds
		//label.setStyle("-fx-background-color: none;");
	}

	private void goToPageContaininingTask(ITask task) {
		int taskNumber = getTaskNumber(task);
		beginIndex = taskNumber / MAX_NO_OF_TASKS;
	}

	
}
