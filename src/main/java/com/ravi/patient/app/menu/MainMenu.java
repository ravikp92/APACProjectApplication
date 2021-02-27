package com.ravi.patient.app.menu;

import java.text.ParseException;
import java.util.Scanner;

public class MainMenu {

	public void init(Scanner scanner) throws ParseException {
		// Loop to make a choice
		while (true) {
				displayMainMenu();
				System.out.println("Please select from options :");

				switch (scanner.nextInt()) {

				case 1:
					System.out.println("You selected Patients \n");
					new PatientMenu().init(scanner);
					break;

				case 2:
					System.out.println("You selected Appointments \n");
					new AppointmentMenu().init(scanner);
					break;

				case 3:
					System.out.println("You selected Reports \n");
					new ReportMenu().init(scanner);
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

	private static void displayMainMenu() {
		System.out.println("\n-------------------------");
		System.out.println("---------Main Menu---------");
		System.out.println("---------------------------");
		System.out.println("Press 1 for Patients");
		System.out.println("Press 2 for Appointments");
		System.out.println("Press 3 for Reports");
		System.out.println("Press 4 to Exit");
	}

}
