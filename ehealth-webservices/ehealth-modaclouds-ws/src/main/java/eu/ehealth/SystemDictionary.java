package eu.ehealth;

import it.polimi.tower4clouds.java_app_dc.Property;
import it.polimi.tower4clouds.java_app_dc.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import eu.ehealth.db.DbQueries;
import eu.ehealth.db.config.DBInitializer;
import eu.ehealth.security.DataBasePasswords;
import eu.ehealth.security.KeyStoreConfig;
import eu.ehealth.server.users.RunnableCheckSessionUsers;


/**
 *
 * 
 * @author 
 */
public class SystemDictionary
{
	
	
	// app version
	public static String APP_VERSION = "ehealth-modaclouds-ws v0.0.2 [default]";		
		
	// scheduler
	public static ScheduledExecutorService _scheduler;
	
	// Storage Component
	public static StorageComponentMain STORAGE_COMPONENT;
	
	public static boolean PERIODIC_TASKS = false;
		
	// E-Health Web Services configuration variables
	public static Level APPLICATION_DEBUG_LEVEL = Level.DEBUG;	// DEBUG level output : DEBUG, INFO ...
	public static boolean ACTIVATE_INTERCEPTOR_DB = true;		// store SOAP messages in DB - for DEBUG purposes
	public static boolean ACTIVATE_OUT_INTERCEPTOR_DB = false;	// store SOAP messages in DB - for DEBUG purposes
	public static DataBase DATABASE = DataBase.MySQL;			// DataBase used
	public static boolean DATABASE_ENCRYPTION = false;			// Encrypt data with JASYPT
	public static boolean HIBERNATE_ENCRYPTION = false;			// Encrypt hibernate properties with JASYPT
	public static boolean DATABASE_WITH_SSL = false;			// SSL Communication with Database 
	public static String RULES_FILE = "rules.json";				// Rules file
	
	// PaaS database
	public static boolean DATABASE_INITIALIZED = true;			// Database correctly installed and configured
	public static String URL = ""; 			//"jdbc:mysql://localhost/db_pruebas1?reconnect=true";
	public static String USERNAME = ""; 	//"rosogonatos_sc";
	public static String PASSWORD = ""; 	//"12345678r";
	public static String INIT_SCRIPT = "20141006_bdNoEnc.sql";	// sql script with all initial data needed to run the application
	
	// system initialized
	private static boolean _initialized = false;
	// database types
	public static enum DataBase 
	{
		MySQL, OTHER
	}
	// logger
	private static Logger logger = null;

	
	/**
	 * Initialization
	 */
	static
	{
		// normal flow - log
		webguiLog("INFO", SystemDictionary.class.getName() + " : initializing...");
		boolean initializedCorrectly = true;
		webguiLog("INFO", SystemDictionary.class.getName() + " : retrieving values ...");
		
		// ACTIONS & VALUES FROM PROPERTIES FILE //////////////////////////////
		try 
		{
			PropertiesConfiguration props = new PropertiesConfiguration("ws.properties");
			
			if ((props == null) || (props.isEmpty())) {
				webguiLog("FATAL", SystemDictionary.class.getName() + " : Error getting application properties");
			}
			else {
				webguiLog("INFO", SystemDictionary.class.getName() + " : application properties loaded");
				try {
					Iterator<String> itr = props.getKeys();
					while(itr.hasNext()) { // CONFIG_PROPERTIES.getKeys()
						String key = (String) itr.next();
						webguiLog("INFO", SystemDictionary.class.getName() + " : ..." + key + " : " + props.getString(key));
					}
				}
				catch (Exception ex1) { }
			}
			
			String version = props.getString("version", APP_VERSION);
			// show app version
			webguiLog("INFO", "........................................................................................");
			webguiLog("INFO", SystemDictionary.class.getName() + " : " + version);
			webguiLog("INFO", "........................................................................................");
			
			// RULES_FILE
			String rulesFileCfg = props.getString("rules");
			if ((rulesFileCfg != null) && (rulesFileCfg.trim().length() > 0)) {
				RULES_FILE = rulesFileCfg;
				webguiLog("INFO", SystemDictionary.class.getName() + " : RULES_FILE ... " + rulesFileCfg);
			}
			else 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : RULES_FILE ... DEFAULT");
			}
			
			// ENCRYPTED PASSWORDS in hibernate.cfg.xml
			String encryptedHibernateCfg = props.getString("encryptedHibernateCfg");
			if ((encryptedHibernateCfg != null) && (encryptedHibernateCfg.equalsIgnoreCase("true"))) {
				HIBERNATE_ENCRYPTION = true;
				webguiLog("INFO", SystemDictionary.class.getName() + " : HIBERNATE_ENCRYPTION ... true");
			}
			else 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : HIBERNATE_ENCRYPTION ... false");
			}
			
