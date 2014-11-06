package list.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import list.model.ITask;

public class TaskOverviewController {
    private static final double LABEL_WIDTH = 550.0d;
    private static final double LABEL_HEIGHT = 37.5d;
    private static final double TIMELINE_WIDTH = 37.5d;
    
    private static final int MAX_NO_OF_TASKS = 8;
    
    @FXML
    Pane tasksContainer;
 
    private List<ITask> allTasks;
    private Integer beginIndex = 0;
    private Map<ITask, Label> taskLabels = new HashMap<ITask, Label>();
    private List<ITask> oldDisplayedTasks;
    
    public void displayTasks(List<ITask> newTasks) {
        this.allTasks = newTasks;
        refresh();
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
            animateMoveLabel(label, positionIndex);
        } else { //if task is not displayed yet
            label = createTaskLabel(task, positionIndex);
            taskLabels.put(task, label);
            tasksContainer.getChildren().add(label);
        }
    }

    private void animateMoveLabel(Label label, int positionIndex) {
        label.setLayoutY(positionIndex * LABEL_HEIGHT);
    }

    private Label createTaskLabel(ITask task, int positionIndex) {
        Label label = new Label();
        label.setText(task.getTitle());
        label.setLayoutX(TIMELINE_WIDTH);
        label.setLayoutY(positionIndex * LABEL_HEIGHT);
        label.setPrefHeight(LABEL_HEIGHT);
        label.setPrefWidth(LABEL_WIDTH);
        label.setStyle("-fx-background-color: red;");
        label.setFont(Font.font("Helvetica", 18.0d));
        return label;
    }
    
    private void undisplayTask(ITask task) {
        if (taskLabels.containsKey(task)) {
            Label label = taskLabels.get(task);
            tasksContainer.getChildren().remove(label);
            taskLabels.remove(task);
        }
    }
    
}
