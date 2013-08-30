package models.scripts;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class FMBotScript extends Model {
	@Id
	public long		id;
	
	@Required
	@MinLength(1)
	public String	name;
	
	@Required
	@Lob
	public String	script;
	
	public boolean	enabled;
	
	public Date		lastUpdated;
	
	/**
	 * If the script is default, disable deletion of the script
	 */
	public boolean	defaultScript;
	
	public FMBotScript(String name) {
		this.name = name;
		this.script = "";
		this.enabled = true;
		this.defaultScript = false;
		this.lastUpdated = Calendar.getInstance().getTime();
	}
	
}