			// DATABASE_ENCRYPTION : database content
			String encryptionEnabled = props.getString("encryptdb");
			if ((encryptionEnabled != null) && (encryptionEnabled.equalsIgnoreCase("true"))) {
				DATABASE_ENCRYPTION = true;
				DataBasePasswords.registerEncryptionMethods();
				webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_ENCRYPTION ... true");
			}
			else {
				webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_ENCRYPTION ... false");
			}

			// DATABASE_WITH_SSL : communitacions with database
			String dbSslEnabled = props.getString("dbSslEnabled");
			if ((dbSslEnabled != null) && (dbSslEnabled.equalsIgnoreCase("true"))) {
				KeyStoreConfig keyCfg = new KeyStoreConfig();
				if (keyCfg.configureSSLParameters(props)) 
				{
					DATABASE_WITH_SSL = true;
					webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_WITH_SSL ... true");
				}
			}
			else 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_WITH_SSL ... false");
			}
			
			// DATABASE INITIALIZATION
			initializeDB(false, props.getString("PaaS"), props.getString("sqliniscript"));
			
			// DATA COLLECTORS --> MONITORING PLATFORM TODO
			webguiLog("INFO", SystemDictionary.class.getName() + " : Data collectors ...");
			String data_collectors = props.getString("data_collectors");
			if ("true".equals(data_collectors)) 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Starting data collectors ...");
				try
				{
					Map<Property, String> applicationProperties = new HashMap<Property, String>();
					applicationProperties.put(Property.ID, "App1");
					applicationProperties.put(Property.TYPE, "App");
					applicationProperties.put(Property.VM_ID, "Frontend1");
					applicationProperties.put(Property.VM_TYPE, "Frontend");
					applicationProperties.put(Property.CLOUD_PROVIDER_ID, "CF");
					applicationProperties.put(Property.CLOUD_PROVIDER_TYPE, "PaaS");
					
					String mpIp = props.getString("MODACLOUDS_T4C_ENDPOINT_IP", "109.231.126.100");
					int mpPort = props.getInt("MODACLOUDS_T4C_ENDPOINT_PORT", 8170);
					
					Registry.initialize(mpIp, mpPort, applicationProperties, "eu.ehealth.ws_client");
					Registry.startMonitoring();
					
					webguiLog("INFO", SystemDictionary.class.getName() + " : Data Collector initialized");
				}
				catch (Exception e)
				{
					logException(e);
				}
			}
			else 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Data collectors are not enabled");
			}
			
			// Initialize Storage Component
			String database_sysproperty = props.getString("database.sysproperty");
			boolean useSysPropDB = false;
			if ("true".equals(database_sysproperty)) 
			{
				useSysPropDB = true;
			}
			STORAGE_COMPONENT = new StorageComponentMain(true, rulesFileCfg, HIBERNATE_ENCRYPTION, 
					DATABASE_ENCRYPTION, DATABASE_WITH_SSL, false, useSysPropDB);
			if (!STORAGE_COMPONENT.initialize()) {
				webguiLog("ERROR", SystemDictionary.class.getName() + " : Storage Component NOT initialized");
			}
			else
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Storage Component initialized");
			}
			
			// PERIODIC TASKS 
			if (PERIODIC_TASKS) {
				try 
				{
					_scheduler = Executors.newScheduledThreadPool(1);
	
					webguiLog("INFO", SystemDictionary.class.getName() + " : Starting CheckUsers task ...");
					Runnable checkSessionUsers = new RunnableCheckSessionUsers();
					_scheduler.scheduleWithFixedDelay(checkSessionUsers, 540, 25, TimeUnit.SECONDS);
					webguiLog("INFO", SystemDictionary.class.getName() + " : CheckUsers task initialized");
				}
				catch (Exception ex) {
					logException(ex);
				}
			}
		}
		catch (Exception ex) {
			initializedCorrectly = false;
			logException(ex);
		}
		
		///////////////////////////////////////////////////////////////////////
		if (initializedCorrectly) 
		{
			webguiLog("INFO", SystemDictionary.class.getName() + " : initialized");
		}
		else {
			webguiLog("WARN", SystemDictionary.class.getName() + " : initialized with ERRORS");
		}
	}
	
	
	/**
	 * 
	 */
	public static boolean initialize() {
		if (!_initialized)
		{
			webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_INITIALIZED [db status] ... " + DATABASE_INITIALIZED);
			if (!DATABASE_INITIALIZED) {
				initializeDB(true, null, null);
			}
			webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_INITIALIZED [db status] ... " + DATABASE_INITIALIZED);
			
			webguiLog("INFO", SystemDictionary.class.getName() + " : _initialized [eHealth init parameters] ... " + _initialized);
			if ((DATABASE_INITIALIZED) && (!_initialized)) {
				try
				{
					DbQueries db = new DbQueries();
					ArrayList<String[]> roles = (ArrayList<String[]>) db.getEhealthRoles();
					Globals.addValuesEHEALTH_PARAMETERS(roles);
					
					ArrayList<String[]> params = (ArrayList<String[]>) db.getEhealthParameters();
					Globals.addValuesEHEALTH_PARAMETERS(params);
					
					webguiLog("INFO", SystemDictionary.class.getName() + " : EHealth parameters retrieved");
				}
				catch (Exception e)
				{
					SystemDictionary.logException(e);
				}
			
				_initialized = true;
			}
			else 
			{
				webguiLog("WARN", SystemDictionary.class.getName() + " : Could not initialize database");
			}
			webguiLog("INFO", SystemDictionary.class.getName() + " : _initialized [eHealth init parameters] ... " + _initialized);
		}
		
		return _initialized;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean getInitializedValue() {
		return _initialized;
	}
	
	
	/**
	 * 
	 * @param getDataFromProps
	 * @param paaS
	 * @param scriptFile
	 */
	private static void initializeDB(boolean getDataFromProps, String paaS, String scriptFile) {
		try 
		{
			if (getDataFromProps) 
			{
				PropertiesConfiguration props = new PropertiesConfiguration("ws.properties");
				paaS = props.getString("PaaS");
				scriptFile = props.getString("sqliniscript");
			}
			
			// DATABASE INITIALIZATION
			webguiLog("INFO", SystemDictionary.class.getName() + " : Checking database ...");
			
			DBInitializer dbInitializer = new DBInitializer(paaS, scriptFile);
			if (!dbInitializer.existsDB()) 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Database is not configured");
				webguiLog("INFO", SystemDictionary.class.getName() + " : Configuring database ...");
				
				DATABASE_INITIALIZED = dbInitializer.initializeDB();
			}
			
			if (DATABASE_INITIALIZED) 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Database is configured and running");
			}
			else 
			{
				webguiLog("WARN", SystemDictionary.class.getName() + " : Database is NOT configured");
			}
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
	}


	/**
	 * Static method which allows the whole system to log messages without
	 * managing log4java objects
	 * 
	 * @param type Log level such as "DEBUG", "INFO", ...
	 * @param message String containing log message
	 */
	public static void webguiLog(String type, String message)
	{
		if (logger == null)
		{
			logger = Logger.getLogger("WSERVICES");
			PropertyConfigurator.configure(SystemDictionary.class.getClassLoader().getResource("log4j.properties"));
			logger.setLevel(APPLICATION_DEBUG_LEVEL);
		}
		if (type.equals("INFO"))
		{
			logger.info(message);
		}
		else if (type.equals("DEBUG"))
		{
			logger.debug(message);
		}
		else if (type.equals("WARN"))
		{
			logger.warn(message);
		}
		else if (type.equals("ERROR"))
		{
			logger.error(message);
		}
		else if (type.equals("FATAL"))
		{
			logger.fatal(message);
		}
		else if (type.equals("TRACE"))
		{
			logger.trace(message);
		}
	}
	
	
	/**
     * 
     * @param ex
     */
	public static void logException(Exception ex) 
    {
		logger.error(ex.getMessage(), ex);
    }


}
