package com.ravi.patient.app;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.ravi.patient.app.config.DBconfiguration;
import com.ravi.patient.app.menu.MainMenu;

public class PatientApplication {

	private static Logger logger = Logger.getLogger(DBconfiguration.class);

	public static void main(String[] args) throws ParseException, IOException {

		BasicConfigurator.configure();
		try(Scanner scanner = new Scanner(System.in)){
			new MainMenu().init(scanner);
		} catch (Exception e) {
			logger.error("Expcetion occured at Main Menu class {0}"+e);
		}
	}
}