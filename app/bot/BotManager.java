package bot;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import models.config.ServerConfig;

import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages bot instances
 * 
 * @author pwootage
 * 
 */
public class BotManager {
	private static final Logger			logger		= LoggerFactory.getLogger(BotManager.class);
	private static HashMap<Long, FMBot>	instances	= new HashMap<>();
	private static volatile boolean		botsActive	= false;
	private static Calendar				startDate;
	
	/**
	 * This is a static class
	 */
	private BotManager() {
	}
	
	/**
	 * Starts a bot with the config given if it's not already started. <br/>
	 * <b>Note</b>: Does no connection checking, simply checks if a bot exists.
	 * 
	 * @param serverConfig
	 *            Configuration for the bot
	 * @throws NickAlreadyInUseException
	 *             If there's an error starting the bot
	 * @throws IOException
	 *             If there's an error starting the bot
	 * @throws IrcException
	 *             If there's an error starting the bot
	 */
	public static void startBot(Long confID) throws NickAlreadyInUseException, IOException, IrcException {
		if (getBot(confID) != null) {
			return;
		}
		ServerConfig conf = ServerConfig.find.byId(confID);
		if (conf == null) return;
		FMBot bot = new FMBot(conf);
		try {
			bot.start();
		} catch (IOException | IrcException e) {
			bot.disconnect();
			throw e;
		}
		instances.put(confID, bot);
	}
	
	/**
	 * Kills the bot with the config given and then removes it from the bot collection.<br/>
	 * If the bot does not exist, does nothing.
	 * 
	 * @param conf
	 *            Config of the bot to kill
	 */
	@SuppressWarnings("deprecation")
	public static void stopBot(Long confID) {
		FMBot bot = getBot(confID);
		if (bot == null) return;
		ServerConfig conf = ServerConfig.find.byId(confID);
		if (conf == null) return;
		logger.info("Stopping bot \"{}\"", conf.name);
		if (bot.isConnected()) {
			bot.quitServer("Shutting down.");
			@SuppressWarnings("rawtypes")
			DisconnectEvent disconnectEvent = null;
			try {
				disconnectEvent = bot.waitFor(DisconnectEvent.class);
			} catch (InterruptedException e) {
				// Don't care
			}
			if (disconnectEvent != null) {
				logger.info("Bot \"{}\" disconnnected at time {}", conf.name, disconnectEvent.getTimestamp());
			} else {
				logger.info("Bot \"{}\" probobly disconnected, but event was null.", conf.name);
			}
		} else {
			logger.info("Bot \"{}\" probobly disconnected, since connected flag was false.", conf.name);
		}
		instances.remove(conf);
	}
	
	/**
	 * Get the bot using the conf given (if it exists)
	 * 
	 * @param conf
	 *            Config of the bot in question
	 * @return
	 */
	public static FMBot getBot(Long confID) {
		return instances.get(confID);
	}
	
	public static boolean areBotsEnabled() {
		return botsActive;
	}
	
	public static void setBotsEnabled(boolean enabled) {
		botsActive = enabled;
		if (enabled) {
			startDate = Calendar.getInstance();
		}
	}
	
	public static String getFormattedUptime() {
		if (!areBotsEnabled()) return "0d, 0h, 0m, 0s";
		Calendar now = Calendar.getInstance();
		long msDiff = now.getTimeInMillis() - startDate.getTimeInMillis();
		long days = msDiff / (1000 * 60 * 60 * 24);
		msDiff %= 1000 * 60 * 60 * 24;
		long hours = msDiff / (1000 * 60 * 60);
		msDiff %= 1000 * 60 * 60;
		long minutes = msDiff / (1000 * 60);
		msDiff %= 1000 * 60;
		long seconds = msDiff / (1000);
		msDiff %= 1000;
		long ms = msDiff;
		
		String result = "";
		result += days + "d, ";
		result += hours + "h, ";
		result += minutes + "m, ";
		result += seconds + "s ";
		return result;
	}
}
