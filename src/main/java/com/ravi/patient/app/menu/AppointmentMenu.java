package com.ravi.patient.app.menu;

import java.text.ParseException;
import java.util.Scanner;

import com.ravi.patient.app.helper.AppointmentHelper;

/**
 * @author RaviP
 *
 */
public class AppointmentMenu {

	public void init(Scanner scanner) throws ParseException {

		while (true) {
			displayAppointmentMenu();
			AppointmentHelper appointmentHelper=new AppointmentHelper();
			System.out.println("Please select from options :");

			switch (scanner.nextInt()) {
			case 1:
				System.out.println("You selected Book Appointment !!");
				appointmentHelper.bookAppointment(scanner);
				break;
			case 2:
				System.out.println("You selected Cancel Appointment !!");
				appointmentHelper.cancelAppointment(scanner);
				break;
			case 3:
				System.out.println("You selected Reschedule Appointment !!");
				appointmentHelper.rescheduleAppointment(scanner);
				break;
			case 4:
				System.out.println("Returning to Previous Menu..");
				new MainMenu().init(scanner);
				return;

			case 5:
				System.out.println("Exiting...");
				System.exit(0);
				break;

			default:
				// inform user in case of invalid choice.
				System.out.println("Invalid choice. Read the options carefully...");
			}
		}
	}

	private void displayAppointmentMenu() {
		System.out.println("\n----------------------------------");
		System.out.println("-------Appointment Menu-----------");
		System.out.println("----------------------------------");
		System.out.println("Press 1 - Book Appointment");
		System.out.println("Press 2 - Cancel Appointment");
		System.out.println("Press 3 - Reschedule Appointment");
		System.out.println("Press 4 - Return to Main Menu");
		System.out.println("Press 5 - Exit");
	}

}
