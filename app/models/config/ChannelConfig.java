package models.config;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class ChannelConfig extends Model {
	public static final Finder<Long, ChannelConfig>	find	= new Finder<Long, ChannelConfig>(Long.class, ChannelConfig.class);
	
	@Id
	public long										id;
	
	@Required
	@MinLength(3)
	@MaxLength(24)
	public String									name;
	
	public String									password;
	
	@ManyToOne
	public ServerConfig								server;
	
	public ChannelConfig(ServerConfig server, String name) {
		this(server, name, null);
	}
	
	public ChannelConfig(ServerConfig server, String channel, String password) {
		this.server = server;
		this.name = channel;
		if (password != null && password.length() > 0) {
			this.password = password;
		} else {
			this.password = null;
		}
	}
	
	public String getChannelName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasPassword() {
		return password != null && password.length() > 0;
	}
}
