package eu.ehealth.db.config;

import eu.ehealth.SystemDictionary;


/**
 * superadmin / password
 * @author a572832
 *
 */
public class DBPropertiesCloudFoundry implements DBProperties
{
	

	@Override
	public String getURL()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_URL_CF : jdbc:mysql://95.211.172.242/ehealthdb?reconnect=true");
		return "jdbc:mysql://95.211.172.242/ehealthdb?reconnect=true";
	}

	
	@Override
	public String getUserName()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_USERNAME_CF : superadmin");
		return "superadmin";
	}
	

	@Override
	public String getPassword()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_PASSWORD_CF : password");
		return "password";
	}
	

}
