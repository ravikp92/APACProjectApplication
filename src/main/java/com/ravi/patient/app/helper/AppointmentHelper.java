package com.ravi.patient.app.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;
import com.ravi.patient.app.service.AppointmentService;
import com.ravi.patient.app.service.PatientService;
import com.ravi.patient.app.util.AppointmentUtility;


public class AppointmentHelper {
	AppointmentService appointmentService = new AppointmentService();

	public void bookAppointment(Scanner scanner) throws ParseException {
		PatientService patientService = new PatientService();

		int patientAttemptCount = 0;
		int physicianAttemptCount = 0;

		int patientIdInt = 0;
		do {

			try {
				System.out.println("Enter Patient ID for which appointment needs to be booked:\n");
				String patientId = scanner.next();
				patientIdInt = Integer.parseInt(patientId);

				Optional<Patient> patientObj = patientService.searchPatientById(patientIdInt);

				if (patientObj.isPresent()) {
					System.out.println("\nSearch result for Patient: \n\n"
							+ (patientObj.isPresent() ? patientObj.get() : patientObj));

					do {
						System.out.println("Enter Physician Name :\n");
						String physicianName = scanner.next();

						Physician physician = appointmentService.getAllPhysicians().stream()
								.filter(customer -> physicianName.equalsIgnoreCase(customer.getName())).findFirst()
								.orElse(null);

						if (physician != null) {

							System.out.println("Enter Appointment Date in dd/MM/yyyy format:\n");
							String appointmentDate = scanner.next();

							System.out.println("Enter Appointment Slot: in HH:MM format:\n");
							String appointmentSlot = scanner.next();

							Date appointmentDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(appointmentDate);

							boolean doesAppointmentExists = appointmentService.checkIfAppointmentSlotAvailable(
									physician, patientObj, appointmentDateConverted, appointmentSlot);

							if (doesAppointmentExists) {
								System.out.println("Appointment Slot not available for Physician " + physician.getName()
										+ " for Date- " + appointmentDateConverted + " & Timeslot- " + appointmentSlot);

							} else {

								String resultMessage = appointmentService.bookAppointmentForPatient(physician,
										patientObj, appointmentDateConverted, appointmentSlot);

								if (resultMessage != "") {

									System.out.println("Appointment has been booked successfully with Appointment No.: "
											+ resultMessage);
								} else {
									System.out.println("There is an error in booking appointment");
								}

								DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

								System.out.println("Appointment Id: " + resultMessage);
								System.out.println("Patient Id:" + patientObj.get().getId());
								System.out.println("Patient Name:" + patientObj.get().getFirstName() + " "
										+ patientObj.get().getLastName());
								System.out.println("Doctor Name:" + physician.getName());
								System.out.println("Appointment Date:" + dateFormat.format(appointmentDateConverted));
								System.out.println("Appointment Slot:" + appointmentSlot);

								AppointmentUtility.generateAppointmentPDF(physician, patientObj, appointmentDateConverted,
										appointmentSlot, resultMessage);

								break;
							}

							break;
						} else {
							System.out.println("Physician name is invalid, Please try again.\n");
							physicianAttemptCount++;
						}
					} while (physicianAttemptCount < 3);
					break;
				} else {
					System.out.println("Patient not found, please try again\n");
					patientAttemptCount++;
				}

			} catch (NumberFormatException ex) {
				System.out.println("Patient ID is invalid please try again.\n");
				patientAttemptCount++;
			}
		} while (patientAttemptCount < 3);

		if (patientAttemptCount == 3) {
			System.out.println("Sorry, you can't continue further as 3 attempts exhausted for valid Patient Id");
			System.exit(0);
		}
		if (physicianAttemptCount == 3) {
			System.out.println("Sorry, you can't continue further as 3 attempts exhausted for valid Physician Name");
			System.exit(0);
		}
	}

	public void cancelAppointment(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

	public void rescheduleAppointment(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

}
