package xyz.vtools;
/**
 * This file is part of the test project
 * Id: xyz.vtools/ConnectSession.java
 * Location: VHES
 * Developer: Nguyen Thach Vu
 * Date: May 25, 2016 8:37:24 AM
 * Email: nguyenthachvu.vn@gmail.com
 * Skype: vunguyen_1601
 * Note: N/A
 */

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectSession.
 */
public class ConnectSession {
	// Property based configuration
	/** The session factory config. */
	private static SessionFactory sessionFactoryConfig;

	/**
	 * Buildsession factory config.
	 *
	 * @return the session factory
	 */
	@SuppressWarnings("unchecked")
	private static SessionFactory buildsessionFactoryConfig() {
		try {

			Configuration configuration = new Configuration().configure("/config/hibernate.cfg.xml");
			Properties props = new Properties();

			File file = new File("config/hibernate.properties");

			if (file.exists()) {
				// load properties file
				//System.out.println("Config found");
				
				props.load(new FileReader("config/hibernate.properties"));
				// configuration.setProperties(p);
			} else {
				// load properties file
				props.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("config/hibernate.properties"));
				// configuration.setProperties(p);

			}

			Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
			while (enums.hasMoreElements()) {
				String key = enums.nextElement();
				String value = props.getProperty(key);
				// System.out.println(key + " : " + value);
				configuration.getProperties().setProperty(key, value);
			}

			configuration.addAnnotatedClass(Logrecords.class);
			configuration.addAnnotatedClass(Blocked.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			// HelpCoreDB.sSchema=
			// sessionFactory.getSettings().getDefaultSchemaName( )+".";

			// Detect connect db (oracle, SQL Server, My SQL,...)
			// String aaa = sessionFactory.getDialect().toString();

			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Gets the session factory config.
	 *
	 * @return the session factory config
	 */
	public static SessionFactory getsessionFactoryConfig() {
		if (sessionFactoryConfig == null)
			sessionFactoryConfig = buildsessionFactoryConfig();

		return sessionFactoryConfig;
	}

	/**
	 * Reset session factory config.
	 *
	 * @return the session factory
	 */
	public static SessionFactory resetSessionFactoryConfig() {
		sessionFactoryConfig = buildsessionFactoryConfig();
		return sessionFactoryConfig;
	}

}
