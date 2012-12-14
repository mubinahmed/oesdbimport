package com.oes.db.model.util;

public class DBProfile {

	String dbIP;
	String dbName;
	String dbPort;
	String dbUserName;
	String profileName;
	
	public DBProfile() {
		
	}
	
	public DBProfile(String dbIP, String dbName, String dbPort,
			String dbUserName, String profileName) {
		super();
		this.dbIP = dbIP;
		this.dbName = dbName;
		this.dbPort = dbPort;
		this.dbUserName = dbUserName;
		this.profileName = profileName;
	}
	public String getDbIP() {
		return dbIP;
	}
	public void setDbIP(String dbIP) {
		this.dbIP = dbIP;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProfileName() {
		return profileName;
	}
}
