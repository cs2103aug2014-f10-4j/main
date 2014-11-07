package list.view;

import java.util.List;

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
    
    void displayCategories(List<ICategory> categories);
    
    void hideCategories();
    
    boolean back();
    
    boolean next();

	void hideTaskDetail();
	
	void highlightTask(ITask task);
	
	void refreshUI();
}
