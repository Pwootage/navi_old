package bot.exceptions;

public class InvalidCommandException extends Exception {
	
	public InvalidCommandException(String command) {
		super("Cannot register command \"" + command + "\": Command is invalid!");
	}
	
	public InvalidCommandException(String command, String reason) {
		super("Cannot register command \"" + command + "\": Command is invalid: " + reason);
	}
}
