package com.ravi.patient.app.config;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * @author RaviP
 *
 */
public class DBconfiguration {

	static Logger logger = Logger.getLogger(DBconfiguration.class);
	private static DBconfiguration instance;

	private Connection connection;
	private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/patientportal?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password_123";

	private DBconfiguration() throws SQLException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
			this.connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DBconfiguration getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBconfiguration();
		} else if (instance.getConnection().isClosed()) {
			instance = new DBconfiguration();
		}
		return instance;
	}
}
