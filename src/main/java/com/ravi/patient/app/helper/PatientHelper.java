package com.ravi.patient.app.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.ravi.patient.app.config.DBconfiguration;
import com.ravi.patient.app.enums.AffectedOrgan;
import com.ravi.patient.app.enums.Gender;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;
import com.ravi.patient.app.service.PatientService;
import com.ravi.patient.app.validation.Validations;

public class PatientHelper {
	private static Logger logger = Logger.getLogger(DBconfiguration.class);
	PatientService patientService = new PatientService();

	public void addPatient(Scanner scanner) throws ParseException {
		
		Patient patient = new Patient();
		PatientMedicalHistory patientMedicalHistory = new PatientMedicalHistory();
		readPatientData(scanner, patient,patientMedicalHistory);
		String result = patientService.addPatient(patient, patientMedicalHistory);
		System.out.println(result);
	}

	public void updatePatient(Scanner scanner) throws ParseException {
		System.out.println("Updating Patient Detials...\n");
		System.out.println("Please Enter Patient Id :\n");
		String patientId = scanner.next();
		
		Patient updatedPatient = new Patient();
		PatientMedicalHistory updatedPatientMedicalHistory = new PatientMedicalHistory();
		readPatientData(scanner, updatedPatient,updatedPatientMedicalHistory);
		
		updatedPatient.setId(Integer.parseInt(patientId));
		updatedPatientMedicalHistory.setPatientId(Integer.parseInt(patientId));
		
		
		int updatePatientResult = patientService.updatePatient(updatedPatient);
		int updatePatienHistoryResult = patientService.updatePatientMedicalHistory(updatedPatientMedicalHistory);
		
		if (updatePatientResult > 0 && updatePatienHistoryResult>0) {
			System.out.println("Record is updated successfully !!!");
			System.out.println(updatedPatient);
			System.out.println(updatedPatientMedicalHistory);
			logger.info("Patient Details Updated!!");
		} else {
			System.err.println("Record updation failed !!!");
			logger.error("Patient Details Failed Updation!!");
		}
		
	}

	public void deletePatient(Scanner scanner) {
		System.out.println("Deleting Patient Detials...\n");
		System.out.println("Please Enter Patient Id :\n");
		String patientId = scanner.next();
		int deleted = patientService.deletePatient(Integer.parseInt(patientId));
		if (deleted > 0) {
			System.out.println("Record has been deleted Successfully for patient Id :"+patientId);
		} else {
			System.err.println("Failed!! Unable to delete record for patient Id :"+patientId);
			logger.error("Failed!! Unable to delete record for patient Id :"+patientId);
		}
		
	}

	public void searchPatient(Scanner scanner) {
		System.out.println("Searching  Patient Detials...\n");
		System.out.println("Please Enter Patient Id :\n");
		String patientId = scanner.next();
		Optional<Patient> patientObj = patientService.searchPatientById(Integer.parseInt(patientId));
		
		if(patientObj.isPresent()) {
			System.out.println("Search Results..\n");
			System.out.println("Patient Details : "+patientObj.orElse(new Patient()));
			Optional<PatientMedicalHistory> patientHistoryObj = patientService.searchPatientHistoryByPatientId(Integer.parseInt(patientId));
			System.out.println("Patient Medical History Details : "+patientHistoryObj.orElse(new PatientMedicalHistory()));
		}else {
			System.err.println("Invalid Search Results..\n");
			logger.error("Invalid Search Results..\n");
		}
	}
	


