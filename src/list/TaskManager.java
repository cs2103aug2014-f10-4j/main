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
    }
    
    static ITask getTask(Integer taskNumberShownOnScreen) {
        return mTasks.get(getTaskId(taskNumberShownOnScreen));
    }
    
    static void deleteTask(Integer taskNumberShownOnScreen) {
        mTasks.remove(getTaskId(taskNumberShownOnScreen));
    }
    
    static void clearTasks() {
    	mTasks.clear();
    }
    
    static void sortTasks() {
    	Collections.sort(mTasks);
	}
    
    static int getNumberOfTasks() {
    	return mTasks.size();
    }
    
    static boolean hasTask(ITask task) {
    	return mTasks.contains(task);
    }
    
    static boolean isValidTaskNumber(Integer taskNumber) {
		return (taskNumber >= 1) && (taskNumber <= TaskManager.getNumberOfTasks());
	}
    
    static boolean isListOfTasksSorted() {
    	if (mTasks.size() <= 1) {
    		return true;
    	} else {
    		for (int i = 0; i < mTasks.size() - 1; i++) {
    			if (mTasks.get(i).compareTo(mTasks.get(i + 1)) > 0) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
	}
    
    /**
     * Converts a 1-based id to 0-based id as represented in the array.
     * @param taskNumberShownOnScreen 1-based index
     * @return 0-based index
     */
    private static int getTaskId(Integer taskNumberShownOnScreen) {
        return taskNumberShownOnScreen - 1;
    }
    
    private static void printTasks() {
    	for (int i = 0; i < mTasks.size(); i++) {
    		System.out.println(mTasks.get(i).getTitle());
    	}
    }
    
//    //METHODS FOR UPDATING GUI
//    static void displayTenTasks(int startIndex, IUserInterface ui) {
//        int i = getTaskId(startIndex);
//        List<ITask> tasks = mTasks.subList(i, i + 10);
//        ui.displayTasks(tasks);
//    }
//    
//    static void displayTaskDetail(int taskId, IUserInterface ui) {
//        ITask task = mTasks.get(getTaskId(taskId));
//        ui.displayTaskDetail(task);
//    }
    
}
