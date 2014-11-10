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
            
    /**
     * Sets the page title and list of tasks to display on the next refresh.
     * Warning: change will not be reflected until <code>refreshUI</code> is called.
     * @param pageTitle
     * @param tasks
     */
    void setDisplayItems(String pageTitle, List<ITask> tasks);
        
    void displayMessageToUser(String message);
    //@author A0126722L
    void displayCategories(List<ICategory> categories);
    
    void hideCategories();

    void displayHelp();
    
    void hideHelp();

    boolean back();
    
    boolean next();
    //@author A0126722L
	void hideTaskDetail();
	
	void highlightTask(ITask task);
	
	void refreshUI();
	//@author A0126722L
	void displayCongratulations(List<ITask> floatingTasks);
	
	void hideCongratulations();
	//@author A0126722L
	void clearConsole();

}
