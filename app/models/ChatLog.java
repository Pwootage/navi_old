package models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;

import play.db.ebean.Model;
import bot.FMBot;

@Entity
public class ChatLog extends Model {
	public static final int	MESSAGE	= 0;
	public static final int	ACTION	= 1;
	public static final int	JOIN	= 2;
	public static final int	PART	= 3;
	
	@Id
	public long				id;
	public String			channel;
	public String			sender;
	public String			message;
	public Date				timestamp;
	public int				messageType;
	
	public ChatLog(String sender, String channel, String message) {
		this(sender, channel, message, MESSAGE);
	}
	
	public ChatLog(String sender, String channel, String message, int messageType) {
		this(sender, channel, message, messageType, Calendar.getInstance().getTime());
	}
	
	public ChatLog(String sender, String channel, String message, int messageType, Date timestamp) {
		this.channel = channel;
		this.sender = sender;
		this.message = message;
		this.messageType = messageType;
		this.timestamp = timestamp;
	}
	
	public ChatLog(MessageEvent<FMBot> event) {
		this(event.getUser().getNick(), event.getChannel().getName(), event.getMessage(), MESSAGE, new Date(event.getTimestamp()));
	}
	
	public ChatLog(ActionEvent<FMBot> event) {
		this(event.getUser().getNick(), event.getChannel().getName(), event.getMessage(), ACTION, new Date(event.getTimestamp()));
	}
	
	public ChatLog(JoinEvent<FMBot> event) {
		this(event.getUser().getNick(), event.getChannel().getName(), "", JOIN, new Date(event.getTimestamp()));
	}
	
	public ChatLog(PartEvent<FMBot> event) {
		this(event.getUser().getNick(), event.getChannel().getName(), event.getReason(), PART, new Date(event.getTimestamp()));
	}
	
	public ChatLog(QuitEvent<FMBot> event) {
		this(event.getUser().getNick(), "", event.getReason(), PART, new Date(event.getTimestamp()));
	}
	
	public String formatMessage() {
		switch (messageType) {
			case ACTION:
				return "* " + sender + " " + message;
			case JOIN:
				return sender + " has joined " + channel;
			case PART:
				return sender + " has left " + channel + " (" + message + ")";
			case MESSAGE:
			default:
				return "<" + sender + "> " + message;
		}
	}
}
