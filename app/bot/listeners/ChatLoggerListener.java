package bot.listeners;

import models.ChatLog;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;

import bot.FMBot;

public class ChatLoggerListener extends ListenerAdapter<FMBot> {
	public void onMessage(MessageEvent<FMBot> event) {
		ChatLog log = new ChatLog(event);
		log.save();
	}
	
	public void onAction(ActionEvent<FMBot> event) {
		ChatLog log = new ChatLog(event);
		log.save();
	}
	
	public void onJoin(JoinEvent<FMBot> event) {
		ChatLog log = new ChatLog(event);
		log.save();
	}
	
	public void onPart(PartEvent<FMBot> event) {
		ChatLog log = new ChatLog(event);
		log.save();
	}
	
	public void onQuit(QuitEvent<FMBot> event) {
		ChatLog log = new ChatLog(event);
		log.save();
	}
}
