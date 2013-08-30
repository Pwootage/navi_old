package models.config;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.json.ServerConfigJSON;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class ServerConfig extends Model {
	public static final Finder<Long, ServerConfig>	find	= new Finder<Long, ServerConfig>(Long.class, ServerConfig.class);
	
	@Id
	public long										id;
	
	@Required
	@MinLength(3)
	@MaxLength(16)
	public String									name;
	
	@MinLength(3)
	@MaxLength(16)
	@Pattern(value = "[a-z_\\-\\[\\]\\\\^{}|`][a-z0-9_\\-\\[\\]\\\\^{}|`]*", message = "Must be a valid IRC nick")
	public String									nick;
	
	@MinLength(3)
	@MaxLength(16)
	@Pattern(value = "[a-z_\\-\\[\\]\\\\^{}|`][a-z0-9_\\-\\[\\]\\\\^{}|`]*", message = "Must be a valid IRC nick")
	public String									login;
	
	public String									nickservUser;
	
	public String									nickservPassword;
	
	@Required
	@MinLength(1)
	@MaxLength(255)
	public String									serverIP;
	
	@Min(2)
	@Max(65535)
	@Required
	public int										serverPort;
	
	public boolean									serverSSL;
	
	public boolean									autoReconnect;
	
	public boolean									enabled;
	
	public ServerConfig(String name) {
		this.name = name;
		nick = play.i18n.Messages.get("bot.name");
		login = null;
		serverIP = null;
		serverPort = 6667;
		serverSSL = false;
		autoReconnect = false;
		enabled = false;
	}
	
	public ServerConfigJSON getJSONObj() {
		ServerConfigJSON res = new ServerConfigJSON();
		res.id = id;
		res.name = name;
		res.nick = nick;
		res.login = login;
		res.nickservUser = nickservUser;
		res.nickservPassword = nickservPassword;
		res.serverIP = serverIP;
		res.serverPort = serverPort;
		res.serverSSL = serverSSL;
		res.autoReconnect = autoReconnect;
		res.enabled = enabled;
		return res;
	}
	
	public void updateWithJSONObj(ServerConfigJSON obj) {
		this.name = obj.name;
		this.nick = obj.nick;
		this.login = obj.login;
		this.nickservUser = obj.nickservUser;
		if (obj.nickservPassword != null) this.nickservPassword = obj.nickservPassword;
		this.serverIP = obj.serverIP;
		this.serverPort = obj.serverPort;
		this.serverSSL = obj.serverSSL;
		this.autoReconnect = obj.autoReconnect;
		this.enabled = obj.enabled;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String name) {
		this.nick = name;
	}
	
	public String getServerIP() {
		return serverIP;
	}
	
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public boolean isServerSSL() {
		return serverSSL;
	}
	
	public void setServerSSL(boolean serverSSL) {
		this.serverSSL = serverSSL;
	}
	
	public String getLogin() {
		if (login != null) {
			return login;
		} else {
			return getNick();
		}
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public boolean getAutoReconnect() {
		return autoReconnect;
	}
	
	public void setAutoReconnect(boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	
	public String getNickservPassword() {
		return nickservPassword;
	}
	
	public void setNickservPassword(String nickservPassword) {
		this.nickservPassword = nickservPassword;
	}
	
	public String getNickservUser() {
		return nickservUser;
	}
	
	public void setNickservUser(String nickservUser) {
		this.nickservUser = nickservUser;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
