package list;

import java.io.IOException;

public class TestCongCommand implements ICommand {

	@Override
	public String execute() throws CommandExecutionException, IOException {
		Controller.displayCongratulations();
		return null;
	}

	@Override
	public ICommand getInverseCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
