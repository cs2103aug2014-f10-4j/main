package list.view;

import java.util.List;

import javafx.collections.ObservableList;
import list.model.ICategory;
import list.model.ITask;

/**
 * 
 * 
 * @author andhieka, michael, shotaro
 */
public interface IUserInterface { 
    
    void displayTaskDetail(ITask task);
    
    void display(String pageTitle, List<ITask> tasks);
        
    void clearDisplay();
        
    void displayMessageToUser(String message);
    
    void updateCategory(List<ICategory> categories);
    
    void refresh();
    
    boolean back();
    
    boolean next();
}
