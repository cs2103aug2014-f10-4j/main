package list;

import java.io.IOException;

public class TestCongCommand implements ICommand {

	@Override
	public String execute() throws CommandExecutionException, IOException {
		TaskManager taskManager = TaskManager.getInstance();
		System.out.println("Congrats!");
		//Controller.displayCongratulations(taskManager.getFloatingTasks());
		return null;
	}

	@Override
	public ICommand getInverseCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
