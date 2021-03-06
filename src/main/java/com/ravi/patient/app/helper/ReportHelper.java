package com.ravi.patient.app.helper;

import java.util.Optional;
import java.util.Scanner;

import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;
import com.ravi.patient.app.service.impl.AppointmentServiceImpl;
import com.ravi.patient.app.service.impl.PatientServiceImpl;
import com.ravi.patient.app.util.PatientReportUtility;

public class ReportHelper {
	public void patientReports(Scanner scanner) {
		PatientServiceImpl patientService = new PatientServiceImpl();

		int patientIdTryCount = 0;

		int patientIdInt = 0;
		do {

			try {
				System.out.println("Generating Patient Reports....\n");
				System.out.println("Enter Patient ID  :\n");
				String patientId = scanner.next();
				patientIdInt = Integer.parseInt(patientId);

				Optional<Patient> patientObj = patientService.searchPatientById(patientIdInt);

				if (patientObj.isPresent()) {
					System.out.println("\n Patient Data : "+patientObj.get());

					Optional<PatientMedicalHistory> patientMedHistoryObj = patientService
							.searchPatientHistoryByPatientId(Integer.parseInt(patientId));
					System.out.println("\n Patient History: \n"
							+ patientMedHistoryObj.get());

					PatientReportUtility.generatePatientReportPDF(patientObj, patientMedHistoryObj);
					System.out.println("Patient Report is successfully generated!!!.\n");

					break;

				} else {
					System.out.println("Sorry !! No Patient Data exists for Patient Id: " + patientId);
					patientIdTryCount++;
				}

			} catch (NumberFormatException ex) {
				System.out.println("Invalid Patient Data! Please try again.\n");
				patientIdTryCount++;
			}
		} while (patientIdTryCount < 3);

		if (patientIdTryCount == 3) {
			System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
			System.out.println("Exiting... \n ");
			System.exit(0);
		}

	}

	public void appointmentReports(Scanner scanner) {
		PatientServiceImpl patientService = new PatientServiceImpl();
		 AppointmentServiceImpl appointmentService=new AppointmentServiceImpl();
		int patientIdTryCount = 0;

		int patientIdInt = 0;
		do {

			try {
				System.out.println("Generating Appointment Reports for Patient....\n");
				System.out.println("Enter Patient ID  :\n");
				String patientId = scanner.next();
				patientIdInt = Integer.parseInt(patientId);

				Optional<Patient> patientObj = patientService.searchPatientById(patientIdInt);

				if (patientObj.isPresent()) {
					System.out.println("\nSearch result for Patient: \n\n"
							+ (patientObj.isPresent() ? patientObj.get() : patientObj));

					boolean isAppointmentExists = appointmentService.checkAppointmentByPatientId(patientIdInt);

					if (isAppointmentExists) {

						Optional<Appointment> appointmentObj = appointmentService
								.getAppointmentByPatientId(patientIdInt);

						PatientReportUtility.generateAppointmentReportPDF(appointmentObj, patientObj);
						System.out.println("Appointment Report is successfully generated!!!.\n");

						break;
					}

					else {
						System.out.println("Sorry !! No Appointment exists for Patient Id: " + patientId);
						break;
					}

				} else {
					System.out.println("Invalid Patient Data! Please try again.\n");
					patientIdTryCount++;
				}

			} catch (NumberFormatException ex) {
				System.out.println("Invalid Patient Id !! Please try again.\n");
				patientIdTryCount++;
			}
		} while (patientIdTryCount < 3);

		if (patientIdTryCount == 3) {
			System.out.println("Data entered is invalid and Maximum limit allowed is 3. \n ");
			System.out.println("Exiting... \n ");
			System.exit(0);
		}
		
	}

}
