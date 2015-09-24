package eu.ehealth.db.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import eu.ehealth.SystemDictionary;


/**
 * 
 * 
 * @author a572832
 * @date 06/10/2014.
 *
 */
public class DBInitializer
{
	
	
	/**
	 * Constructor
	 */
	public DBInitializer(String selectedPaaS, String initScript) 
	{
		try 
		{
			SystemDictionary.INIT_SCRIPT = initScript;
			
			DBProperties dbprops = null;
			
			if ("CloudBees".equalsIgnoreCase(selectedPaaS)) 
			{
				SystemDictionary.webguiLog("INFO", "[DBInitializer] Using CloudBees configuration ...");
				dbprops = new DBPropertiesCloudBees();
			}
			else if ("localhost".equalsIgnoreCase(selectedPaaS)) 
			{
				SystemDictionary.webguiLog("INFO", "[DBInitializer] Using local configuration ...");
				dbprops = new DBPropertiesLocalhost();
			}
			else if ("Heroku".equalsIgnoreCase(selectedPaaS)) 
			{
				SystemDictionary.webguiLog("INFO", "[DBInitializer] Using Heroku configuration ...");
				dbprops = new DBPropertiesHeroku();
			}
			// CloudFoundry - ATOS
			else if ("CloudFoundry".equalsIgnoreCase(selectedPaaS)) 
			{
				SystemDictionary.webguiLog("INFO", "[DBInitializer] Using CloudFoundry configuration ...");
				dbprops = new DBPropertiesCloudFoundry();
			}
			
			if (dbprops != null)
			{
				SystemDictionary.URL = dbprops.getURL();
				SystemDictionary.USERNAME = dbprops.getUserName();
				SystemDictionary.PASSWORD = dbprops.getPassword();
			}
			else 
			{
				SystemDictionary.webguiLog("WARN", "[DBInitializer] Using default configuration ...");
			}
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
	}
	

	/**
	 * 
	 * @return
	 */
	public boolean existsDB() 
	{
		if ((SystemDictionary.URL == null) || (SystemDictionary.URL.trim().length() == 0) || (SystemDictionary.URL.indexOf("null") != -1)) 
		{
			SystemDictionary.webguiLog("WARN", "[DBInitializer] Database does not exist!! (1)");
			return false;
		}
		
		Connection conn = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			SystemDictionary.webguiLog("DEBUG", "[DBInitializer]  ..." + SystemDictionary.URL);
			SystemDictionary.webguiLog("DEBUG", "[DBInitializer]  ..." + SystemDictionary.USERNAME);
			SystemDictionary.webguiLog("DEBUG", "[DBInitializer]  ..." + SystemDictionary.PASSWORD);
			conn = DriverManager.getConnection(SystemDictionary.URL, SystemDictionary.USERNAME, SystemDictionary.PASSWORD);
			
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "aladdinuser", null);
			if(resultSet.next()) 
			{
				// Table exists
				return true;
			}
		}
		catch (Exception ex) 
		{
			SystemDictionary.logException(ex);
		}
		finally 
		{
			if (conn != null) 
			{
				try
				{
					conn.close();
				}
				catch (SQLException e) {}
			}
		}
		
		SystemDictionary.webguiLog("WARN", "[DBInitializer] Database does not exist!! (2)");
		return false;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean initializeDB() 
	{
		if ((SystemDictionary.URL == null) || (SystemDictionary.URL.trim().length() == 0) || (SystemDictionary.URL.indexOf("null") != -1)) 
		{
			return false;
		}
		
		Connection conn = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(SystemDictionary.URL, SystemDictionary.USERNAME, SystemDictionary.PASSWORD);
			
			boolean booleanAutoCommit = false;
			boolean booleanStopOnerror = true;
			
			ClassLoader loader = this.getClass().getClassLoader();
			JDBCScriptRunner runner = new JDBCScriptRunner(conn, booleanAutoCommit, booleanStopOnerror);
			runner.runScript(new BufferedReader(new InputStreamReader(loader.getResourceAsStream(SystemDictionary.INIT_SCRIPT), "UTF-8")));
			
			conn.close();
			return true;
		}
		catch (Exception ex) 
		{
			SystemDictionary.logException(ex);
			return false;
		}
		finally {
			if (conn != null) 
			{
				try
				{
					conn.close();
				}
				catch (SQLException e) {}
			}
		}
	}
	

}
