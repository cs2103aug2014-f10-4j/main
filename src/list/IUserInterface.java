package list;

import java.util.List;

/**
 * 
 * 
 * @author andhieka, michael, shotaro
 */
interface IUserInterface { 
    
    void displayTaskDetail(ITask task);
    
    void display(String pageTitle, List<ITask> tasks);
        
    void clearDisplay();
    
}
