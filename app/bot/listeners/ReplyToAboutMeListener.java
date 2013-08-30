package bot.listeners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import bot.FMBot;

public class ReplyToAboutMeListener extends ListenerAdapter<FMBot> {
	public void onMessage(MessageEvent<FMBot> event) {
		Pattern p = Pattern.compile("^(.*) you(.*) " + event.getBot().getName() + "(.*)$");
		Pattern p2 = Pattern.compile("^" + event.getBot().getName() + "[,]? ?(.*) you(.*)$");
		Matcher m = p.matcher(event.getMessage());
		Matcher m2 = p2.matcher(event.getMessage());
		if (m.matches()) {
			String message = event.getUser().getNick() + ": Well " + m.group(1).toLowerCase() + " you";
			if (m.groupCount() >= 2) message += m.group(2);
			if (m.groupCount() >= 3) message += m.group(3);
			event.getBot().sendMessage(event.getChannel(), message);
		} else if (m2.matches()) {
			String message = event.getUser().getNick() + ": Well " + m2.group(1).toLowerCase() + " you";
			if (m2.groupCount() >= 2) message += m2.group(2);
			event.getBot().sendMessage(event.getChannel(), message);
		}
	}
}
