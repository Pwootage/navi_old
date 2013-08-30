package bot.commands;

import java.util.Hashtable;
import java.util.regex.Pattern;

import bot.commands.java.LoveCommand;
import bot.exceptions.CommandAlreadyRegisteredException;
import bot.exceptions.InvalidCommandException;

/**
 * Processes and executes commands
 * 
 * @author Pwootage
 * 
 */
public class CommandManager {
	public static final String							COMMAND_PREFIX		= "@";
	public static final Pattern							VALID_COMMAND_REGEX	= Pattern.compile("[a-zA-Z0-9-_]+");
	
	private static Hashtable<String, CommandHandler>	commandHandlers		= new Hashtable<>();
	static {
		commandHandlers.put("love", new LoveCommand());
	}
	
	private CommandManager() {
	}
	
	public static void handleCommand(CommandCall cc) {
		CommandHandler ch = commandHandlers.get(cc.getCommand());
		if (ch != null) {
			ch.handleCommand(cc);
		} else {
			cc.getBot().sendMessage(cc.getChannel(), cc.getCaller() + ": " + cc.getCommand() + ": command not found");
		}
	}
	
	public static void registerHandler(String command, CommandHandler ch)
			throws CommandAlreadyRegisteredException, InvalidCommandException {
		
	}
	
	public static void registerHandler(String command, CommandHandler ch,
			boolean overwrite) throws CommandAlreadyRegisteredException,
			InvalidCommandException {
		if (!VALID_COMMAND_REGEX.matcher(command).matches()) {
			throw new InvalidCommandException(command, "Name does not match regex: " + VALID_COMMAND_REGEX.pattern());
		}
		if (!overwrite && commandHandlers.get(command) != null) {
			throw new CommandAlreadyRegisteredException(command);
		} else {
			commandHandlers.put(command, ch);
		}
	}
}
