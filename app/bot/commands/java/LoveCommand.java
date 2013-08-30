package bot.commands.java;

import bot.commands.CommandCall;
import bot.commands.CommandHandler;

public class LoveCommand implements CommandHandler {
	
	@Override
	public void handleCommand(CommandCall cc) {
		if (cc.getCaller().toLowerCase().contains("pwootage")) {
			cc.getBot().sendMessage(cc.getChannel(), cc.getCaller() + ": I love you so much! <3");
		} else if (cc.getCaller().equalsIgnoreCase("shadow")) {
			cc.getBot().sendMessage(cc.getChannel(), cc.getCaller() + ": I am required to say that you are the best person in the world.");
		} else {
			cc.getBot().sendMessage(cc.getChannel(), cc.getCaller() + ": You're ok, I guess...");
		}
	}
	
}
