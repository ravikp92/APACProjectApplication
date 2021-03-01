package com.ravi.patient.app.menu;

import java.text.ParseException;
import java.util.Scanner;

import com.ravi.patient.app.helper.PatientHelper;

public class PatientMenu {

	public int init(Scanner scanner) throws ParseException {
		
		while (true) {
			displayPatientMenu();
			PatientHelper patientHelper=new PatientHelper();
			System.out.println("Please select from options :");
			
			switch (scanner.nextInt()) {
			case 1:
				System.out.println("You selected Add Patient !!");
				patientHelper.addPatient(scanner);
				break;
			case 2:
				System.out.println("You selected Update Patient");
				patientHelper.updatePatient(scanner);
				break;
			case 3:
				System.out.println("You selected Delete Patient");
				patientHelper.deletePatient(scanner);
				break;
			case 4:
				System.out.println("You selected Search Patient");
				patientHelper.searchPatient(scanner);
				break;
			case 5:
				System.out.println("Returning to Previous Menu..");
				new MainMenu().init(scanner);
				break;
			case 6:
				System.out.println("Exiting...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice");
				break;
			}
		}
		
	}

	private void displayPatientMenu() {
		System.out.println("---------------------------");
		System.out.println("-------Patient Menu--------");
		System.out.println("---------------------------");
		System.out.println("Press 1 - Add Patient");
		System.out.println("Press 2 - Update Patient");
		System.out.println("Press 3 - Delete Patient");
		System.out.println("Press 4 - Search Patient");
		System.out.println("Press 5 - Return to Main Menu");
		System.out.println("Press 6 - Exit");
	}
}
