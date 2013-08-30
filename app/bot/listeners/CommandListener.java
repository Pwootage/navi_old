package bot.listeners;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import bot.FMBot;
import bot.commands.CommandCall;
import bot.commands.CommandManager;

public class CommandListener extends ListenerAdapter<FMBot> {
	public void onMessage(MessageEvent<FMBot> event) {
		if (event.getMessage().startsWith(CommandManager.COMMAND_PREFIX)) {
			CommandCall cc = new CommandCall(event.getMessage(), event.getUser().getNick(), event.getChannel().getName(), event.getBot());
			CommandManager.handleCommand(cc);
		}
	}
}
