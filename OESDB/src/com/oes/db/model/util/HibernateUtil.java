package com.oes.db.model.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.transaction.SystemException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.mapping.Table;
import org.hibernate.metadata.ClassMetadata;

import com.mbi.oes.db.utils.PropsUtil;

public class HibernateUtil {
	public static String MY_SQL = "hibernate_mysql";  //key to hibernateConfiguration for My SQL
	private static final boolean usingAnnotations = true;
	private static HashMap<String, SessionFactory> sessionFactoryMap = new HashMap<String, SessionFactory>();
	public static final ThreadLocal<Map<String, Session>> sessionMapsThreadLocal = new ThreadLocal<Map<String, Session>>();
	public static final boolean USE_MYSQL = true;
	private static 	SessionFactory sf;
	private static HibernateUtil singleton = new HibernateUtil();
	private static Logger logger_ = Logger.getLogger(HibernateUtil.class);
	private static AnnotationConfiguration cfg;
	private static boolean configLoaded = false;
	static {/*
		try {
			String fileName;
			ArrayList<String> cfglist = new ArrayList<String>(); 
			//	cfglist.add(SQL_SERVER);
			cfglist.add(MY_SQL);
			for (String key : cfglist) {
				fileName = key + ".cfg.xml";
				if (usingAnnotations) {
					System.out.println("loading cfg file : "+ fileName);
					cfg = new AnnotationConfiguration();
					sf = cfg.configure(fileName)
					.buildSessionFactory();
					System.out.println("Loaded Hibernate configuration for "+key);
				}
				sessionFactoryMap.put(key, sf);
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			System.out.println(ex);
			logger_.info("Initial SessionFactories creation failed.");
			throw new ExceptionInInitializerError(ex);
		}
	*/}

	public static String getPKColumn(String tableName) {
		ClassMetadata metaData = sf.getClassMetadata(tableName);
		String id = metaData.getIdentifierPropertyName();
		return id;
	}
	
	public static java.util.List<String> getTableColumns(String tableName){
		java.util.List<String> columnNames = new ArrayList<String>();
		ClassMetadata metaData = sf.getClassMetadata(tableName);
		System.out.println("TableName: "+tableName+"Is MetaData null: "+(metaData == null));
		logger_.info("TableName: "+tableName+"Is MetaData null: "+(metaData == null));
		String tempFieldNames[] = metaData.getPropertyNames();
		for (String colName : tempFieldNames) {
			columnNames.add(colName);
		}
		String id = metaData.getIdentifierPropertyName(); //getPropertyNames doesn't return ID attributes
		if(id != null) {
			columnNames.add(id);
		}
		return columnNames;
	}
	public static HibernateUtil getInstance() {
		return singleton;
	}
	
	public static String getTableNameFromEntity(String entityName){
		String tableName = cfg.getClassMapping(entityName).getTable().getName();
		return tableName;
	}
	

	public Session currentSession(String key) throws HibernateException {
		Map<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
		.get();

		if (sessionMaps == null) {
			sessionMaps = new HashMap<String, Session>();
			sessionMapsThreadLocal.set(sessionMaps);
		}

		// Open a new Session, if this Thread has none yet
		Session s = (Session) sessionMaps.get(key);
		if (s == null) {
			s = ((SessionFactory) sessionFactoryMap.get(key)).openSession();
			sessionMaps.put(key, s);
		}
		return s;
	}

	public void closeSessions() throws HibernateException {
		HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
		.get();
		sessionMapsThreadLocal.set(null);
		if (sessionMaps != null) {
			for (Session session : sessionMaps.values()) {
				if (session.isOpen())
					session.close();
			}
			;
		}
	}

