package list;

import java.util.List;

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
    
    void back();
    
    void next();
}
