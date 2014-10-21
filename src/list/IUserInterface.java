package list;

import java.util.List;

/**
 * 
 * 
 * @author andhieka, michael, shotaro
 */
interface IUserInterface {
    
    void displayNewTaskOrDate(ITask task);
    
    void displayTaskDetail(ITask task);
    
    void displayCategories(List<ICategory> categories);
    
    void displayMessageToUser(String message);
    
    void prepareForUserInput();
    
    boolean isFull();

    void clearAll();
    
    void clearDisplay();
    
}
