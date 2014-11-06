package list;

import java.io.IOException;

import list.model.ITask;

public class UndeleteCommand implements ICommand {

    private String MESSAGE_SUCCESS = "Task successfully undeleted.";
    
    private ITask task;
    
    public UndeleteCommand() {
        
    }
    
    public UndeleteCommand(ITask task) {
        this.task = task;
    }
    
    public UndeleteCommand setTask(ITask task) {
        this.task = task;
        return this;
    }
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        TaskManager taskManager = TaskManager.getInstance();
        taskManager.undeleteTask(this.task);
        
        taskManager.saveData();
        
        return MESSAGE_SUCCESS;
    }

    @Override
    public ICommand getInverseCommand() {
        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setTask(this.task);
        return deleteCommand;
    }

}
