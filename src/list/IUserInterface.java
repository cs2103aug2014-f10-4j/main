package list;

import java.util.List;

/**
 * 
 * 
 * @author andhieka, michael, shotaro
 */
interface IUserInterface {
    class DisplayFullException extends Exception { };
    
    
    void displayNewTaskOrDate(ITask task) throws DisplayFullException;
    
    void displayTaskDetail(ITask task);
    
    void displayCategories(List<ICategory> categories);
    
    boolean isFull();
    
    void clearDisplay();
    
}
