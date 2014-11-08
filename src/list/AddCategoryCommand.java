package list;

import java.awt.Color;
import java.io.IOException;

import list.model.ICategory;

public class AddCategoryCommand implements ICommand {
    private static final String MESSAGE_SUCCESS = "Successfully created the category %s.";
    private static final String ERROR_CATEGORY_ALREADY_EXISTS = "Category with title %s already exists.";
    private static final String ERROR_CATEGORY_TITLE_MUST_NOT_BE_EMPTY = "Category title must not be empty.";
    
    private String categoryName;
    private Color color;
    private ICategory category;
    
    private TaskManager taskManager = TaskManager.getInstance();
    
    public AddCategoryCommand() {
        
    }
    
    public AddCategoryCommand(String categoryName, Color color) {
        setCategoryName(categoryName);
        setColor(color);
    }
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        if (categoryName == null) {
            throw new CommandExecutionException(ERROR_CATEGORY_TITLE_MUST_NOT_BE_EMPTY);
        }
        if (taskManager.hasCategory(categoryName)) {
            throw new CommandExecutionException(String.format(ERROR_CATEGORY_ALREADY_EXISTS, categoryName));
        }
        category = taskManager.getCategory(categoryName);
        category.setColor(color);
        Controller.displayCategories();
        taskManager.saveData();
        return String.format(MESSAGE_SUCCESS, categoryName);
    }

    @Override
    public ICommand getInverseCommand() {
        DeleteCategoryCommand invCommand = new DeleteCategoryCommand();
        invCommand.setCategory(category);
        return invCommand;
    }
    
    public AddCategoryCommand setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
    
    public AddCategoryCommand setColor(Color color) {
        this.color = color;
        return this;
    }

}
