package list;

import java.io.IOException;
import java.util.Stack;

public class RedoCommand implements ICommand {

    private static final String MESSAGE_NOTHING_TO_REDO = "There is nothing to redo.";

    @Override
    public String execute() throws CommandExecutionException, IOException,
            InvalidTaskNumberException {
        Stack<ICommand> undoStack = Controller.getUndoStack();
        Stack<ICommand> redoStack = Controller.getRedoStack();
        
        if (redoStack.empty()) {
            return MESSAGE_NOTHING_TO_REDO;
        }
        ICommand undoneCommand = redoStack.pop();
        String reply = undoneCommand.execute();
        undoStack.push(undoneCommand.getInverseCommand());
        return reply;
    }

    @Override
    public ICommand getInverseCommand() {
        return null; //cannot be undone
    }

}
