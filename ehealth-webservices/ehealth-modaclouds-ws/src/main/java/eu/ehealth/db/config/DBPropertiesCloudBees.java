package eu.ehealth.db.config;

import eu.ehealth.SystemDictionary;


/**
 * 
 * 
 * @author a572832
 * @date 06/10/2014.
 *
 */
public class DBPropertiesCloudBees implements DBProperties
{

	
	@Override
	public String getURL()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_URL_MYDB : " + System.getProperty("DATABASE_URL_MYDB"));
		return "jdbc:" + System.getProperty("DATABASE_URL_MYDB");
	}

	
	@Override
	public String getUserName()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_USERNAME_MYDB : " + System.getProperty("DATABASE_USERNAME_MYDB"));
		return System.getProperty("DATABASE_USERNAME_MYDB");
	}
	

	@Override
	public String getPassword()
	{
		SystemDictionary.webguiLog("INFO", "DATABASE_PASSWORD_MYDB : " + System.getProperty("DATABASE_PASSWORD_MYDB"));
		return System.getProperty("DATABASE_PASSWORD_MYDB");
	}
	

}
