package list;

import java.io.IOException;

public class TestCongCommand implements ICommand {

	@Override
	public String execute() throws CommandExecutionException, IOException {
		TaskManager taskManager = TaskManager.getInstance();
		Controller.displayCongratulations(taskManager.getFloatingTasks());
		return null;
	}

	@Override
	public ICommand getInverseCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
