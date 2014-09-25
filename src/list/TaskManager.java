package list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a passive class that keeps track of Tasks.
 * Execution of commands is not handled by this class
 * but by the individual command types.
 * 
 * This class modifies the user interface by the methods
 * given in <code>IUserInterface</code>.
 * 
 * @author andhieka, michael
 */
class TaskManager {
    //TODO: keep the methods and variables static
    //TODO: please maintain mTasks sorted at all times
    private static List<ITask> mTasks = new ArrayList<ITask>();
    
    //METHODS FOR COMMANDS EXECUTION
    static void addTask(ITask task) {
        mTasks.add(task);
        Collections.sort(mTasks); //keep mTasks sorted
    }
    
    static ITask getTask(Integer taskId) {
        return mTasks.get(normaliseId(taskId));
    }
    
    static void deleteTask(Integer taskId) {
        mTasks.remove(normaliseId(taskId));
    }
    
    /**
     * Converts a 1-based id to 0-based id as represented in the array.
     * @param inputId 1-based index
     * @return 0-based index
     */
    static Integer normaliseId(Integer inputId) {
        return inputId - 1;
    }
    
    //METHODS FOR UPDATING GUI
    static void displayTenTasks(int startIndex, IUserInterface ui) {
        int i = normaliseId(startIndex);
        List<ITask> tasks = mTasks.subList(i, i + 10);
        ui.displayTasks(tasks);
    }
    
    static void displayTaskDetail(int taskId, IUserInterface ui) {
        ITask task = mTasks.get(normaliseId(taskId));
        ui.displayTaskDetail(task);
    }
    
}
