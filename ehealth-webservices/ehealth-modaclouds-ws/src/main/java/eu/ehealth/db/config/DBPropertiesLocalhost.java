package eu.ehealth.db.config;

import eu.ehealth.SystemDictionary;


/**
 * 
 * 
 * @author a572832
 * @date 08/10/2014.
 *
 */
public class DBPropertiesLocalhost implements DBProperties
{

	
	@Override
	public String getURL()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_URL_LOCAL : jdbc:mysql://localhost/ehealthdbservice?reconnect=true");
		return "jdbc:mysql://localhost/ehealthdbservice?reconnect=true";
	}

	
	@Override
	public String getUserName()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_USERNAME_LOCAL : rosogonatos_sc");
		return "rosogonatos_sc";
	}
	

	@Override
	public String getPassword()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_PASSWORD_LOCAL : 12345678r");
		return "12345678r";
	}
	
	
}
