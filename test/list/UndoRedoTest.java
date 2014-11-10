//@author Å0113672L
package list;

import static org.junit.Assert.*;
import javafx.application.Application;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import list.AddCommand;
import list.CommandBuilder.RepeatFrequency;
import list.model.Date;
import list.model.ITask;
import list.model.ITask.TaskStatus;

public class UndoRedoTest {
    private static TaskManager taskManager = TaskManager.getInstance();
    
    private static AddCommand addCommand;
    
    private static boolean uiReady = false;
    
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    @BeforeClass
    public static void setup() throws Exception {
        Thread thread = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(Controller.class, new String[0]);
            }
        };
        thread.setDaemon(true);
        thread.start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        
        addCommand = new AddCommand()
            .setCategory(taskManager.getCategory("Friends"))
            .setTitle("Hang out with the awesome")
            .setEndDate(new Date("13-11-2014"))
            .setStartDate(new Date("13-11-2014"))
            .setPlace("Yayoiken Star Vista")
            .setNotes("p.s. bring along chocolates")
            .setRepeatFrequency(RepeatFrequency.MONTHLY);
        
    }
    
    @Before
    public void clearTaskManager() throws InterruptedException {
        taskManager.clearTasks();
    }
    
    @Test
    public void undoAddCommand() throws Exception {
        addCommand.execute();
        
        assertEquals(1, taskManager.getAllTasks().size());
        
        ICommand invCommand = addCommand.getInverseCommand();
        invCommand.execute();
        
        assertEquals(0, taskManager.getAllTasks().size());
        
        ICommand redoCommand = invCommand.getInverseCommand();
        redoCommand.execute();
        
        assertEquals(1, taskManager.getAllTasks().size());
        
        ITask task = taskManager.getAllTasks().get(0);
        
        assertEquals(taskManager.getCategory("Friends"), task.getCategory());
        assertEquals("Hang out with the awesome", task.getTitle());
        assertEquals(new Date("13-11-2014"), task.getEndDate());
        assertEquals(new Date("13-11-2014"), task.getStartDate());
        assertEquals("Yayoiken Star Vista", task.getPlace());
        assertEquals("p.s. bring along chocolates", task.getNotes());
        assertEquals(RepeatFrequency.MONTHLY, task.getRepeatFrequency());
    }
    
    @Test
    public void undoEditCommand() throws Exception {
        addCommand.execute();
        
        ITask task = taskManager.getAllTasks().get(0);
        
        EditCommand editCommand = new EditCommand()
            .setTitle("School")
            .setNotes("Studying is more important than friends")
            .setPlace("PC Commons")
            .setStartDate(new Date("01-01-2014"))
            .setEndDate(new Date("01-01-2014"))
            .setRepeatFrequency(RepeatFrequency.NONE)
            .setTask(task)
            .setCategory(taskManager.getCategory("School"));
        
        editCommand.execute();
        
        
        ICommand undoCommand = editCommand.getInverseCommand();
        undoCommand.execute();
        
        assertEquals(taskManager.getCategory("Friends"), task.getCategory());
        assertEquals("Hang out with the awesome", task.getTitle());
        assertEquals(new Date("13-11-2014"), task.getEndDate());
        assertEquals(new Date("13-11-2014"), task.getStartDate());
        assertEquals("Yayoiken Star Vista", task.getPlace());
        assertEquals("p.s. bring along chocolates", task.getNotes());
        assertEquals(RepeatFrequency.MONTHLY, task.getRepeatFrequency());
    }
    
    @Test
    public void undoMarkCommand() throws Exception {
        addCommand.execute();
        
        ITask task = taskManager.getAllTasks().get(0);
        
        MarkCommand markCommand = new MarkCommand(task);
        markCommand.execute();
        
        assertEquals(TaskStatus.DONE, task.getStatus());
        
        ICommand undoCommand = markCommand.getInverseCommand();
        undoCommand.execute();
        
        assertEquals(TaskStatus.PENDING, task.getStatus());
        
        ICommand redoCommand = undoCommand.getInverseCommand();
        redoCommand.execute();
        
        assertEquals(TaskStatus.DONE, task.getStatus());
    }
    
    @Test
    public void undoDeleteCommand() throws Exception {
        addCommand.execute();
        
        ITask task = taskManager.getAllTasks().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(task);
        deleteCommand.execute();
        
        assertEquals(0, taskManager.getAllTasks().size());
        
        ICommand undoCommand = deleteCommand.getInverseCommand();
        undoCommand.execute();
        
        assertEquals(1, taskManager.getAllTasks().size());
        
        ICommand redoCommand = undoCommand.getInverseCommand();
        redoCommand.execute();
        
        assertEquals(0, taskManager.getAllTasks().size());
    }

}
