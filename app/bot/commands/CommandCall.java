package bot.commands;

import bot.FMBot;


public class CommandCall {
	private String originalMessage;
	private String paramString;
	private String[] paramArray;
	private String command;
	private String caller;
	private String channel;
	private FMBot bot;

	public CommandCall(String message, String caller, String channel, FMBot bot) {
		// Remove prefix
		originalMessage = message.substring(CommandManager.COMMAND_PREFIX
				.length());
		paramString = message.replaceFirst("[^ ]+ ", "");
		paramArray = paramString.split(" ");
		command = originalMessage.split(" ")[0];
		
		this.caller = caller;
		this.channel = channel;
		this.bot = bot;
	}

	public String getOriginalMessage() {
		return originalMessage;
	}

	public String getParamString() {
		return paramString;
	}

	public String[] getParams() {
		return paramArray;
	}

	public String getCommand() {
		return command;
	}

	public String[] getParamArray() {
		return paramArray;
	}

	public String getCaller() {
		return caller;
	}

	public String getChannel() {
		return channel;
	}

	public FMBot getBot() {
		return bot;
	}
	
}
