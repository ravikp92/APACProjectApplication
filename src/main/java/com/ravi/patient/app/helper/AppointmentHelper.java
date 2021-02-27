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
		int patientAttemptCount = 0;

		do {
			try {

				System.out.println("Enter Patient ID for which appointment needs to be cancelled:\n");
				String patientId = scanner.next();
				int patientIdInt = Integer.parseInt(patientId);

				boolean doesAppointmentExist = appointmentService.checkAppointmentExistsForPatientId(patientIdInt);

				if (doesAppointmentExist) {
					System.out.println("Do you want to cancel the appointment? Press Y for Yes or N for No \n");
					String confirmCancel = scanner.next();
					if ("Y".equalsIgnoreCase(confirmCancel)) {

						boolean statusOfCancellation = appointmentService.cancelAppointmentForPatientId(patientIdInt);
						if (statusOfCancellation) {
							System.out.println("Thank you for confirmation. Your Appointment is cancelled.");
							break;
						} else {
							System.out.println(
									"Thank you for confirmation. There is some issue with the system. Please try again.");
							break;
						}

					} else if ("N".equalsIgnoreCase(confirmCancel)) {
						System.out.println("Thank you for confirmation. Your Appointment is still valid");
						break;
					} else {
						System.out.println("Invalid Input. Please input again Patient Id");
						patientAttemptCount++;
						continue;
					}

				} else {
					System.out.println("Sorry Appointment does not exists for Patient Id: " + patientId);
					break;
				}

			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Patient ID please try again");

				patientAttemptCount++;
			}
		} while (patientAttemptCount < 3);

		if (patientAttemptCount == 3) {
			System.out.println("Sorry, you can't continue further as 3 attempts exhausted for valid input");
			System.exit(0);

		}
		
	}

	public void rescheduleAppointment(Scanner scanner) throws ParseException {
		int patientAttemptCount = 0;
		int physicianAttemptCount = 0;
		String resultMessage = "";
		boolean patientLoopBreakFlag = false;
		PatientService patientService = new PatientService();

		do {
			try {

				System.out.println("Enter Patient ID for which appointment needs to be rescheduled:\n");
				String patientId = scanner.next();
				int patientIdInt = Integer.parseInt(patientId);

				boolean doesAppointmentExist = appointmentService.checkAppointmentExistsForPatientId(patientIdInt);

				if (doesAppointmentExist) {
					System.out.println("Do you want to reschedule the appointment? Press Y for Yes or N for No \n");
					String confirmReschedule = scanner.next();
					if ("Y".equalsIgnoreCase(confirmReschedule)) {

						Optional<Patient> patientObj = patientService.searchPatientById(patientIdInt);

						if (patientObj.isPresent()) {

							do {
								System.out.println("Enter Physician Name :\n");
								String physicianName = scanner.next();

								Physician physician = appointmentService.getAllPhysicians().stream()
										.filter(customer -> physicianName.equalsIgnoreCase(customer.getName()))
										.findFirst().orElse(null);

								if (physician != null) {

									System.out.println("Enter New Appointment Date in dd/MM/yyyy format:\n");
									String appointmentDate = scanner.next();

									System.out.println("Enter New Appointment Slot: in HH:MM format:\n");
									String appointmentSlot = scanner.next();

									Date appointmentDateConverted = new SimpleDateFormat("dd/MM/yyyy")
											.parse(appointmentDate);

									boolean doesAppointmentExists = appointmentService.checkIfAppointmentSlotAvailable(
											physician, patientObj, appointmentDateConverted, appointmentSlot);

									if (doesAppointmentExists) {
										System.out.println("Appointment Slot not available for Physician "
												+ physician.getName() + " for Date- " + appointmentDateConverted
												+ " & Timeslot- " + appointmentSlot);
										patientLoopBreakFlag = true;
										break;

									} else {

										resultMessage = appointmentService.rescheduleAppointmentForPatientId(physician,
												patientObj, appointmentDateConverted, appointmentSlot);

										if (resultMessage != "") {

											System.out.println(
													"Appointment has been rescheduled successfully with Appointment No.: "
															+ resultMessage);
											DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

											System.out.println("Appointment Id: " + resultMessage);
											System.out.println("Patient Id:" + patientObj.get().getId());
											System.out.println("Patient Name:" + patientObj.get().getFirstName() + " "
													+ patientObj.get().getLastName());
											System.out.println("Doctor Name:" + physician.getName());
											System.out.println(
													"Appointment Date:" + dateFormat.format(appointmentDateConverted));
											System.out.println("Appointment Slot:" + appointmentSlot);

											AppointmentUtility.generateAppointmentPDF(physician, patientObj,
													appointmentDateConverted, appointmentSlot, resultMessage);
											patientLoopBreakFlag = true;
											break;

										} else {
											System.out.println("There is an error in booking appointment");
											patientLoopBreakFlag = true;
											break;
										}

									}
								} else {
									System.out.println("Physician name is invalid, Please try again.\n");
									physicianAttemptCount++;
								}
							} while (physicianAttemptCount < 3);

							if (physicianAttemptCount == 3) {
								System.out.println(
										"Sorry, you can't continue further as 3 attempts exhausted for valid Physician Name");
								System.exit(0);
							}

						}
					} else if ("N".equalsIgnoreCase(confirmReschedule)) {
						System.out.println("Thank you for confirmation. Your Appointment is not rescheduled.");
						patientLoopBreakFlag = true;
						break;
					} else {
						System.out.println("Invalid Input. Please input again Patient Id");
						patientAttemptCount++;
						continue;
					}

				} else {
					System.out.println("Sorry Appointment does not exists for Patient Id: " + patientId);
					break;
				}

			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Patient ID please try again");

				patientAttemptCount++;
			}
		} while (patientAttemptCount < 3 && patientLoopBreakFlag == false);

		if (patientAttemptCount == 3) {
			System.out.println("Sorry, you can't continue further as 3 attempts exhausted for valid input");
			System.exit(0);

		}
		
	}

}
