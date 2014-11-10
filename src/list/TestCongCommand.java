//@author A0113672L
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
		return null;
	}

}