	private void readPatientData(Scanner scanner, Patient patient, PatientMedicalHistory patientMedicalHistory) throws ParseException {
		
		System.out.print("\nEnter Patient First Name: ");
		
		String firstName = scanner.nextLine();
		firstName = Validations.validateName(scanner, firstName);
		
		System.out.print("\nEnter Patient Last Name: ");
		String lastName = scanner.nextLine();
		lastName = Validations.validateName(scanner, lastName);
		System.out.print("\nEnter Email Id: ");

		String email = scanner.nextLine();
		while (!Validations.isValidEmail(email)) {
			System.out.println("Please Enter Valid Email ID!! ");
			System.out.print("Please try again : ");
			email = scanner.nextLine();
		}

		System.out.print("\nEnter Phone Number: ");

		String phoneNumber = scanner.nextLine();
		while (!Validations.isValidPhoneNumber(phoneNumber)) {
			System.out.println("Please Enter Valid Phone Number!! ");
			System.out.print("Please try again : ");
			phoneNumber = scanner.nextLine();
		}

		System.out.print("Enter City: ");
		String city = scanner.nextLine();
		city = Validations.validateCityState(scanner, city, true);

		System.out.print("Enter State: ");
		String state = scanner.nextLine();
		state = Validations.validateCityState(scanner, state, false);

		System.out.print("Enter Date of birth(DD/MM/YYYY): ");

		String dateOfBirth = scanner.nextLine();
		while (!Validations.validateDob(dateOfBirth)) {
			System.out.println("Please Enter Valid DOB (dd/MM/yyyy)!! ");
			System.out.print("Please try again : ");
			dateOfBirth = scanner.nextLine();
		}

		System.out.print("Enter Gender (M/F): ");
		String gender = scanner.nextLine();
		boolean genderflag = false;
		while (!genderflag) {
			if (Gender.MALE.getGender().equalsIgnoreCase(gender)) {
				gender = Gender.MALE.name();
				genderflag = true;
			} else if (Gender.FEMALE.getGender().equalsIgnoreCase(gender)) {
				gender = Gender.FEMALE.name();
				genderflag = true;
			} else {
				System.out.println("Please Enter Valid Gender!! ");
				System.out.print("Please try again : ");
				gender = scanner.nextLine();
				genderflag = false;
			}
		}

		System.out.print("Enter Height: ");
		String height = scanner.nextLine();
		while (!Validations.validateNum(height)) {
			System.out.println("Please Enter Valid Height!! ");
			System.out.print("Please try again : ");
			height = scanner.nextLine();
		}

		System.out.print("Enter Weight: ");
		String weight = scanner.nextLine();
		while (!Validations.validateNum(weight)) {
			System.out.println("Please Enter Valid Weight!! ");
			System.out.print("Please try again : ");
			weight = scanner.nextLine();
		}

		System.out.print("Enter Pulse Rate: ");
		String pr = scanner.nextLine();
		while (!Validations.validateNum(pr)) {
			System.out.println("Please Enter Valid Pulse rate!! ");
			System.out.print("Please try again : ");
			pr = scanner.nextLine();
		}

		System.out.print("Enter Blood Pressure: ");
		String bloodPressure = scanner.nextLine();
		while (!Validations.validateNum(bloodPressure)) {
			System.out.println("Please Enter Valid Blood Pressure!! ");
			System.out.print("Please try again : ");
			bloodPressure = scanner.nextLine();
		}

		System.out
				.print("Enter affected organ number :" + "LUNGS(1), HEART(2), LEG(3), HANDS(4), KIDNEY(5),OTHERS(6) ");
		int affectedOrgan = scanner.nextInt();
		String affectedOrganName = "";
		boolean affectedOrganflag = false;
		while (!affectedOrganflag) {
			if (AffectedOrgan.LUNGS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.LUNGS.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.HEART.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.HEART.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.LEG.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.LEG.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.KIDNEY.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.KIDNEY.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.HANDS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.HANDS.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.OTHERS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.OTHERS.name();
				affectedOrganflag = true;
			} else {
				System.out.println("Please Enter Valid Affected Organ Number!! ");
				System.out.print("Please try again : ");
				affectedOrgan = scanner.nextInt();
				affectedOrganflag = false;
			}
		}

		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setEmail(email);
		patient.setPhoneNumber(phoneNumber);
		patient.setCity(city);
		patient.setState(state);
		patient.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
		patient.setGender(gender);
		patientMedicalHistory.setHeight(Double.parseDouble(height));
		patientMedicalHistory.setWeight(Double.parseDouble(weight));
		patientMedicalHistory.setPulseRate(Double.parseDouble(pr));
		patientMedicalHistory.setBloodPressure(Double.parseDouble(bloodPressure));
		patientMedicalHistory.setAffectedOrgan(affectedOrganName);
	}

}
