package com.mbi.oes.db.utils;


import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;


public class PropsUtil {

	private  Properties props = new Properties();
	private static PropsUtil _instance = new PropsUtil();
	private static Logger logger_ = Logger.getLogger(PropsUtil.class);

	public static boolean contains(String key) {
		return _instance.props.contains(key);
	}

	public static void set(String key, String value) {
		_instance.props.put(key, value);
	}

	public static String get(String key) {
		return (String) _instance.props.get(key);
	}


	public static String[] getArray(String key) {
		return get(key).split(StringPool.COMMA);
	}

	public static Properties getProperties() {
		return  _instance.props;
	}


	public static void reload() {
		_instance.props.clear();
		load();
	}




	public static boolean load() {
		boolean propsLoaded = false;
		// Default OES home directory
		String oesHome = _instance._getDefaultOESHome(); 
		String oesHome2 = normalize(FileUtils.getUserDirectoryPath());
		if(oesHome != null ){
			logger_.info("Loading Properties from OES Home Directory");
			propsLoaded = loadProps(oesHome);
		} 
		logger_.info("User Home Directory: "+oesHome2);
		logger_.info("Loading Properties from User Directory. Properties from User Directory supercedes Properties from Home Directory");
		propsLoaded = loadProps(oesHome2); // User Props will overwrite Home Props
		if(!propsLoaded){
			InputStream is = PropsUtil.class.getResourceAsStream("/oes.properties");
			File propsFile = FileUtils.getFile(FilenameUtils.concat(oesHome2, "oes.properties"));
			try {
				FileUtils.copyInputStreamToFile(is, propsFile);
				is.close();
				logger_.error("Saved default Properties to OES Home Directory");
			} catch (Exception e) {
				logger_.error("Error saving default Properties to OES Home Directory");
			}
		}
		return propsLoaded;
	}

	private static boolean loadProps(String oesHome) {
		boolean propsLoaded = false;
		try {
			File propsFile = FileUtils.getFile(FilenameUtils.concat(oesHome, "oes.properties"));
			if (propsFile != null && propsFile.exists()) {
				_instance.props.load(FileUtils.openInputStream(propsFile));
				propsLoaded = true;
				logger_.info("Loaded Properties from : " + propsFile.getAbsolutePath());
			}else{
				logger_.warn("Property file not found  : " + propsFile.getAbsolutePath());
			}
		} catch (Exception e) {
			logger_.error(ExceptionUtils.getFullStackTrace(e));
		}
		return propsLoaded;
	}

	private String _getDefaultOESHome() {
		String defaultOESHome = System.getenv("OES_HOME");

		if (defaultOESHome != null) {
			defaultOESHome = normalize(defaultOESHome);
			logger_.info("Default OES home : " + defaultOESHome);
		}else{
			logger_.warn("OES Home not set");
		}


		return defaultOESHome;
	}

	public static String normalize(String str) {
		str = StringUtils.replace(
				str, CharPool.BACK_SLASH, CharPool.SLASH);

		str = StringUtils.replace(
				str, StringPool.DOUBLE_SLASH, StringPool.SLASH);
		return str;
	}

}