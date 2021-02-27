package com.ravi.patient.app.config;

import java.sql.*;

import org.apache.log4j.Logger; 

/**
 * @author RaviP
 *
 */
public class DBconfiguration {

	static Logger logger = Logger.getLogger(DBconfiguration.class);
	
	private static final String DRIVER_CLASS_NAME="com.mysql.cj.jdbc.Driver"; 
	private static final String CONNECTION_URL="jdbc:mysql://127.0.0.1:3306/patientportal?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT";
	private static final String USERNAME="root"; 
	private static final String PASSWORD="password_123"; 
	
	Connection con=null;
	 
	public DBconfiguration() {
		
		con=getConnection();
	}
	
	public Connection getConnection() {
		
		if(con==null) {
		try{  
			Class.forName(DRIVER_CLASS_NAME);  
			con=DriverManager.getConnection(  
					CONNECTION_URL,USERNAME,PASSWORD);  
			  
			}catch(Exception e){
				logger.error(e.getMessage());
				}  
		
		}
		return con;
	}
	
}
