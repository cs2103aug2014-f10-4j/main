package list;

import java.util.List;

/**
 * 
 * 
 * @author andhieka, michael, shotaro
 */
interface IUserInterface {
    
    void displayTasks(List<ITask> tasks);
    
    void displayTaskDetail(ITask task);
    
    void displayCategories(List<ICategory> categories);
    
}
