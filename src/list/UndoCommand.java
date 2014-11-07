package list;

import java.io.IOException;
import java.util.Stack;


public class UndoCommand implements ICommand {
    private static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo.";
    
    @Override
    public String execute() throws CommandExecutionException, IOException {
        Controller.reportUndoRedoOperation();
        Stack<ICommand> undoStack = Controller.getUndoStack();
        Stack<ICommand> redoStack = Controller.getRedoStack();
        if (undoStack.empty()) {
            return MESSAGE_NOTHING_TO_UNDO;
        }
        ICommand invCommand = undoStack.pop();
        String reply = invCommand.execute();
        redoStack.push(invCommand.getInverseCommand());
        return reply;
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
