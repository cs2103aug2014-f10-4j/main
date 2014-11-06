package list.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import list.model.ITask;

public class TaskOverviewController implements ListChangeListener<ITask> {
    private static final double LABEL_WIDTH = 550.0d;
    private static final double LABEL_HEIGHT = 37.5d;
    private static final double TIMELINE_WIDTH = 37.5d;
    
    private static final int MAX_NO_OF_TASKS = 8;
    
    @FXML
    Pane tasksContainer;
    @FXML
    private Label labelFeedback;
    
    
    private ObservableList<ITask> tasks;
    private Integer firstDisplayedTaskIndex = -1;
    private List<Label> displayedTaskLabels = new ArrayList<Label>();
    
    public void displayTasks(ObservableList<ITask> newTasks) {
        if (this.tasks != null) {
            this.tasks.removeListener(this);
        }
        this.tasks = newTasks;
        refresh();
        this.tasks.addListener(this);
    }

    private void refresh() {
        for (int i = 0; i < Math.min(MAX_NO_OF_TASKS, this.tasks.size()); i++) {
            displayTaskAtPosition(this.tasks.get(i), i);
        }
    }
    
    @Override
    public void onChanged(Change<? extends ITask> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                Integer addedIndex = change.getFrom();
                assert (change.getFrom() == change.getTo());
                if (addedIndex < firstDisplayedTaskIndex + MAX_NO_OF_TASKS) {
                    ITask task = tasks.get(addedIndex);
                    displayTaskAtPosition(task, addedIndex - firstDisplayedTaskIndex);
                }
            } else  if (change.wasRemoved()) {
                
            } else if (change.wasPermutated()) {
                
            }
        }
    }

    private void displayTaskAtPosition(ITask task, int positionIndex) {
        deleteLastLabelWhenFull();
        shiftDownLabelsBelow(positionIndex);
        Label label = new Label();
        label.setText(task.getTitle());
        label.setLayoutX(TIMELINE_WIDTH);
        label.setLayoutY(positionIndex * LABEL_HEIGHT);
        label.setPrefHeight(LABEL_HEIGHT);
        label.setPrefWidth(LABEL_WIDTH);
        label.setStyle("-fx-background-color: red;");
        label.setFont(Font.font("Helvetica", 18.0d));
        tasksContainer.getChildren().add(label);
    }

    private void deleteLastLabelWhenFull() {
        if (this.displayedTaskLabels.size() == MAX_NO_OF_TASKS) {
            Label lastLabel = this.displayedTaskLabels.get(MAX_NO_OF_TASKS - 1);
            tasksContainer.getChildren().remove(lastLabel);
        }
    }

    private void shiftDownLabelsBelow(int positionIndex) {
        for (int i = positionIndex; i < displayedTaskLabels.size(); i++) {
            Label label = displayedTaskLabels.get(i);
            Double currentY = label.getLayoutY();
            label.setLayoutY(currentY + LABEL_HEIGHT);
        }
    }

}
