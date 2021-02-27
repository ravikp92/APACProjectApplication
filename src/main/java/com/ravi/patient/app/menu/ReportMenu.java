package com.ravi.patient.app.menu;

import java.text.ParseException;
import java.util.Scanner;

import com.ravi.patient.app.helper.ReportHelper;

public class ReportMenu {

	public void init(Scanner scanner) throws ParseException {
		while (true) {
			displayReportMenu();
			ReportHelper reportHelper=new ReportHelper();
			System.out.println("Please select from options :");

			switch (scanner.nextInt()) {
			case 1:
				reportHelper.patientReports(scanner);
				break;
			case 2:
				reportHelper.appointmentReports(scanner);
				break;
			case 3:
				System.out.println("Returning to Previous Menu..");
				new MainMenu().init(scanner);
				break;
			case 4:
				System.out.println("Exiting...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

	private void displayReportMenu() {
		System.out.println("\n-------------------------------");
		System.out.println("---------Report Menu------------");
		System.out.println("-------------------------------");
		System.out.println("Press 1 - Patients");
		System.out.println("Press 2 - Appointments");
		System.out.println("Press 3 - Return to Main menu");
		System.out.println("Press 4 - Exit");
	}

}
