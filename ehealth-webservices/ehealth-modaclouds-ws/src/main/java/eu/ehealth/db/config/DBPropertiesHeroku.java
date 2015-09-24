package eu.ehealth.db.config;

import eu.ehealth.SystemDictionary;


/**
 * 
 * 
 * @author a572832
 * @date 08/11/2014.
 *
 */
public class DBPropertiesHeroku implements DBProperties
{

	
	@Override
	public String getURL()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_URL_HEROKU : jdbc:mysql://eu-cdbr-west-01.cleardb.com/heroku_52c9cbef6918e0d?reconnect=true");
		return "jdbc:mysql://eu-cdbr-west-01.cleardb.com/heroku_52c9cbef6918e0d?reconnect=true";
	}

	
	@Override
	public String getUserName()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_USERNAME_HEROKU : b5328504c7330a");
		return "b5328504c7330a";
	}
	

	@Override
	public String getPassword()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_PASSWORD_HEROKU : d1544c6e");
		return "d1544c6e";
	}
	

}
