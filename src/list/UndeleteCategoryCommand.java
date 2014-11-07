package list;

import java.io.IOException;
import java.util.List;

import list.model.ICategory;
import list.model.ITask;

public class UndeleteCategoryCommand implements ICommand {
    private static final String ERROR_INCOMPLETE_INFORMATION = "Incomplete information to undo delete category.";
    private static final String MESSAGE_SUCCESS = "Undo delete category success.";
    List<ITask> tasks;
    ICategory category;
    TaskManager taskManager = TaskManager.getInstance();
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        if (tasks == null || category == null) {
            throw new CommandExecutionException(ERROR_INCOMPLETE_INFORMATION);
        }
        ICategory newCategory = taskManager.getCategory(this.category.getName()); //do not reuse the old category
        for (ITask task: tasks) {
            task.setCategory(newCategory);
        }

        taskManager.saveData();
        Controller.displayCategories();
        return MESSAGE_SUCCESS;
    }
    
    public UndeleteCategoryCommand setAffectedTasks(List<ITask> tasks) {
        this.tasks = tasks;
        return this;
    }
    
    public UndeleteCategoryCommand setCategory(ICategory category) {
        this.category = category;
        return this;
    }

    @Override
    public ICommand getInverseCommand() {
        DeleteCategoryCommand invCommand = new DeleteCategoryCommand();
        invCommand.setCategory(category);
        return invCommand;
    }

}