	public void save(Object sql_server, Object my_sql)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			saveInternal(my_sql, MY_SQL);
		}
	}

	public void save(Object mySQLObj)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			saveInternal(mySQLObj, MY_SQL);
		}
	}

	private void saveInternal(Object object, String dataName) {
		try {
			Session s = currentSession(dataName);
			Transaction tx = s.beginTransaction();
			s.save(object);
			if(!tx.wasCommitted()) {
				tx.commit();
			}
			s.clear();
		} catch (HibernateException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
		}
	}

	public void saveOrUpdate(Object mySQLObj)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			saveOrUpdateInternal(mySQLObj, MY_SQL);
		}
	}

	public void saveOrUpdate(Object sql_server, Object my_sql)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			saveOrUpdateInternal(my_sql, MY_SQL);
		}
	}

	private void saveOrUpdateInternal(Object object, String dataName) {
		Session s = currentSession(dataName);

		s.clear();
		s.evict(object);
		Transaction tx = s.beginTransaction();
		s.saveOrUpdate(object);
		if(!tx.wasCommitted()) {
			tx.commit();
		}
	}

	public void update(Object sql_server, Object my_sql)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			updateInternal(my_sql, MY_SQL);
		}
	}

	private void updateInternal(Object object, String dataName) {
		Session s = currentSession(dataName);
		Transaction tx = s.beginTransaction();
		s.update(object);
		if(!tx.wasCommitted()) {
			tx.commit();
		}
	}

	public Object merge(Object sql_server, Object my_sql)
	throws org.hibernate.HibernateException {
		Object result;
		if (USE_MYSQL) {
			result = 	mergeInternal(my_sql, MY_SQL);
		}
		return result;
	}

	public Object merge(Object mySQLObj)
	throws org.hibernate.HibernateException {
		Object result;
		if (USE_MYSQL) {
			result = mergeInternal(mySQLObj, MY_SQL);
		}
		return result;
	}
	private Object mergeInternal(Object object, String dataName) {
		Session s = currentSession(dataName);
		Transaction tx = s.beginTransaction();
		Object obj = s.merge(object);
		if(!tx.wasCommitted()) {
			tx.commit();
		}
		return obj;
	}

	public void delete(Object sql_server, Object my_sql)
	throws org.hibernate.HibernateException {
		if (USE_MYSQL) {
			deleteInternal(my_sql, MY_SQL);
		}
	}

	public void delete(Object my_sql)
	throws org.hibernate.HibernateException {
		deleteInternal(my_sql, MY_SQL);
	}

	private void deleteInternal(Object object, String dataName) {
		Session s = currentSession(dataName);
		Transaction tx = s.beginTransaction();
		s.delete(object);
		if(!tx.wasCommitted()) {
			tx.commit();
		}
	}


		public static String loadDBConfig(Properties runtimeProps) {
			logger_.info("loadDBConfig ENTRY");
			if(configLoaded) {
				logger_.info("loadDBConfig EXIT Configuration already loaded");
				return "Loaded";
			}
			try {
				String fileName;
				ArrayList<String> cfglist = new ArrayList<String>(); 
				//	cfglist.add(SQL_SERVER);
				cfglist.add(MY_SQL);
				for (String key : cfglist) {
					fileName = key + ".cfg.xml";
					if (usingAnnotations) {
						logger_.info("loading cfg file: "+ fileName);
						cfg = new AnnotationConfiguration();
						 cfg.configure(fileName);
						 Properties mergedProperties = new Properties();
						 Properties defaultProperties = cfg.getProperties();
						 PropsUtil.reload();
						 Properties userProperties = PropsUtil.getProperties();
						 logger_.info("Properties: "+userProperties);
						 for(String propKey : defaultProperties.stringPropertyNames()){
							 mergedProperties.setProperty(propKey, defaultProperties.getProperty(propKey));
						 }
						 for(String propKey : userProperties.stringPropertyNames()){ // load External Properties
							 mergedProperties.setProperty(propKey, userProperties.getProperty(propKey));
						 }
						 
						 for(String propKey : runtimeProps.stringPropertyNames()) {//load runtime properties
							 mergedProperties.setProperty(propKey, runtimeProps.getProperty(propKey));
						 }
						 
						cfg.setProperties(mergedProperties); //set back all the props
						sf = cfg.buildSessionFactory();
						logger_.info("Loaded Hibernate configuration for "+key);
					}
					sessionFactoryMap.put(key, sf);
				}
				
				
			} catch (Exception ex) {
				logger_.error("Initial SessionFactories creation failed. \n" + ExceptionUtils.getFullStackTrace(ex));
				throw new ExceptionInInitializerError(ex);
			}
		
			Session s = getInstance().currentSession(MY_SQL);  
			try {
				s.beginTransaction(); // Just a test if all the DB Values are correct
			} catch (Exception e) {
				return ExceptionUtils.getRootCauseMessage(e);
			}
			s.getTransaction().rollback();
			configLoaded = true;
			logger_.info("loadDBConfig EXIT");
			return "Loaded";
		}

	public void saveMySQL(Object mySQLObj) {
		saveInternal(mySQLObj, MY_SQL);
	}
	
	public static String createDB(String dbName, String serverIP, String username, String password)
		   {
		      Statement stmt = null;

		      try {
		         Class.forName("com.mysql.jdbc.Driver");

		         String jdbc_url = "jdbc:mysql://"+serverIP+"/?user="+username+"&password="+password;

		         Connection conn = DriverManager.getConnection(jdbc_url);

		         // Does dbName exist?

		         DatabaseMetaData dbmd = conn.getMetaData();
		         ResultSet rs = dbmd.getCatalogs();  // closed with stmt.close()
		         boolean found = false;
		         while (rs.next()) {
		            String catalog = rs.getString(1);
		            if (catalog.equals(dbName)) {
		               found = true;
		            }
		         }

		         // Nuts. Create it!

		         if (!found) {
		            stmt = conn.createStatement();
		            stmt.execute("CREATE DATABASE " + dbName);
		            stmt.close();
		         }
		      }
		      catch (ClassNotFoundException e) {
		    	  return ExceptionUtils.getRootCauseMessage(e);
		      }
		      catch (SQLException e) {
		         return ExceptionUtils.getRootCauseMessage(e);
		      }
		      finally {
		         try {
		            if (stmt != null)
		               stmt.close();
		         }
		         catch (SQLException e) {}
		      }
		      return "SUCCESS";
		   }
	
	public static String reloadDBConfig(Properties p) {
		configLoaded = false;
		return loadDBConfig(p);
	}
}