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

		int patientIdTryCount = 0;
		int physicianNameTryCount = 0;

		do {
			try {
				System.out.println("Booking Appointment.....\n");
				Optional<Patient> patientDetail = getPatientDetail(scanner, patientService);
				if (patientDetail.isPresent()) {
					
					System.out.println("\n Patient Data : "+patientDetail.get());

					physicianNameTryCount = validatePhysicianAndBookAppointment(scanner, physicianNameTryCount,
							patientDetail);
					break;
				} else {
					System.out.println("Patient Id not found!! Please Try Again!");
					patientIdTryCount++;
				}

			} catch (NumberFormatException ex) {
				System.out.println("Patient ID is invalid please try again.\n");
				patientIdTryCount++;
			}
		} while (patientIdTryCount < 3);

		exitIfTryCountExceed(patientIdTryCount, physicianNameTryCount);
	}

	private Optional<Patient> getPatientDetail(Scanner scanner, PatientService patientService) {
		int patientIdInt;
		System.out.println("Enter Patient ID :\n");
		String patientId = scanner.next();
		patientIdInt = Integer.parseInt(patientId);
		Optional<Patient> patientDetail = patientService.searchPatientById(patientIdInt);
		return patientDetail;
	}

	private void exitIfTryCountExceed(int patientIdTryCount, int physicianNameTryCount) {
		if (patientIdTryCount == 3||physicianNameTryCount == 3) {
			System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
			System.out.println("Exiting... \n ");
			System.exit(0);
		}
	}

	private int validatePhysicianAndBookAppointment(Scanner scanner, int physicianNameTryCount,
			Optional<Patient> patientDetail) throws ParseException {
		do {
			
			System.out.println("Enter Physician Name :\n");
			String nameOfPhysician = scanner.next();

			Physician physician = appointmentService.getAllPhysicians().stream()
					.filter(phy -> nameOfPhysician.equalsIgnoreCase(phy.getName())).findFirst()
					.orElse(null);

			if (physician != null) {

				System.out.println("Provide Appointment Date in dd/MM/yyyy format:\n");
				String appointDate = scanner.next();

				System.out.println("Provide Appointment Slot: in HH:MM format:\n");
				String appointSlot = scanner.next();

				Date appointParseDate = new SimpleDateFormat("dd/MM/yyyy").parse(appointDate);

				boolean isAppointMentSlotBlocked = appointmentService.checkAppointmentSlotForPhysician(
						physician, patientDetail, appointParseDate, appointSlot);

				if (isAppointMentSlotBlocked) {
					System.out.println("Appointment Slot not available for Physician " + physician.getName()
							+ " for Date- " + appointParseDate + " & Timeslot- " + appointSlot);

				} else {

					String bookingMessage = appointmentService.bookAppointmentWithPhysician(physician,
							patientDetail, appointParseDate, appointSlot);

					if (bookingMessage != null) {

						System.out.println("Appointment booked with Appointment No.: "
								+ bookingMessage);
						successAppointmentResponse(patientDetail, physician, appointSlot, appointParseDate,
								bookingMessage);
					} else {
						System.out.println("Appointment booking failed");
					}
				}

				break;
			} else {
				System.out.println("No physician present with Physician "+nameOfPhysician+" , Please try again.\n");
				physicianNameTryCount++;
			}
		} while (physicianNameTryCount < 3);
		
		return physicianNameTryCount;
	}

	private void successAppointmentResponse(Optional<Patient> patientDetail, Physician physician, String appointSlot,
			Date appointParseDate, String bookingMessage) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("Appointment Id: " + bookingMessage);
		System.out.println("Patient Id:" + patientDetail.get().getId());
		System.out.println("Patient Name:" + patientDetail.get().getFirstName() + " "
				+ patientDetail.get().getLastName());
		System.out.println("Doctor Name:" + physician.getName());
		System.out.println("Appointment Date:" + dateFormat.format(appointParseDate));
		System.out.println("Appointment Slot:" + appointSlot);

		AppointmentUtility.generateAppointmentPDF(physician, patientDetail, appointParseDate,
				appointSlot, bookingMessage);
	}

	public void cancelAppointment(Scanner scanner) {
		int patientIdTryCount = 0;
		do {
			try {
				System.out.println("Cancelling Appointment.....\n");
				System.out.println("Enter Patient ID : \n");
				String patientId = scanner.next();
				int patientIdInt = Integer.parseInt(patientId);

				boolean isAppointmentExists = appointmentService.checkAppointmentByPatientId(patientIdInt);

				if (isAppointmentExists) {
					System.out.println("Are you Sure You want to Cancel the appointment ? Press Y for Yes or N for No \n");
					String confirmCancel = scanner.next();
					if ("Y".equalsIgnoreCase(confirmCancel)) {

						boolean isAppointmentCancelled = appointmentService.cancelAppointmentForPatientId(patientIdInt);
						if (isAppointmentCancelled) {
							System.out.println("Thank you for confirmation. Your Appointment is cancelled.");
							break;
						} else {
							System.out.println(
									"Thank you for confirmation. Due to Technical Issue cancellation failed. Please try again.");
							break;
						}

					} else if ("N".equalsIgnoreCase(confirmCancel)) {
						System.out.println("Thank you for confirmation. Your Appointment is still available");
						break;
					} else {
						System.out.println("Invalid Option selected . Please try again!!");
						patientIdTryCount++;
						continue;
					}

				} else {
					System.out.println("No Appointment exits for Patient Id: " + patientId);
					break;
				}

			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Patient Data");

				patientIdTryCount++;
			}
		} while (patientIdTryCount < 3);

		if (patientIdTryCount == 3) {
			System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
			System.out.println("Exiting... \n ");
			System.exit(0);
		}
		
	}

	public void rescheduleAppointment(Scanner scanner) throws ParseException {
		int patientIdTryCount = 0;
		int physicianNameTryCount = 0;
		String resultMessage = "";
		boolean rescheduleFlag = false;
		PatientService patientService = new PatientService();

		do {
			try {
				System.out.println("Rescheduling Appointment.....\n");
				System.out.println("Enter Patient ID : \n");
				String patientId = scanner.next();
				int patientIdInt = Integer.parseInt(patientId);

				boolean isAppointmentExists = appointmentService.checkAppointmentByPatientId(patientIdInt);

				if (isAppointmentExists) {
					System.out.println("Do you want to reschedule the appointment? Press Y for Yes or N for No \n");
					String confirmReschedule = scanner.next();
					if ("Y".equalsIgnoreCase(confirmReschedule)) {

						Optional<Patient> patientObj = patientService.searchPatientById(patientIdInt);

						if (patientObj.isPresent()) {
							System.out.println("\n Patient Data : "+patientObj.get());
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

									Date appointDate = new SimpleDateFormat("dd/MM/yyyy")
											.parse(appointmentDate);

									boolean isAppointmentBlocked = appointmentService.checkAppointmentSlotForPhysician(
											physician, patientObj, appointDate, appointmentSlot);

									if (isAppointmentBlocked) {
										System.out.println("Appointment Slot is not available for Physician "
												+ physician.getName() + " for Date- " + appointDate
												+ " & Timeslot- " + appointmentSlot);
										rescheduleFlag = true;
										break;

									} else {

										resultMessage = appointmentService.rescheduleAppointmentForExisitngPatient(physician,
												patientObj, appointDate, appointmentSlot);

										if (resultMessage != null) {

											System.out.println(
													"Your request for reschedule was success. Please Note New Appointment No.: "
															+ resultMessage);
											successAppointmentResponse(patientObj, physician, appointmentSlot,
													appointDate, resultMessage);
											rescheduleFlag = true;
											break;

										} else {
											System.out.println("There is an error in booking appointment");
											rescheduleFlag = true;
											break;
										}

									}
								} else {
									System.out.println("Physician name is invalid, Please try again.\n");
									physicianNameTryCount++;
								}
							} while (physicianNameTryCount < 3);

							if (physicianNameTryCount == 3) {
								System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
								System.out.println("Exiting... \n ");
								System.exit(0);
							}

						}
					} else if ("N".equalsIgnoreCase(confirmReschedule)) {
						System.out.println("Thank you for confirmation. Your Appointment is not rescheduled.");
						rescheduleFlag = true;
						break;
					} else {
						System.out.println("Invalid Input. Please input again Patient Id");
						patientIdTryCount++;
						continue;
					}

				} else {
					System.out.println("Sorry !! Appointment does not exists for entered Patient Id: " + patientId);
					break;
				}

			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Patient ID please try again");

				patientIdTryCount++;
			}
		} while (patientIdTryCount < 3 && rescheduleFlag == false);

		if (patientIdTryCount == 3) {
			System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
			System.out.println("Exiting... \n ");
			System.exit(0);
		}
		
	}

}
