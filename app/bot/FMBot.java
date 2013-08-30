package bot;

import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

import models.ChatLog;
import models.config.ChannelConfig;
import models.config.ServerConfig;

import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.listeners.ChatLoggerListener;
import bot.listeners.CommandListener;
import bot.listeners.ReplyToAboutMeListener;

/**
 * The main bot class, extending the default bot. Mostly used so we have the
 * option of extending bot functionality if necessary
 * 
 * @author Pwootage
 * 
 */
public class FMBot extends PircBotX {
	private static final Logger		logger	= LoggerFactory.getLogger(FMBot.class);
	private ServerConfig			serverConfig;
	private UtilSSLSocketFactory	socketFactory;
	
	public FMBot(ServerConfig serverConfig) {
		super();
		if (serverConfig == null)
			throw new IllegalArgumentException("ServerConfig cannot be null");
		this.serverConfig = serverConfig;
		getListenerManager().addListener(new CommandListener());
		getListenerManager().addListener(new ChatLoggerListener());
		getListenerManager().addListener(new ReplyToAboutMeListener());
	}
	
	public ServerConfig getConfig() {
		return serverConfig;
	}
	
	public void start() throws NickAlreadyInUseException, IOException,
			IrcException {
		setName(serverConfig.getNick());
		setLogin(serverConfig.getLogin());
		setAutoReconnect(serverConfig.getAutoReconnect());
		
		logger.info("Connecting to {}:{}", serverConfig.getServerIP(),
				serverConfig.getServerPort());
		if (serverConfig.serverSSL) {
			socketFactory = new UtilSSLSocketFactory();
			socketFactory.trustAllCertificates();
			connect(serverConfig.getServerIP(), serverConfig.getServerPort(),
					socketFactory);
		} else {
			connect(serverConfig.getServerIP(), serverConfig.getServerPort());
		}
		logger.info("Done!");
		
		logger.info("Identifying with user {} and password {}", serverConfig
				.getNickservUser(), serverConfig.getNickservPassword()
				.replaceAll(".", "*"));
		changeNick(serverConfig.getNickservUser());
		identify(serverConfig.getNickservPassword());
		changeNick(serverConfig.getNick());
		
		Set<ChannelConfig> channels = ChannelConfig.find.where().eq("server",
				serverConfig).findSet();
		for (ChannelConfig channel : channels) {
			if (channel.hasPassword()) {
				logger.info("Joining channel {} with key {}", channel
						.getChannelName(),
						channel.getPassword().replaceAll(".", "*"));
				joinChannel(channel.getChannelName(), channel.getPassword());
			} else {
				logger.info("Joining channel {}", channel.getChannelName());
				joinChannel(channel.getChannelName());
			}
		}
	}
	
	@Override
	public void sendMessage(String target, String message) {
		super.sendMessage(target, message);
		// This will, as of right now, log outgoing pms. Need to check for this somehow...
		// This makes the response show up AFTER the request :P
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);
		ChatLog log = new ChatLog(getNick(), target, message, ChatLog.MESSAGE, cal.getTime());
		log.save();
	}
	
	@Override
	public void sendAction(String target, String action) {
		super.sendAction(target, action);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);
		ChatLog log = new ChatLog(getNick(), target, action, ChatLog.ACTION, cal.getTime());
		log.save();
	}
	
	@Override
	public void sendNotice(String target, String notice) {
		super.sendNotice(target, notice);
		// I don't think you can /notice to a channel
		// ChatLog log = new ChatLog(getNick(), target, action, ChatLog.ACTION);
		// log.save();
	}
}
