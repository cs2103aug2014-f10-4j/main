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
    
    void prepareForUserInput(); //TODO: Remove this method
    
    void displayMessageToUser(String message);
}
