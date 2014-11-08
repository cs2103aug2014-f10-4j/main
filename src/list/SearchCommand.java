package list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import list.model.ITask;

public class SearchCommand implements ICommand {
    private static final String MESSAGE_DISPLAYING_SEARCH_RESULTS = "Displaying search results for \"%s\"";
    private String keyword;
    TaskManager taskManager = TaskManager.getInstance();
    List<ITask> allTasks;
    
    public SearchCommand() {

    }
    
    public SearchCommand(String keyword) {
        this.keyword = keyword;
    }
    
    public SearchCommand setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        allTasks = taskManager.getAllTasks();
        ArrayList<ITask> results = new ArrayList<ITask>();
        for (ITask task: allTasks) {
            if (matchKeyword(task.getTitle()) ||
                matchKeyword(task.getPlace()) ||
                matchKeyword(task.getNotes()) ||
                matchKeyword(task.getCategory().getName()) ||
                matchKeyword(task.getStatus().toString())) {        
                results.add(task);
            }
        }
        Collections.sort(results);
        
        Controller.displayTasks(keyword.toLowerCase(), results);
        Controller.refreshUI();
        return String.format(MESSAGE_DISPLAYING_SEARCH_RESULTS, keyword.toLowerCase());
    }
    
    private boolean matchKeyword(String str) {
        if (str == null) {
            return false;
        } else {
            return str.toLowerCase().contains(keyword.toLowerCase());
        }
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
