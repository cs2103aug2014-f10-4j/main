package list;

import java.io.IOException;
import java.awt.Color;

import list.model.ICategory;

public class EditCategoryCommand implements ICommand {
    private static final String ERROR_CATEGORY_IS_NOT_SPECIFIED = "Error: Category is not specified.";
    private static final String MESSAGE_SUCCESS = "Category successfully edited.";
    Color color;
    String categoryName;
    ICategory category;
    EditCategoryCommand invCommand;
    private TaskManager taskManager = TaskManager.getInstance();
    
    public EditCategoryCommand() {
        
    }
    
    public EditCategoryCommand(String selectedCategoryName, String categoryName, Color color) {
        if (taskManager.hasCategory(categoryName)) {
            category = taskManager.getCategory(categoryName);
        }
        setCategoryName(categoryName);
        setColor(color);
    }
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        if (category == null) {
            throw new CommandExecutionException(ERROR_CATEGORY_IS_NOT_SPECIFIED);
        }
        makeInverseCommand();
        category.setName(categoryName);
        category.setColor(color);
        taskManager.saveData();
        Controller.displayCategories();
        return MESSAGE_SUCCESS;
    }

    public EditCategoryCommand setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
    
    public EditCategoryCommand setColor(Color color) {
        this.color = color;
        return this;
    }
    
    public EditCategoryCommand setCategory(ICategory category) {
        this.category = category;
        return this;
    }
    
    private void makeInverseCommand() {
        if (invCommand != null) {
            return;
        }
        invCommand = new EditCategoryCommand();
        invCommand.setCategory(category);
        invCommand.setCategoryName(category.getName());
        invCommand.setColor(category.getColor());
    }
    
    @Override
    public ICommand getInverseCommand() {
        return invCommand;
    }

}
