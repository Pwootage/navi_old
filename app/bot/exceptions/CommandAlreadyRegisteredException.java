package bot.exceptions;

public class CommandAlreadyRegisteredException extends Exception {
	
	public CommandAlreadyRegisteredException(String command) {
		super("Cannot register command \"" + command + "\": Command already is registered! Register with override = true to override anyways.");
	}
}
