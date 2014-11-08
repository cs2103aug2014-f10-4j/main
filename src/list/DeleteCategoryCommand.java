package list;

import java.io.IOException;
import java.util.List;

import list.model.Category;
import list.model.ITask;
import list.model.ICategory;

public class DeleteCategoryCommand implements ICommand {
    private static final String ERROR_CATEGORY_NOT_SPECIFIED = "Please specify which category to delete.";

    private static final String MESSAGE_CATEGORY_DELETED = "Category %s is deleted. Its task(s) are moved to the default category.";
    
    ICategory category;
    List<ITask> affectedTasks;
    TaskManager taskManager = TaskManager.getInstance();
    UndeleteCategoryCommand invCommand;
    
    public DeleteCategoryCommand() {
        // TODO Auto-generated constructor stub
    }
    
    public DeleteCategoryCommand(ICategory category) {
        setCategory(category);
    }
    
    public DeleteCategoryCommand(String categoryName) {
        if (taskManager.hasCategory(categoryName)) {
            category = taskManager.getCategory(categoryName);
        }
    }
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        if (category == null) {
            throw new CommandExecutionException(ERROR_CATEGORY_NOT_SPECIFIED);
        }
        affectedTasks = category.getList();
        makeInverseCommand(); //make inverse command for undo
        for (ITask task: affectedTasks) {
            task.setCategory(Category.getDefaultCategory());
        }
        taskManager.deleteCategory(category);
        Controller.displayCategories();
        taskManager.saveData();
        return String.format(MESSAGE_CATEGORY_DELETED, category.getName());
    }
    
    public DeleteCategoryCommand setCategory(ICategory category) {
        this.category = category;
        return this;
    }
    
    private void makeInverseCommand() {
        if (invCommand != null) {
            return;
        }
        invCommand = new UndeleteCategoryCommand();
        invCommand.setCategory(category);
        invCommand.setAffectedTasks(affectedTasks);
    }

    @Override
    public ICommand getInverseCommand() {
        return invCommand;
    }

}
